package com.t3rik.mes.common.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import com.alibaba.fastjson2.JSON;
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils;
import com.t3rik.common.constant.RedisConstants;
import com.t3rik.common.core.redis.RedisCache;
import com.t3rik.common.enums.EnableFlagEnum;
import com.t3rik.common.enums.mes.ItemTypeEnum;
import com.t3rik.common.enums.mes.SourceDocTypeEnum;
import com.t3rik.common.exception.BusinessException;
import com.t3rik.common.utils.SecurityUtils;
import com.t3rik.mes.common.dto.RecordStockLogDTO;
import com.t3rik.mes.common.service.IAsyncService;
import com.t3rik.mes.md.domain.MdProductBom;
import com.t3rik.mes.md.domain.MdUnitMeasure;
import com.t3rik.mes.md.service.IMdProductBomService;
import com.t3rik.mes.md.service.IMdUnitMeasureService;
import com.t3rik.mes.pro.domain.ProClientOrderItem;
import com.t3rik.mes.pro.service.IProTaskService;
import com.t3rik.mes.pro.service.IProWorkorderService;
import com.t3rik.mes.wm.domain.WmLogFailure;
import com.t3rik.mes.wm.domain.WmMaterialStockLog;
import com.t3rik.mes.wm.service.IWmLogFailureService;
import com.t3rik.mes.wm.service.IWmMaterialStockLogService;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.Resource;
import org.springframework.retry.annotation.Recover;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 异步共通实现类
 *
 * @author t3rik
 * @date 2024/6/3 23:04
 */
@Service
public class AsyncServiceImpl implements IAsyncService {

    @Resource
    private IMdProductBomService productBomService;
    @Resource
    private RedisCache redisCache;
    @Resource
    private IMdUnitMeasureService unitMeasureService;
    @Resource
    private IWmMaterialStockLogService materialStockLogService;
    @Resource
    private IWmLogFailureService logFailureService;
    @Resource
    private IProWorkorderService workorderService;
    @Resource
    private IProTaskService taskService;

    @PostConstruct
    void initData() {
        this.updateUnitMeasuresToRedis();
    }

    @Async
    @Override
    public void updateUnitMeasuresToRedis() {
        List<MdUnitMeasure> mdUnitMeasures = unitMeasureService.selectMdUnitMeasureList(null);
        if (CollectionUtils.isEmpty(mdUnitMeasures)) {
            return;
        }
        // 先删除，避免写入重复的数据
        this.redisCache.deleteObject(RedisConstants.UNITS_MEASURES_LIST_KEY);
        this.redisCache.setCacheList(RedisConstants.UNITS_MEASURES_LIST_KEY, mdUnitMeasures);
        mdUnitMeasures.forEach(item -> redisCache.setCacheObject(item.getMeasureCode(), item.getMeasureName()));
    }

    /**
     * 更新单位到redis
     * 可以选择是否需要先删除
     *
     * @param delete true代表需要先删除指定的key
     */
    @Async
    @Override
    public void updateUnitMeasuresToRedis(Boolean delete, List<String> keys) {
        // 如果需要删除
        if (delete) {
            this.deleteUnitMeasuresToRedis(keys);
        }
        // 更新单位到redis
        this.updateUnitMeasuresToRedis();
    }

    /**
     * 删除redis中的单位
     */
    @Async
    @Override
    public void deleteUnitMeasuresToRedis(List<String> keys) {
        // 转为可变集合
        ArrayList<String> list = new ArrayList<>(keys);
        // 添加单位集合列表的key，一起删除
        list.add(RedisConstants.UNITS_MEASURES_LIST_KEY);
        this.redisCache.deleteObject(list);
    }

