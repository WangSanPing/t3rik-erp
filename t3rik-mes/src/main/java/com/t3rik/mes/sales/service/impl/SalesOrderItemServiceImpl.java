package com.t3rik.mes.sales.service.impl;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.t3rik.common.constant.UserConstants;
import com.t3rik.common.enums.mes.ItemTypeEnum;
import com.t3rik.common.enums.mes.OrderStatusEnum;
import com.t3rik.common.enums.mes.WorkOrderSourceTypeEnum;
import com.t3rik.common.enums.mes.WorkOrderTypeEnum;
import com.t3rik.common.utils.StringUtils;
import com.t3rik.mes.common.service.IAsyncService;
import com.t3rik.mes.pro.domain.ProClientOrderItem;
import com.t3rik.mes.pro.domain.ProWorkorder;
import com.t3rik.mes.pro.domain.ProWorkorderBom;
import com.t3rik.mes.pro.service.IProClientOrderItemService;
import com.t3rik.mes.pro.service.IProWorkorderBomService;
import com.t3rik.mes.pro.service.IProWorkorderService;
import com.t3rik.mes.sales.domain.SalesOrder;
import com.t3rik.mes.sales.service.ISalesOrderService;
import com.t3rik.mes.wm.domain.WmMaterialStock;
import com.t3rik.mes.wm.service.IWmMaterialStockService;
import com.t3rik.system.strategy.AutoCodeUtil;
import jakarta.annotation.Resource;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.t3rik.mes.sales.mapper.SalesOrderItemMapper;
import com.t3rik.mes.sales.domain.SalesOrderItem;
import com.t3rik.mes.sales.service.ISalesOrderItemService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 销售订单产品列Service业务层处理
 *
 * @author 堇年
 * @date 2024-08-29
 */
@Service
public class SalesOrderItemServiceImpl extends ServiceImpl<SalesOrderItemMapper, SalesOrderItem> implements ISalesOrderItemService {
    @Resource
    private SalesOrderItemMapper salesOrderItemMapper;
    @Resource
    private IWmMaterialStockService wmMaterialStockService;
    @Resource
    private IProWorkorderService proWorkorderService;
    @Resource
    private IProClientOrderItemService clientOrderItemService;
    @Resource
    private IProWorkorderBomService workorderBomService;
    @Resource
    private IAsyncService asyncService;
    @Resource
    private AutoCodeUtil autoCodeUtil;

    @Override
    public List<SalesOrderItem> getItemList(List<SalesOrder> salesOrderList, Long warehouseId) {
        List<SalesOrderItem> items = this.lambdaQuery().eq(SalesOrderItem::getStatus, OrderStatusEnum.WORK_ORDER_FINISHED.getCode()).list();
        List<SalesOrderItem> itemList = new ArrayList<>();
        for (SalesOrder li : salesOrderList) {
            for (SalesOrderItem item : items) {
                // 查询库存
                WmMaterialStock wmMaterialStock = wmMaterialStockService.lambdaQuery()
                        .eq(WmMaterialStock::getItemId, item.getProductId())
                        .eq(WmMaterialStock::getWarehouseId, warehouseId)
                        .one();
                if (wmMaterialStock != null) {
                    item.setStockNumber(wmMaterialStock.getQuantityOnhand());
                } else {
                    item.setStockNumber(new BigDecimal("0"));
                }
                itemList.add(item);
            }
        }
        return items;
    }

