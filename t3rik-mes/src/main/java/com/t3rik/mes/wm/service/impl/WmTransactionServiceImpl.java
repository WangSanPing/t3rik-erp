package com.t3rik.mes.wm.service.impl;

import com.t3rik.common.core.redis.RedissonUtil;
import com.t3rik.common.enums.mes.LogChangeTypeEnum;
import com.t3rik.common.exception.BusinessException;
import com.t3rik.common.utils.DateUtils;
import com.t3rik.common.utils.StringUtils;
import com.t3rik.mes.common.dto.RecordStockLogDTO;
import com.t3rik.mes.common.service.IAsyncService;
import com.t3rik.mes.md.domain.MdItem;
import com.t3rik.mes.md.mapper.MdItemMapper;
import com.t3rik.mes.wm.domain.WmMaterialStock;
import com.t3rik.mes.wm.domain.WmTransaction;
import com.t3rik.mes.wm.mapper.WmMaterialStockMapper;
import com.t3rik.mes.wm.mapper.WmTransactionMapper;
import com.t3rik.mes.wm.service.IWmTransactionService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 库存事务Service业务层处理
 *
 * @author t3rik
 * @date 2025-1-4
 */
@Slf4j
@Service
public class WmTransactionServiceImpl implements IWmTransactionService {

    public static final String PREFIX_LOCK = "lock:";

    @Resource
    private WmTransactionMapper wmTransactionMapper;

    @Resource
    private WmMaterialStockMapper wmMaterialStockMapper;

    @Resource
    private MdItemMapper mdItemMapper;

    @Resource
    private RedissonUtil redissonUtil;

    @Resource
    private IAsyncService asyncService;

    @Override
    public synchronized WmTransaction processTransaction(WmTransaction wmTransaction) {
        // 声明库存记录
        WmMaterialStock stock = new WmMaterialStock();
        // 当前库存
        BigDecimal oldQuantity = BigDecimal.ZERO;
        // 校验传参数是否为空
        validate(wmTransaction);
        // 初始化赋值声明库存记录信息
        initStock(wmTransaction, stock);
        // 查询库存记录通过构建好的库存记录
        WmMaterialStock ms = wmMaterialStockMapper.loadMaterialStock(stock);
        // 扣减或者新增数量 = 事务数量/事务方向
        BigDecimal quantity = wmTransaction.getTransactionQuantity().multiply(new BigDecimal(wmTransaction.getTransactionFlag()));
        String key = this.generateKey(stock);
        try {
            if (redissonUtil.tryLock(key)) {
                if (StringUtils.isNotNull(ms)) {
                    // 已存在，记录旧库存
                    oldQuantity = ms.getQuantityOnhand();
                    // MS已存在扣减库存/添加库存
                    BigDecimal resultQuantity = ms.getQuantityOnhand().add(quantity);
                    // 检查库存量是否充足
                    if (wmTransaction.isStorageCheckFlag() && resultQuantity.compareTo(new BigDecimal(0)) < 0) {
                        throw new BusinessException("库存数量不足！");
                    }
                    // 更新在库数量
                    stock.setQuantityOnhand(resultQuantity);
                    stock.setMaterialStockId(ms.getMaterialStockId());
                    wmMaterialStockMapper.updateWmMaterialStock(stock);
                } else {
                    stock.setQuantityOnhand(quantity);
                    wmMaterialStockMapper.insertWmMaterialStock(stock);// 新增库存记录
                }
                wmTransaction.setMaterialStockId(stock.getMaterialStockId());
                wmTransaction.setTransactionQuantity(quantity);// 事务数量
                wmTransactionMapper.insertWmTransaction(wmTransaction);
            }
        } catch (BusinessException e) {
            log.error("库存处理失败，异常信息: {}", e.getMessage());
            throw new RuntimeException(e);
        } finally {
            // 释放锁
            redissonUtil.releaseLock(key);
        }
        // 日志记录
        recordLog(wmTransaction, stock, oldQuantity);
        return wmTransaction;
    }

    /**
     * 异步记录日志
     */
    private void recordLog(WmTransaction wmTransaction, WmMaterialStock ms, BigDecimal oldQuantity) {
        RecordStockLogDTO dto = new RecordStockLogDTO();
        dto.setWmMaterialStock(ms);
        dto.setChangeQuantity(wmTransaction.getTransactionQuantity());
        dto.setSourceDocCode(wmTransaction.getSourceDocCode());
        dto.setSourceDocId(wmTransaction.getSourceDocId());
        dto.setSourceDocType(wmTransaction.getSourceDocType());
        dto.setSourceLineId(wmTransaction.getSourceDocLineId());
        dto.setSourceDocName(wmTransaction.getSourceDocName());
        dto.setOldQuantity(oldQuantity);
        dto.setLogChangeTypeEnum(wmTransaction.getTransactionFlag() > 0 ? LogChangeTypeEnum.INBOUND : LogChangeTypeEnum.OUTBOUND);
        // flag，正数：入库，负数：出库
        this.asyncService.recordStockLog(dto);
    }


