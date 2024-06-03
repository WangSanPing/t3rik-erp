package com.t3rik.mes.pro.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.t3rik.common.constant.UserConstants;
import com.t3rik.common.enums.mes.*;
import com.t3rik.common.utils.StringUtils;
import com.t3rik.mes.common.service.IAsyncService;
import com.t3rik.mes.md.domain.MdProductBom;
import com.t3rik.mes.md.service.IMdProductBomService;
import com.t3rik.mes.pro.domain.ProClientOrder;
import com.t3rik.mes.pro.domain.ProClientOrderItem;
import com.t3rik.mes.pro.domain.ProWorkorder;
import com.t3rik.mes.pro.domain.ProWorkorderBom;
import com.t3rik.mes.pro.mapper.ProClientOrderMapper;
import com.t3rik.mes.pro.service.IProClientOrderItemService;
import com.t3rik.mes.pro.service.IProClientOrderService;
import com.t3rik.mes.pro.service.IProWorkorderBomService;
import com.t3rik.mes.pro.service.IProWorkorderService;
import com.t3rik.system.strategy.AutoCodeUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 客户订单Service业务层处理
 *
 * @author t3rik
 * @date 2024-05-01
 */
@Service
public class ProClientOrderServiceImpl extends ServiceImpl<ProClientOrderMapper, ProClientOrder> implements IProClientOrderService {


    @Resource
    private IProClientOrderItemService clientOrderItemService;
    @Resource
    private IProWorkorderService proWorkorderService;
    @Resource
    private IProWorkorderBomService workorderBomService;
    @Resource
    private IMdProductBomService productBomService;
    @Resource
    private IAsyncService asyncService;
    @Resource
    private AutoCodeUtil autoCodeUtil;

    /**
     * 保存客户订单
     */
    @Transactional
    @Override
    public void saveClientOrder(ProClientOrder clientOrder) {
        // 保存客户订单
        this.save(clientOrder);
        // 查询当前产品是否存在bom组合,如果存在,写入到客户订单物料表中
        List<MdProductBom> productBoms = this.productBomService.lambdaQuery().
                eq(MdProductBom::getItemId, clientOrder.getProductId())
                .list();
        // 如果存在bom组合,写入到客户订单物料表中
        if (CollectionUtil.isNotEmpty(productBoms)) {
            List<ProClientOrderItem> proClientOrderItems = buildClientOrderItems(clientOrder, productBoms);
            // 保存数据
            this.clientOrderItemService.saveBatch(proClientOrderItems);
        }
    }


    /**
     * 构建客户订单子项
     */
    @NotNull
    private List<ProClientOrderItem> buildClientOrderItems(@NotNull ProClientOrder clientOrder, @NotNull List<MdProductBom> productBoms) {
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
            orderItem.setClientOrderId(clientOrder.getClientOrderId());
            // 如果级别为空 并且级别小于等于4级,才会添加级别,超出的数据级别为空
            if (StringUtils.isBlank(productBom.getLevel()) && levelIndex.get() <= 4) {
                // 添加级别
                orderItem.setLevel(String.valueOf(levelIndex.getAndAdd(1)));
            }
            return orderItem;
        }).toList();
    }

    /**
     * 生成生产订单
     */
    @Transactional
    @Override
    public ProWorkorder generateWorkOrder(ProClientOrder clientOrder) {
        // 生产订单
        ProWorkorder workorder = buildWorkOrder(clientOrder);
        // 保存生成工单
        this.proWorkorderService.save(workorder);
        // 查询对应的客户订单子项
        List<ProClientOrderItem> clientOrderItems =
                this.clientOrderItemService.lambdaQuery()
                        .eq(ProClientOrderItem::getClientOrderId, clientOrder.getClientOrderId())
                        .list();
        // 构建工单相应的bom列表
        List<ProWorkorderBom> workorderBomList = buildWorkBomList(workorder, clientOrderItems);
        // 保存工单相应的bom列表
        this.workorderBomService.saveBatch(workorderBomList);
        // 回写客户订单
        ProClientOrder proClientOrder = buildClientOrder(clientOrder, workorder);
        // 更新客户订单
        this.updateById(proClientOrder);
        // 回写客户订单物料信息
        this.clientOrderItemService.lambdaUpdate()
                .ge(ProClientOrderItem::getClientOrderId, clientOrder.getClientOrderId())
                .set(ProClientOrderItem::getWorkorderId, workorder.getWorkorderId())
                .set(ProClientOrderItem::getWorkorderCode, workorder.getWorkorderCode())
                .set(ProClientOrderItem::getWorkorderName, workorder.getWorkorderName())
                .update();
        // 异步回写产品对应的bom列表
        this.asyncService.updateItemBomAndLevel(clientOrder.getProductId(), clientOrderItems);
        return workorder;
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


    /**
     * 构建回写客户订单
     */
    private ProClientOrder buildClientOrder(@NotNull ProClientOrder clientOrder, @NotNull ProWorkorder workorder) {
        ProClientOrder clientOrderUpdate = new ProClientOrder();
        clientOrderUpdate.setClientOrderId(clientOrder.getClientOrderId());
        clientOrderUpdate.setWorkorderId(workorder.getWorkorderId());
        clientOrderUpdate.setWorkorderCode(workorder.getWorkorderCode());
        clientOrderUpdate.setWorkorderName(workorder.getWorkorderName());
        // 订单状态变为已生成生成订单
        clientOrderUpdate.setStatus(ClientOrderStatusEnum.WORK_ORDER_FINISHED.getCode());
        return clientOrderUpdate;
    }


    /**
     * 构建生产订单
     */
    private @NotNull ProWorkorder buildWorkOrder(@NotNull ProClientOrder clientOrder) {
        ProWorkorder workorder = new ProWorkorder();
        BeanUtil.copyProperties(clientOrder, workorder);
        String workorderCode = this.autoCodeUtil.genSerialCode(UserConstants.WORKORDER_CODE, null);
        // 编码code
        workorder.setWorkorderCode(workorderCode);
        // 工单名称
        workorder.setWorkorderName(clientOrder.getProductName());
        // 工单类型 默认自产
        workorder.setWorkorderType(WorkOrderTypeEnum.SELF.getCode());
        // 订单来源  默认客户订单
        workorder.setOrderSource(WorkOrderSourceTypeEnum.ORDER.getCode());
        // 来源单据
        workorder.setSourceCode(clientOrder.getClientOrderCode());
        // 订单状态 默认草稿状态
        workorder.setStatus(WorkOrderStatusEnum.PREPARE.getCode());
        // 需求日期
        workorder.setRequestDate(clientOrder.getDeliveryDate());
        // 默认父订单
        workorder.setParentId(0L);
        // 默认父节点集合
        workorder.setAncestors("0");
        // 订货数量
        workorder.setQuantity(clientOrder.getOrderQuantity());
        // 规格
        workorder.setProductSpc(clientOrder.getProductSpec());
        // 质量要求
        workorder.setRemark(clientOrder.getQualityRequirement());
        return workorder;
    }
}
