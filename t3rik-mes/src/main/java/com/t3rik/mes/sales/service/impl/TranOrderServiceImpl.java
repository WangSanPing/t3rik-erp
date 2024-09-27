package com.t3rik.mes.sales.service.impl;

import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.enums.mes.OrderStatusEnum;
import com.t3rik.mes.md.domain.MdProductBom;
import com.t3rik.mes.pro.domain.ProClientOrder;
import com.t3rik.mes.pro.domain.ProClientOrderItem;
import com.t3rik.mes.sales.domain.SalesOrderItem;
import com.t3rik.mes.sales.domain.TranOrderLine;
import com.t3rik.mes.sales.service.ISalesOrderItemService;
import com.t3rik.mes.sales.service.ITranOrderLineService;
import com.t3rik.system.strategy.AutoCodeUtil;
import org.apache.commons.collections.CollectionUtils;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.t3rik.mes.sales.mapper.TranOrderMapper;
import com.t3rik.mes.sales.domain.TranOrder;
import com.t3rik.mes.sales.service.ITranOrderService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 销售送货单Service业务层处理
 *
 * @author t3rik
 * @date 2024-09-09
 */
@Service
public class TranOrderServiceImpl extends ServiceImpl<TranOrderMapper, TranOrder> implements ITranOrderService {
    @Autowired
    private TranOrderMapper TranOrderMapper;
    @Autowired
    private ITranOrderLineService tranOrderLineService;
    @Autowired
    private AutoCodeUtil autoCodeUtil;
    @Autowired
    private ISalesOrderItemService salesOrderItemService;

    @Transactional
    @Override
    public void saveTranOrder(TranOrder tranOrder) {
        if (tranOrder.getTranOrderLineList().size() > 0) {
            this.save(tranOrder);
            this.saveLine(tranOrder);
        } else {
            this.save(tranOrder);
        }
    }

    //保存子项
    private void saveLine(TranOrder tranOrder) {
        for (TranOrderLine obj : tranOrder.getTranOrderLineList()) {
            obj.setTranCode(autoCodeUtil.genSerialCode("TRAN_CODE", null));
            obj.setTranOrderId(tranOrder.getTranOrderId());
            obj.setTranOrderCode(tranOrder.getTranOrderCode());
            obj.setBusMan(tranOrder.getBusMan());
            obj.setFollowerMan(tranOrder.getFollowerMan());
            if (tranOrder.getStatus() != null) {
                obj.setStatus(tranOrder.getStatus());
            }
        }
        tranOrderLineService.saveBatch(tranOrder.getTranOrderLineList());
    }

