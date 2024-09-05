package com.t3rik.mes.sales.service;

import java.util.List;

import com.baomidou.mybatisplus.extension.service.IService;
import com.t3rik.mes.sales.domain.SalesOrder;
import org.apache.ibatis.annotations.Mapper;

/**
 * 销售订单Service接口
 *
 * @author t3rik
 * @date 2024-08-29
 */
public interface ISalesOrderService extends IService<SalesOrder> {

    public void saveOrder(SalesOrder order);

}
