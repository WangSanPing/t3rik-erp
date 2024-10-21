package com.t3rik.mes.sales.service.impl;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.t3rik.common.constant.UserConstants;
import com.t3rik.common.enums.mes.OrderStatusEnum;
import com.t3rik.common.exception.BusinessException;
import com.t3rik.mes.sales.domain.SalesOrderItem;
import com.t3rik.mes.sales.domain.TranOrderLine;
import com.t3rik.mes.sales.service.ISalesOrderItemService;
import com.t3rik.mes.sales.service.ITranOrderLineService;
import com.t3rik.mes.wm.domain.WmMaterialStock;
import com.t3rik.mes.wm.domain.WmTransaction;
import com.t3rik.mes.wm.service.IWmMaterialStockService;
import com.t3rik.mes.wm.service.IWmTransactionService;
import com.t3rik.system.strategy.AutoCodeUtil;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.t3rik.mes.sales.mapper.TranOrderMapper;
import com.t3rik.mes.sales.domain.TranOrder;
import com.t3rik.mes.sales.service.ITranOrderService;
import org.springframework.transaction.annotation.Transactional;

/**
 * 销售送货单Service业务层处理
 *
 * @author 堇年
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
    @Autowired
    private IWmTransactionService wmTransactionService;
    @Autowired
    private IWmMaterialStockService wmMaterialStockService;



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
                newLineList.add(f);
                f.setStatus(OrderStatusEnum.APPROVED.getCode());
            } catch (Exception e) {
                throw new BusinessException("没有需要处理的产品销售出库单行");
            }
        });

        // 校验子单是否全部通过审批
        List<TranOrderLine> nullWorkList = tranOrderLineList.stream().filter(f -> !f.getStatus().equals(OrderStatusEnum.APPROVED.getCode())).toList();
        if (CollectionUtil.isEmpty(nullWorkList)) {
            tranOrder.setStatus(OrderStatusEnum.APPROVED.getCode());
            this.updateById(tranOrder);
        }

        if (CollectionUtil.isEmpty(newLineList)) {
            sb.append("失败！没有可送货的单据");
        }else{
            //扣除库存
            this.processTranOrder(newLineList);
            salesOrderItemService.updateBatchById(salesOrderItemList);
            sb.append("成功" + newLineList.size() + "条数据");
        }
        //更新送货单状态
        tranOrderLineService.updateBatchById(newLineList);
        return sb;
    }

    //库存
    private void processTranOrder(List<TranOrderLine>lines){
        String transactionType = UserConstants.TRANSACTION_TYPE_TRAN_SALES;
//        List<WmTransaction> list=new ArrayList<>();
        lines.forEach(f->{
            //查询库存记录
            // 查询库存
            WmMaterialStock wmMaterialStock = wmMaterialStockService.lambdaQuery()
                    .eq(WmMaterialStock::getItemId,f.getProductId())
                    .eq(WmMaterialStock::getWarehouseId,f.getWarehouseId())
                    .one();
            if(wmMaterialStock==null){
                 throw new BusinessException("仓库未查询到该产品数据，请检查各环节！"+f.getProductCode()+"/"+f.getProductName());
            }
            WmTransaction transaction = new WmTransaction();
            //产品
            transaction.setItemId(f.getProductId());
            transaction.setItemCode(f.getProductCode());
            transaction.setItemName(f.getProductName());
            transaction.setSpecification(f.getProductSpec());
            transaction.setUnitOfMeasure(f.getUnitOfMeasure());
            //仓库
            transaction.setWarehouseId(f.getWarehouseId());
            transaction.setWarehouseCode(f.getWarehouseCode());
            transaction.setWarehouseName(f.getWarehouseName());
            //库区(未做)
            transaction.setLocationId(null);
            transaction.setLocationCode(null);
            transaction.setLocationName(null);
            //库位(未做)
            transaction.setAreaId(null);
            transaction.setAreaCode(null);
            transaction.setAreaName(null);
            //数量
            transaction.setTransactionQuantity(f.getSaleSgqty());
            //工单
            transaction.setWorkorderId(f.getWorkorderId());
            transaction.setWorkorderCode(f.getWorkorderCode());

            transaction.setSourceDocId(f.getTranOrderId());
            transaction.setSourceDocCode(f.getTranOrderCode());
            transaction.setSourceDocLineId(f.getTranOrderLineId());

            transaction.setMaterialStockId(wmMaterialStock.getMaterialStockId());
            transaction.setTransactionType(transactionType);
            transaction.setTransactionFlag(-1); // 库存减少
            transaction.setTransactionDate(new Date());
            wmTransactionService.processTransaction(transaction);
//            list.add(transaction);
        });

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