    /**
     * 生成生产订单
     */
    @Transactional
    @Override
    public ProWorkorder execute(SalesOrderItem order, SalesOrder salesOrder) throws Exception {
        StringBuilder sb = new StringBuilder();
        //校验是否部生成
//        if (StringUtils.isNotEmpty(order.getWorkorderCode()) || order.getWorkorderId() != null) {
//            sb.append(MessageFormat.format("已经生成过生产订单(生成订单的编号为:{0}),不允许再次生成", order.getSalesOrderItemCode()));
//            return sb;
//        }


        // 生产订单
        ProWorkorder workorder = this.buildWorkOrder(order, salesOrder.getOrderType());
        // 保存生成工单
        this.proWorkorderService.save(workorder);
        // 查询对应的客户订单子项
        List<ProClientOrderItem> clientOrderItems =
                this.clientOrderItemService.lambdaQuery()
                        .eq(ProClientOrderItem::getClientOrderId, order.getSalesOrderItemId())
                        .list();
        // 构建工单相应的bom列表
        List<ProWorkorderBom> workorderBomList = buildWorkBomList(workorder, clientOrderItems);
        // 保存工单相应的bom列表
        this.workorderBomService.saveBatch(workorderBomList);
        // 回写销售订单子项
        this.buildSalesOrder(order, workorder);
        // 回写客户订单物料信息
        this.clientOrderItemService.lambdaUpdate()
                .ge(ProClientOrderItem::getClientOrderId, order.getSalesOrderItemId())
                .set(ProClientOrderItem::getWorkorderId, workorder.getWorkorderId())
                .set(ProClientOrderItem::getWorkorderCode, workorder.getWorkorderCode())
                .set(ProClientOrderItem::getWorkorderName, workorder.getWorkorderName())
                .update();
        // 异步回写产品对应的bom列表
        this.asyncService.updateItemBomAndLevel(order.getProductId(), clientOrderItems);
        sb.append(MessageFormat.format("成功生成生产订单(生成订单的编号为:{0})", order.getSalesOrderItemCode())).append("\n");
        ;
        // 更新销售订单子项
        this.updateById(order);
        return workorder;
    }

    /**
     * 构建生产订单
     */
    private @NotNull
    ProWorkorder buildWorkOrder(@NotNull SalesOrderItem salesOrderItem, String sourceCode) {
        ProWorkorder workorder = new ProWorkorder();
        BeanUtil.copyProperties(salesOrderItem, workorder);
        String workorderCode = this.autoCodeUtil.genSerialCode(UserConstants.WORKORDER_CODE, null);
        // 编码code
        workorder.setWorkorderCode(workorderCode);
        // 工单名称
        workorder.setWorkorderName(salesOrderItem.getProductName());
        // 工单类型 默认自产
        workorder.setWorkorderType(WorkOrderTypeEnum.SELF.getCode());
        // 订单来源  默认客户订单
        workorder.setOrderSource(WorkOrderSourceTypeEnum.ORDER.getCode());
        // 来源单据
        workorder.setSourceCode(sourceCode);
        // 订单状态 默认草稿状态
        workorder.setStatus(OrderStatusEnum.PREPARE.getCode());
        // 需求日期
        workorder.setRequestDate(salesOrderItem.getDeliveryDate());
        // 默认父订单
        workorder.setParentId(0L);
        // 默认父节点集合
        workorder.setAncestors("0");
        // 订货数量
        workorder.setQuantity(salesOrderItem.getSalesOrderQuantity());
        // 规格
        workorder.setProductSpc(salesOrderItem.getProductSpec());
        // 质量要求
        workorder.setRemark(salesOrderItem.getRemark());
        return workorder;
    }

    /**
     * 构建回写销售订单子项
     */
    private void buildSalesOrder(@NotNull SalesOrderItem salesOrderItem, @NotNull ProWorkorder workorder) {
//        SalesOrderItem salesOrderItemUpdate = new SalesOrderItem();
        salesOrderItem.setSalesOrderItemId(salesOrderItem.getSalesOrderItemId());
        salesOrderItem.setWorkorderId(workorder.getWorkorderId());
        salesOrderItem.setWorkorderCode(workorder.getWorkorderCode());
        salesOrderItem.setWorkorderName(workorder.getWorkorderName());
        // 订单状态变为已生成生成订单
        salesOrderItem.setStatus(OrderStatusEnum.WORK_ORDER_FINISHED.getCode());
    }

    /**
     * 构建工单相应的bom列表
     */
    private List<ProWorkorderBom> buildWorkBomList(ProWorkorder workorder, List<ProClientOrderItem> clientOrderItems) {
        // 转化为workOrderBom列表
        return clientOrderItems.stream().map(orderItem -> {
            ProWorkorderBom workorderBom = new ProWorkorderBom();
            BeanUtil.copyProperties(orderItem, workorderBom);
            // 主表id
            workorderBom.setWorkorderId(workorder.getWorkorderId());
            // 规格型号
            workorderBom.setItemSpc(orderItem.getSpecification());
            // 默认物料
            workorderBom.setItemOrProduct(ItemTypeEnum.ITEM.getCode());
            return workorderBom;
        }).toList();
    }
}
