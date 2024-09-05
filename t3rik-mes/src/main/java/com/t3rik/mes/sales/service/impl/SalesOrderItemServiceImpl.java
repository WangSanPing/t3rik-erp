package com.t3rik.mes.sales.service.impl;

import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.t3rik.mes.sales.mapper.SalesOrderItemMapper;
import com.t3rik.mes.sales.domain.SalesOrderItem;
import com.t3rik.mes.sales.service.ISalesOrderItemService;

/**
 * 销售订单产品列Service业务层处理
 *
 * @author t3rik
 * @date 2024-08-29
 */
@Service
public class SalesOrderItemServiceImpl  extends ServiceImpl<SalesOrderItemMapper, SalesOrderItem>  implements ISalesOrderItemService
{
    @Autowired
    private SalesOrderItemMapper salesOrderItemMapper;

}
