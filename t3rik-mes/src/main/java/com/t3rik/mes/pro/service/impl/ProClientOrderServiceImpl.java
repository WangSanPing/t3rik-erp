package com.t3rik.mes.pro.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.t3rik.common.enums.mes.ClientOrderStatusEnum;
import com.t3rik.common.enums.mes.WorkOrderSourceTypeEnum;
import com.t3rik.common.enums.mes.WorkOrderStatusEnum;
import com.t3rik.common.enums.mes.WorkOrderTypeEnum;
import com.t3rik.mes.pro.domain.ProClientOrder;
import com.t3rik.mes.pro.domain.ProClientOrderItem;
import com.t3rik.mes.pro.domain.ProWorkorder;
import com.t3rik.mes.pro.mapper.ProClientOrderMapper;
import com.t3rik.mes.pro.service.IProClientOrderItemService;
import com.t3rik.mes.pro.service.IProClientOrderService;
import com.t3rik.mes.pro.service.IProWorkorderService;
import com.t3rik.system.strategy.AutoCodeUtil;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 客户订单Service业务层处理
 *
 * @author t3rik
 * @date 2024-05-01
 */
@Service
public class ProClientOrderServiceImpl extends ServiceImpl<ProClientOrderMapper, ProClientOrder> implements IProClientOrderService {

    @Resource
    private ProClientOrderMapper proClientOrderMapper;
    @Resource
    private IProClientOrderItemService clientOrderItemService;
    @Resource
    private IProWorkorderService proWorkorderService;
    @Resource
    private AutoCodeUtil autoCodeUtil;
    /**
     * 生产订单编码规则code
     */
    private static final String WORKORDER_RULE_CODE = "WORKORDER_CODE";


    @Transactional
    @Override
    public Long generateWorkOrder(ProClientOrder clientOrder) {
        // 生产订单
        ProWorkorder workorder = buildWorkOrder(clientOrder);
        // 保存生成工单
        this.proWorkorderService.save(workorder);
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
        return workorder.getWorkorderId();
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
        String workorderCode = this.autoCodeUtil.genSerialCode(WORKORDER_RULE_CODE, null);
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