    private void validate(WmTransaction transaction) {
        if (StringUtils.isNull(transaction.getTransactionType())) {
            throw new BusinessException("库存事务不能为空");
        }

        if (StringUtils.isNull(transaction.getTransactionQuantity())) {
            throw new BusinessException("事务数量不能为空");
        }

        if (StringUtils.isNull(transaction.getSourceDocCode())) {
            throw new BusinessException("来源单据号不能为空");
        }

        if (StringUtils.isNull(transaction.getSourceDocLineId())) {
            throw new BusinessException("来源单据行ID不能为空");
        }

        if (StringUtils.isNull(transaction.getTransactionDate())) {
            transaction.setTransactionDate(new Date());
        }
    }

    /**
     * 初始化新增库存记录信息
     *
     * @param transaction 事务
     * @param stock       库存记录
     */
    public void initStock(WmTransaction transaction, WmMaterialStock stock) {
        // 如果事务中存在库存记录id，通过库存记录将相关信息赋值
        // 不存在库存记录信息 需要根据物料产品查询一系列信息赋值
        if (StringUtils.isNotNull(transaction.getMaterialStockId())) {
            // 查询库存记录
            WmMaterialStock st = wmMaterialStockMapper.selectWmMaterialStockByMaterialStockId(transaction.getMaterialStockId());
            // 给新增库存记录赋值
            BeanUtils.copyProperties(st, stock);
        } else {
            // 查询物料产品根据事务中存取得id
            MdItem item = mdItemMapper.selectMdItemById(transaction.getItemId());
            stock.setItemTypeId(item.getItemTypeId());// 物料类型ID
            BeanUtils.copyProperties(transaction, stock);
        }
    }

    /**
     * 生成分布式锁使用的key
     */
    private String generateKey(WmMaterialStock stock) {
        StringBuilder stringBuilder = new StringBuilder();
        // 物料id
        Optional.ofNullable(stock.getItemId()).ifPresent(stringBuilder::append);
        // 仓库id
        Optional.ofNullable(stock.getWarehouseId()).ifPresent(stringBuilder::append);
        // 库区id
        Optional.ofNullable(stock.getLocationId()).ifPresent(stringBuilder::append);
        // 库位id
        Optional.ofNullable(stock.getAreaId()).ifPresent(stringBuilder::append);
        // 供应商id
        Optional.ofNullable(stock.getVendorId()).ifPresent(stringBuilder::append);
        // 生产工单id
        Optional.ofNullable(stock.getWorkorderId()).ifPresent(stringBuilder::append);
        // 生产工单编码
        if (StringUtils.isNotBlank(stock.getWorkorderCode())) {
            stringBuilder.append(stock.getWorkorderCode());
        }
        // 单位
        if (StringUtils.isNotBlank(stock.getUnitOfMeasure())) {
            stringBuilder.append(stock.getUnitOfMeasure());
        }
        // 入库批次号
        if (StringUtils.isNotBlank(stock.getBatchCode())) {
            stringBuilder.append(stock.getBatchCode());
        }
        // 缩短字符串
        String md5Str = DigestUtils.md5DigestAsHex(stringBuilder.toString().getBytes());
        return PREFIX_LOCK + md5Str + stock.getItemId();

    }

    /**
     * 查询库存事务
     *
     * @param transactionId 库存事务主键
     * @return 库存事务
     */
    @Override
    public WmTransaction selectWmTransactionByTransactionId(Long transactionId) {
        return wmTransactionMapper.selectWmTransactionByTransactionId(transactionId);
    }

    /**
     * 查询库存事务列表
     *
     * @param wmTransaction 库存事务
     * @return 库存事务
     */
    @Override
    public List<WmTransaction> selectWmTransactionList(WmTransaction wmTransaction) {
        return wmTransactionMapper.selectWmTransactionList(wmTransaction);
    }

    /**
     * 新增库存事务
     *
     * @param wmTransaction 库存事务
     * @return 结果
     */
    @Override
    public int insertWmTransaction(WmTransaction wmTransaction) {
        wmTransaction.setCreateTime(DateUtils.getNowDate());
        return wmTransactionMapper.insertWmTransaction(wmTransaction);
    }

    /**
     * 修改库存事务
     *
     * @param wmTransaction 库存事务
     * @return 结果
     */
    @Override
    public int updateWmTransaction(WmTransaction wmTransaction) {
        wmTransaction.setUpdateTime(DateUtils.getNowDate());
        return wmTransactionMapper.updateWmTransaction(wmTransaction);
    }

    /**
     * 批量删除库存事务
     *
     * @param transactionIds 需要删除的库存事务主键
     * @return 结果
     */
    @Override
    public int deleteWmTransactionByTransactionIds(Long[] transactionIds) {
        return wmTransactionMapper.deleteWmTransactionByTransactionIds(transactionIds);
    }

    /**
     * 删除库存事务信息
     *
     * @param transactionId 库存事务主键
     * @return 结果
     */
    @Override
    public int deleteWmTransactionByTransactionId(Long transactionId) {
        return wmTransactionMapper.deleteWmTransactionByTransactionId(transactionId);
    }
}

