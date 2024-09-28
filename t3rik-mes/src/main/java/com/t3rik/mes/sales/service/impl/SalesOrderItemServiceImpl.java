package com.t3rik.mes.sales.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.t3rik.common.enums.mes.OrderStatusEnum;
import com.t3rik.mes.sales.domain.SalesOrder;
import com.t3rik.mes.wm.domain.WmMaterialStock;
import com.t3rik.mes.wm.service.IWmMaterialStockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.t3rik.mes.sales.mapper.SalesOrderItemMapper;
import com.t3rik.mes.sales.domain.SalesOrderItem;
import com.t3rik.mes.sales.service.ISalesOrderItemService;

/**
 * 销售订单产品列Service业务层处理
 *
 * @author 堇年
 * @date 2024-08-29
 */
@Service
public class SalesOrderItemServiceImpl  extends ServiceImpl<SalesOrderItemMapper, SalesOrderItem>  implements ISalesOrderItemService
{
    @Autowired
    private SalesOrderItemMapper salesOrderItemMapper;
    @Autowired
    private IWmMaterialStockService wmMaterialStockService;


    @Override
    public List<SalesOrderItem> getItemList(List<SalesOrder> salesOrderList,Long warehouseId) {
        List<SalesOrderItem> items = this.list();
        List<SalesOrderItem> itemList = new ArrayList<>();
        for (SalesOrder li : salesOrderList) {
            for (SalesOrderItem item : items) {
                if (item.getSalesOrderId().equals(li.getSalesOrderId())&&li.getStatus().equals(OrderStatusEnum.WORK_ORDER_FINISHED.getCode())) {
                    // 查询库存
                    WmMaterialStock wmMaterialStock = wmMaterialStockService.lambdaQuery()
                            .eq(WmMaterialStock::getItemId,item.getProductId())
                            .eq(WmMaterialStock::getWarehouseId,warehouseId)
                            .one();
                    if(wmMaterialStock!=null){
                        item.setStockNumber(wmMaterialStock.getQuantityOnhand());
                    }else {
                        item.setStockNumber(new BigDecimal("0"));
                    }
                    itemList.add(item);
                }
            }
        }
        return items;
    }
}