    @Transactional
    @Override
    public StringBuffer execute(TranOrder tranOrder) throws Exception {
        List<Long> orderIds = tranOrder.getTranOrderLineList().stream().map(o -> o.getSalesOrderId()).collect(Collectors.toList());
        List<TranOrderLine> tranOrderLineList = tranOrder.getTranOrderLineList();
        List<SalesOrderItem> salesOrderItems = salesOrderItemService.listByIds(orderIds);
        Map<Long, SalesOrderItem> salesOrderMap = salesOrderItems.stream().collect
                ((Collectors.toMap(SalesOrderItem::getSalesOrderItemId, salesOrderItem -> salesOrderItem)));
        List<SalesOrderItem> salesOrderItemList = new ArrayList<>();
        StringBuffer sb = new StringBuffer();
        //校验是否全部生成
        List<TranOrderLine> newLineList = new ArrayList<>();
        //更新销售订单明细列
        tranOrderLineList.stream().forEach(f -> {
            try {
                if (f.getStatus().equals(OrderStatusEnum.APPROVED.getCode())) {
                    sb.append(MessageFormat.format("单据已经执行过了(订单的编号为:{0})", f.getTranCode()));
                    return;
                }
                SalesOrderItem orderItem = salesOrderMap.get(f.getSalesOrderId());
                orderItem.setSaleQty(orderItem.getSaleQty().add(f.getSaleSgqty()));
                orderItem.setOweQty(orderItem.getSaleQty().subtract(f.getSaleSgqty()));
                salesOrderItemList.add(orderItem);
                if (orderItem.getSaleQty().compareTo(orderItem.getSalesOrderQuantity()) == 0) {
                    orderItem.setStatus(OrderStatusEnum.FINISHED.getCode());
                }
                f.setStatus(OrderStatusEnum.APPROVED.getCode());
                newLineList.add(f);
            } catch (Exception e) {
                sb.append("送货单" + f.getTranCode() + "出现错误:" + e.getMessage() + "\n");
            }
        });
        salesOrderItemService.updateBatchById(salesOrderItemList);
        // 校验子单是否全部生成生产订单
        List<TranOrderLine> nullWorkList = newLineList.stream().filter(f -> !f.getStatus().equals(OrderStatusEnum.APPROVED.getCode())).toList();
        if (CollectionUtil.isEmpty(nullWorkList)) {
            tranOrder.setStatus(OrderStatusEnum.APPROVED.getCode());
            this.updateById(tranOrder);
            //更新送货单状态
            tranOrderLineService.updateBatchById(tranOrderLineList);
        }
        sb.append("成功" + salesOrderItemList.size() + "条数据");
        return sb;
    }

    @Override
    public boolean updateTranOrder(TranOrder tranOrder) {
        if (tranOrder.getStatus().equals(OrderStatusEnum.REFUSE.getCode())) {
            tranOrder.setStatus(OrderStatusEnum.APPROVING.getCode());
        }
        this.updateById(tranOrder);
        if (tranOrder.getTranOrderLineList().size() > 0) {
            List<TranOrderLine> itemList = tranOrderLineService.lambdaQuery().eq(TranOrderLine::getTranOrderId, tranOrder.getTranOrderId()).list();
            //删除原来的数据
            this.tranOrderLineService.removeByIds(itemList);
            this.saveLine(tranOrder);
            return true;
        }
        return false;
    }

    @Override
    public StringBuffer deleteByIds(List<Long> tranOrderIds) {
        List<TranOrder> tranOrders = this.listByIds(tranOrderIds);
        StringBuffer sb = new StringBuffer();
        for (TranOrder li : tranOrders) {
            List<TranOrderLine> lineList = tranOrderLineService.lambdaQuery().eq(TranOrderLine::getTranOrderId, li.getTranOrderId()).list();
            if (lineList.size() > 0) {
                sb.append("销售送货单" + li.getTranOrderCode() + "下还有未删除的清单，不允许删除" + "\n");
                tranOrders.remove(li.getTranOrderId());
            }
        }
        this.removeByIds(tranOrderIds);
        return sb;
    }


    @Override
    public boolean refuse(TranOrder tranOrder) {
        if (tranOrder.getStatus().equals(OrderStatusEnum.PREPARE.getCode())) {
            tranOrder.setStatus(OrderStatusEnum.CONFIRMED.getCode());
        } else if (tranOrder.getStatus().equals(OrderStatusEnum.CONFIRMED.getCode())) {
            tranOrder.setStatus(OrderStatusEnum.APPROVING.getCode());
        } else if (tranOrder.getStatus().equals(OrderStatusEnum.APPROVING.getCode())) {
            tranOrder.setStatus(OrderStatusEnum.REFUSE.getCode());
        } else {
            return false;
        }
        this.updateById(tranOrder);
        // 查询是否已经添加销列
        List<TranOrderLine> itemList = tranOrder.getTranOrderLineList();
        // 审批拒绝/提交
        this.updateById(tranOrder);
        if (CollectionUtils.isNotEmpty(itemList)) {
            itemList.forEach(object -> object.setStatus(tranOrder.getStatus()));
        }
        return tranOrderLineService.updateBatchById(itemList);
    }
}
