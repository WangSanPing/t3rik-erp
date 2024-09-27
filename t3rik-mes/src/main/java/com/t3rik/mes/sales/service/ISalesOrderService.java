package com.t3rik.mes.sales.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.t3rik.mes.pro.domain.ProWorkorder;
import com.t3rik.mes.sales.domain.SalesOrder;


/**
 * 销售订单Service接口
 *
 * @author t3rik
 * @date 2024-08-29
 */
public interface ISalesOrderService extends IService<SalesOrder> {

    //save
    boolean saveOrder(SalesOrder order);

    //执行
    StringBuilder execute(SalesOrder order) throws Exception;

    //update
    boolean updateSalesOrder(SalesOrder salesOrder);

    //remove
    StringBuilder deleteByIds(List<Long> salesOrderIds);

    //审批
    boolean refuse(SalesOrder salesOrder);


}
