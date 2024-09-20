package com.t3rik.mes.api.service;

import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.mes.sales.domain.SalesOrder;
import com.t3rik.mes.sales.domain.SalesOrderItem;

import java.util.List;

/**
 * MES工单Service接口
 */
public interface IMesWorkOrderService {
    public AjaxResult pushMesWorkOrder(SalesOrder salesOrder);
}
