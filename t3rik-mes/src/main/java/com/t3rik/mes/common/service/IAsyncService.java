package com.t3rik.mes.common.service;

import com.t3rik.mes.pro.domain.ProClientOrderItem;

import java.util.List;

/**
 * 异步共通类
 *
 * @author t3rik
 * @date 2024/6/3 22:37
 */
public interface IAsyncService {

    /**
     * 更新产品bom列表的级别
     *
     * @param itemId              要更新的产品id
     * @param clientOrderItemList 对应该产品的bom列表
     */
    void updateItemBomAndLevel(Long itemId, List<ProClientOrderItem> clientOrderItemList);

    /**
     * 更新单位到redis
     */
    void updateUnitMeasuresToRedis();

    /**
     * 更新单位到redis
     * 可以选择是否需要先删除
     */
    void updateUnitMeasuresToRedis(Boolean delete, List<String> keys);

    /**
     * 删除redis种的单位
     */
    void deleteUnitMeasuresToRedis(List<String> keys);

    /**
     * 记录库存变化日志
     */
    void recordStockLog(String message);
}
