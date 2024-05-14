package com.t3rik.mes.wm.service.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.t3rik.common.exception.BusinessException;
import com.t3rik.common.utils.DateUtils;
import com.t3rik.common.utils.StringUtils;
import com.t3rik.mes.md.domain.MdItem;
import com.t3rik.mes.md.mapper.MdItemMapper;
import com.t3rik.mes.wm.domain.WmMaterialStock;
import com.t3rik.mes.wm.mapper.WmMaterialStockMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.t3rik.mes.wm.mapper.WmTransactionMapper;
import com.t3rik.mes.wm.domain.WmTransaction;
import com.t3rik.mes.wm.service.IWmTransactionService;

/**
 * 库存事务Service业务层处理
 *
 * @author yinjinlu
 * @date 2022-05-24
 */
@Service
public class WmTransactionServiceImpl implements IWmTransactionService
{
    @Autowired
    private WmTransactionMapper wmTransactionMapper;

    @Autowired
    private WmMaterialStockMapper wmMaterialStockMapper;

    @Autowired
    private MdItemMapper mdItemMapper;

    @Override
    public synchronized WmTransaction processTransaction(WmTransaction wmTransaction) {
        //声明库存记录
        WmMaterialStock stock = new WmMaterialStock();
        //校验传参数是否为空
        validate(wmTransaction);
        //初始化赋值声明库存记录信息
        initStock(wmTransaction,stock);
        //查询库存记录通过构建好的库存记录
        WmMaterialStock ms =wmMaterialStockMapper.loadMaterialStock(stock);
        //扣减或者新增数量 = 事务数量/事务方向
        BigDecimal quantity = wmTransaction.getTransactionQuantity().multiply(new BigDecimal(wmTransaction.getTransactionFlag()));
        if(StringUtils.isNotNull(ms)){
            //MS已存在扣减库存/添加库存
            BigDecimal resultQuantity =ms.getQuantityOnhand().add(quantity);
            //检查库存量是否充足
            if(wmTransaction.isStorageCheckFlag() && resultQuantity.compareTo(new BigDecimal(0))<0){
                throw new BusinessException("库存数量不足！");
            }
            //更新在库数量
            stock.setQuantityOnhand(resultQuantity);
            stock.setMaterialStockId(ms.getMaterialStockId());
            wmMaterialStockMapper.updateWmMaterialStock(stock);
        }else {
            //MS不存在
            stock.setQuantityOnhand(quantity);
            wmMaterialStockMapper.insertWmMaterialStock(stock);//新增库存记录
        }
        wmTransaction.setMaterialStockId(stock.getMaterialStockId());
        wmTransaction.setTransactionQuantity(quantity);//事务数量
        wmTransactionMapper.insertWmTransaction(wmTransaction);
        return wmTransaction;
    }


    private void validate(WmTransaction transaction){
        if(StringUtils.isNull(transaction.getTransactionType())){
            throw new BusinessException("库存事务不能为空");
        }

        if(StringUtils.isNull(transaction.getTransactionQuantity())){
            throw new BusinessException("事务数量不能为空");
        }

        if(StringUtils.isNull(transaction.getSourceDocCode())){
            throw new BusinessException("来源单据号不能为空");
        }

        if(StringUtils.isNull(transaction.getSourceDocLineId())){
            throw new BusinessException("来源单据行ID不能为空");
        }

        if(StringUtils.isNull(transaction.getTransactionDate())){
            transaction.setTransactionDate(new Date());
        }
    }