    /**
     * 记录库存变化日志
     * 加入失败补偿
     */
    @Async
    @Retryable(retryFor = RuntimeException.class, maxAttempts = 1)
    @Override
    public void recordStockLog(RecordStockLogDTO dto) {
        WmMaterialStockLog log = new WmMaterialStockLog();
        BeanUtil.copyProperties(dto.getWmMaterialStock(), log, "createBy", "createTime", "updateBy", "updateTime");
        // 变化类型（10:入库, 20:出库, 30:盘点）
        log.setChangeType(dto.getLogChangeTypeEnum().getCode());
        // 变化前库存数量
        log.setBeforeQuantity(dto.getOldQuantity());
        // 变化后库存数量
        log.setAfterQuantity(dto.getWmMaterialStock().getQuantityOnhand());
        // 库存变化数量
        log.setChangeQuantity(dto.getChangeQuantity().abs());
        log.setSourceDocId(dto.getSourceDocId());
        log.setSourceDocCode(dto.getSourceDocCode());
        log.setSourceDocName(dto.getSourceDocName());
        log.setSourceDocType(dto.getSourceDocType());
        log.setSourceLineId(dto.getSourceLineId());
        // 生产工单信息
        if (log.getWorkorderId() != null) {
            Optional.ofNullable(this.workorderService.getById(log.getWorkorderId()))
                    .ifPresent(w -> log.setWorkorderName(w.getWorkorderName()));
        }
        // 单据类型
        SourceDocTypeEnum typeEnum = SourceDocTypeEnum.getEnumByCode(dto.getSourceDocType());
        switch (typeEnum) {
            // 查询责任人
            case ISSUE, RTISSUE, WMWASTE -> {
                Optional.ofNullable(this.taskService.getById(dto.getSourceDocId()))
                        .ifPresent(i -> {
                            log.setOperationUserId(i.getTaskUserId());
                            log.setOperationBy(i.getTaskBy());
                            log.setOperationTime(new Date());
                        });
            }
            default -> {
                log.setOperationUserId(SecurityUtils.getUserId());
                log.setOperationBy(SecurityUtils.getUsername());
                log.setOperationTime(new Date());
            }
        }
        this.materialStockLogService.save(log);
    }

    /**
     * 失败补偿
     */
    @Recover
    public void recordStockLogRecover(RuntimeException e, RecordStockLogDTO dto) {
        WmLogFailure wmLogFailure = new WmLogFailure();
        wmLogFailure.setMaterialStockId(dto.getWmMaterialStock().getMaterialStockId());
        wmLogFailure.setFailureReason(e.getMessage());
        wmLogFailure.setFailureTime(new Date());
        wmLogFailure.setLogData(JSON.toJSONString(dto));
        this.logFailureService.save(wmLogFailure);
    }

    @Async
    @Override
    public void updateItemBomAndLevel(Long itemId, List<ProClientOrderItem> clientOrderItemList) {
        Assert.notNull(itemId, () -> new BusinessException("itemId不能为空"));
        // 判断当前是否存在产品相应的bom列表,如果存在,更新,如果不存在,新增bom列表
        // 转化数据
        List<MdProductBom> productBomData =
                clientOrderItemList.stream().map(orderItem -> {
                    MdProductBom mdProductBom = new MdProductBom();
                    mdProductBom.setItemId(itemId);
                    mdProductBom.setBomItemId(orderItem.getItemId());
                    mdProductBom.setLevel(orderItem.getLevel());
                    mdProductBom.setBomItemCode(orderItem.getItemCode());
                    mdProductBom.setBomItemName(orderItem.getItemName());
                    mdProductBom.setBomItemSpec(orderItem.getSpecification());
                    mdProductBom.setUnitOfMeasure(orderItem.getUnitOfMeasure());
                    mdProductBom.setItemOrProduct(ItemTypeEnum.ITEM.getCode());
                    mdProductBom.setQuantity(orderItem.getQuantity());
                    mdProductBom.setEnableFlag(EnableFlagEnum.YES.getCode());
                    return mdProductBom;
                }).toList();
        // 如果集合不为空,代表已经设置过bom数据
        List<MdProductBom> productBomList = this.productBomService.lambdaQuery().eq(MdProductBom::getItemId, itemId).list();
        if (CollectionUtil.isEmpty(productBomList)) {
            // 新增
            this.productBomService.saveBatch(productBomData);
        } else {
            // 更新,只更新传递过来的数据中的level字段
            this.productBomService.updateItemBomAndLevel(productBomData);
        }
    }
}
