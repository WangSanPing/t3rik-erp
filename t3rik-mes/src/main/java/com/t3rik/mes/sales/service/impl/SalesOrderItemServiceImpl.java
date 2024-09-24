package com.t3rik.mes.sales.service.impl;

import java.util.ArrayList;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.t3rik.mes.sales.domain.SalesOrder;
import com.t3rik.mes.sales.service.ISalesOrderService;
import org.checkerframework.checker.units.qual.A;
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

    @Override
    public List<SalesOrderItem> getItemList(List<SalesOrder> salesOrderList) {
        List<SalesOrderItem> items = this.list();
        List<SalesOrderItem> itemList = new ArrayList<>();
        for (SalesOrder li : salesOrderList) {
            for (SalesOrderItem item : items) {
                if (item.getSalesOrderId().equals(li.getSalesOrderId())) {
                    itemList.add(item);
                }
            }
        }
        return items;
    }
}
