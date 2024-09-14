package com.t3rik.mes.sales.service.impl;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.t3rik.common.utils.StringUtils;
import com.t3rik.mes.md.domain.MdProductBom;
import com.t3rik.mes.md.service.IMdProductBomService;
import com.t3rik.mes.pro.domain.ProClientOrder;
import com.t3rik.mes.pro.domain.ProClientOrderItem;
import com.t3rik.mes.pro.service.IProClientOrderItemService;
import com.t3rik.mes.sales.domain.SalesOrderItem;
import com.t3rik.mes.sales.service.ISalesOrderItemService;
import jakarta.annotation.Resource;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.t3rik.mes.sales.mapper.SalesOrderMapper;
import com.t3rik.mes.sales.domain.SalesOrder;
import com.t3rik.mes.sales.service.ISalesOrderService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 销售订单Service业务层处理
 *
 * @author t3rik
 * @date 2024-08-29
 */
@Service
public class SalesOrderServiceImpl  extends ServiceImpl<SalesOrderMapper, SalesOrder>  implements ISalesOrderService
{
    @Autowired
    private SalesOrderMapper salesOrderMapper;
    @Autowired
    private ISalesOrderItemService salesOrderItemService;
    @Autowired
    private IMdProductBomService productBomService;

    @Autowired
    private IProClientOrderItemService clientOrderItemService;

    @Override
    @Transactional
    public void saveOrder(SalesOrder salesOrder){
        if(salesOrder.getSalesOrderItemList().size()>0){
            this.save(salesOrder);
            for(SalesOrderItem obj:salesOrder.getSalesOrderItemList()){
                obj.setSalesOrderId(salesOrder.getSalesOrderId());
                obj.setSalesOrderCode(salesOrder.getSalesOrderCode());
                obj.setOweQty(obj.getSalesOrderQuantity());
                salesOrderItemService.save(obj);
                // 查询当前产品是否存在bom组合,如果存在,写入到客户订单物料表中
                List<MdProductBom> productBoms = productBomService.lambdaQuery().
                        eq(MdProductBom::getItemId, obj.getProductId())
                        .list();
                // 如果存在bom组合,写入到客户订单物料表中
                if (CollectionUtil.isNotEmpty(productBoms)) {
                    salesOrderItemService.save(obj);
                    List<ProClientOrderItem> proClientOrderItems = buildClientOrderItems(obj, productBoms);
                    // 保存数据
                    this.clientOrderItemService.saveBatch(proClientOrderItems);
                }
            }
        }else{
            this.save(salesOrder);
        }
    }



    /**
     * 构建客户订单子项
     */
    @NotNull
    private List<ProClientOrderItem> buildClientOrderItems(@NotNull SalesOrderItem salesOrderItem, @NotNull List<MdProductBom> productBoms) {
        // 获取物料层级
        AtomicInteger levelIndex = new AtomicInteger(1);
        return productBoms.stream().map(productBom -> {
            ProClientOrderItem orderItem = new ProClientOrderItem();
            // 需求数量不复制
            BeanUtil.copyProperties(productBom, orderItem, "quantity");
            // 物料主键
            orderItem.setItemId(productBom.getBomItemId());
            orderItem.setItemCode(productBom.getBomItemCode());
            orderItem.setItemName(productBom.getBomItemName());
            orderItem.setSpecification(productBom.getBomItemSpec());
            // 客户订单表主键
            orderItem.setClientOrderId(salesOrderItem.getSalesOrderItemId());
            // 如果级别为空 并且级别小于等于4级,才会添加级别,超出的数据级别为空
            if (StringUtils.isBlank(productBom.getLevel()) && levelIndex.get() <= 4) {
                // 添加级别
                orderItem.setLevel(String.valueOf(levelIndex.getAndAdd(1)));
            }
            return orderItem;
        }).toList();
    }
}
