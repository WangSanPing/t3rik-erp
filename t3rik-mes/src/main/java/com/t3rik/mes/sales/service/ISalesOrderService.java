package com.t3rik.mes.sales.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.mes.pro.domain.ProWorkorder;
import com.t3rik.mes.sales.domain.SalesOrder;

/**
 * 销售订单Service接口
 *
 * @author t3rik
 * @date 2024-08-29
 */
public interface ISalesOrderService extends IService<SalesOrder> {

    public void saveOrder(SalesOrder order);

    public  List<ProWorkorder>  execute(SalesOrder order) throws Exception;

    public boolean updateById(SalesOrder salesOrder);

    public StringBuffer removeByIds(List<Long> salesOrderIds);


}