    /**
     * 初始化新增库存记录信息
     * @param transaction 事务
     * @param stock 库存记录
     */
    public void initStock(WmTransaction transaction,WmMaterialStock stock){
        //如果事务中存在库存记录id，通过库存记录将相关信息赋值
        //不存在库存记录信息 需要根据物料产品查询一系列信息赋值
        if(StringUtils.isNotNull(transaction.getMaterialStockId())){
            //查询库存记录
            WmMaterialStock st = wmMaterialStockMapper.selectWmMaterialStockByMaterialStockId(transaction.getMaterialStockId());
            //给新增库存记录赋值
            BeanUtils.copyProperties(st,stock);
        }else{
            //查询物料产品根据事务中存取得id
            MdItem item =mdItemMapper.selectMdItemById(transaction.getItemId());
            stock.setItemTypeId(item.getItemTypeId());//物料类型ID
            stock.setItemId(transaction.getItemId());
            stock.setItemCode(transaction.getItemCode());
            stock.setItemName(transaction.getItemName());
            stock.setSpecification(transaction.getSpecification());
            stock.setUnitOfMeasure(transaction.getUnitOfMeasure());
            stock.setBatchCode(transaction.getBatchCode());
            stock.setWarehouseId(transaction.getWarehouseId());
            stock.setWarehouseCode(transaction.getWarehouseCode());
            stock.setWarehouseName(transaction.getWarehouseName());
            stock.setLocationId(transaction.getLocationId());
            stock.setLocationCode(transaction.getLocationCode());
            stock.setLocationName(transaction.getLocationName());
            if(StringUtils.isNotNull(transaction.getAreaId())){
                stock.setAreaId(transaction.getAreaId());
                stock.setAreaCode(transaction.getAreaCode());
                stock.setAreaName(transaction.getAreaName());
            }
            if(StringUtils.isNotNull(transaction.getVendorId())){//供应商ID
                stock.setVendorId(transaction.getVendorId());
                stock.setVendorCode(transaction.getVendorCode());
                stock.setVendorName(transaction.getVendorName());
                stock.setVendorNick(transaction.getVendorNick());
            }
            //使用库存事务日期初始化入库日期
            //一般在入库的时候才会涉及到materialStock的新增，出库的时候都是出的现有库存
            if(StringUtils.isNotNull(transaction.getRecptDate())){
                stock.setRecptDate(transaction.getRecptDate());
            }else{
                stock.setRecptDate(new Date());
            }

            //使用库存事务上的生产工单初始化库存记录上的生产工单，以支持某些情况下库存需要标记生产工单的情况
            if(StringUtils.isNotNull(transaction.getWorkorderId())){
                stock.setWorkorderId(transaction.getWorkorderId());
                stock.setWorkorderCode(transaction.getWorkorderCode());
            }
            stock.setExpireDate(transaction.getExpireDate());//库存有效期
        }
    }


    /**
     * 查询库存事务
     *
     * @param transactionId 库存事务主键
     * @return 库存事务
     */
    @Override
    public WmTransaction selectWmTransactionByTransactionId(Long transactionId)
    {
        return wmTransactionMapper.selectWmTransactionByTransactionId(transactionId);
    }

    /**
     * 查询库存事务列表
     *
     * @param wmTransaction 库存事务
     * @return 库存事务
     */
    @Override
    public List<WmTransaction> selectWmTransactionList(WmTransaction wmTransaction)
    {
        return wmTransactionMapper.selectWmTransactionList(wmTransaction);
    }

    /**
     * 新增库存事务
     *
     * @param wmTransaction 库存事务
     * @return 结果
     */
    @Override
    public int insertWmTransaction(WmTransaction wmTransaction)
    {
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
    public int updateWmTransaction(WmTransaction wmTransaction)
    {
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
    public int deleteWmTransactionByTransactionIds(Long[] transactionIds)
    {
        return wmTransactionMapper.deleteWmTransactionByTransactionIds(transactionIds);
    }

    /**
     * 删除库存事务信息
     *
     * @param transactionId 库存事务主键
     * @return 结果
     */
    @Override
    public int deleteWmTransactionByTransactionId(Long transactionId)
    {
        return wmTransactionMapper.deleteWmTransactionByTransactionId(transactionId);
    }
}
