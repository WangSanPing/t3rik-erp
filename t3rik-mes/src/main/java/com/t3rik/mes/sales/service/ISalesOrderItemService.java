package com.t3rik.mes.sales.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.t3rik.mes.pro.domain.ProWorkorder;
import com.t3rik.mes.sales.domain.SalesOrder;
import com.t3rik.mes.sales.domain.SalesOrderItem;

/**
 * 销售订单产品列Service接口
 *
 * @author 堇年
 * @date 2024-08-29
 */
public interface ISalesOrderItemService extends IService<SalesOrderItem> {

    List<SalesOrderItem> getItemList(List<SalesOrder> salesOrderList,Long warehouseId);

    //销售订单详细列表
    ProWorkorder   execute(SalesOrderItem order, SalesOrder salesOrder) throws Exception;
}
