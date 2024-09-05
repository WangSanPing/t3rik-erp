package com.t3rik.mes.sales.controller;

import java.beans.Transient;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import com.t3rik.common.constant.MsgConstants;
import com.t3rik.common.core.domain.CheckInfo;
import com.t3rik.common.enums.mes.ClientOrderStatusEnum;
import com.t3rik.common.exception.BusinessException;
import com.t3rik.mes.md.domain.MdProductBom;
import com.t3rik.mes.md.service.IMdProductBomService;
import com.t3rik.mes.pro.domain.ProClientOrder;
import com.t3rik.mes.pro.domain.ProClientOrderItem;
import com.t3rik.mes.pro.domain.ProWorkorder;
import com.t3rik.mes.pro.service.IProClientOrderItemService;
import com.t3rik.mes.pro.service.IProWorkorderService;
import com.t3rik.mes.pro.service.impl.ProClientOrderServiceImpl;
import com.t3rik.mes.sales.domain.SalesOrderItem;
import com.t3rik.mes.sales.service.ISalesOrderItemService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.t3rik.common.annotation.Log;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.utils.StringUtils;
import com.t3rik.common.enums.BusinessType;
import com.t3rik.mes.sales.domain.SalesOrder;
import com.t3rik.mes.sales.service.ISalesOrderService;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.common.core.page.TableDataInfo;

/**
 * 销售订单Controller
 *
 * @author t3rik
 * @date 2024-08-29
 */
@RestController
@RequestMapping("/sales/order")
public class SalesOrderController extends BaseController {
    @Autowired
    private ISalesOrderService salesOrderService;
    @Autowired
    private ISalesOrderItemService salesOrderItemService;
    @Autowired
    private ProClientOrderServiceImpl proClientOrderService;
    @Autowired
    private IMdProductBomService productBomService;

    /**
     * 查询销售订单列表
     */
    @PreAuthorize("@ss.hasPermi('sales:order:list')")
    @GetMapping("/list")
    public TableDataInfo list(SalesOrder salesOrder) {
        // 获取查询条件
        LambdaQueryWrapper<SalesOrder> queryWrapper = getQueryWrapper(salesOrder);
        // 组装分页
        Page<SalesOrder> page = getMPPage(salesOrder);
        // 查询
        this.salesOrderService.page(page, queryWrapper);
        return getDataTableWithPage(page);
    }

    /**
     * 导出销售订单列表
     */
    @PreAuthorize("@ss.hasPermi('sales:order:export')")
    @Log(title = "销售订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SalesOrder salesOrder) {
        // 获取查询条件
        LambdaQueryWrapper<SalesOrder> queryWrapper = getQueryWrapper(salesOrder);
        List<SalesOrder> list = this.salesOrderService.list(queryWrapper);
        ExcelUtil<SalesOrder> util = new ExcelUtil<SalesOrder>(SalesOrder. class);
        util.exportExcel(response, list, "销售订单数据");
    }

    /**
     * 获取销售订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('sales:order:query')")
    @GetMapping(value = "/{salesOrderId}")
    public AjaxResult getInfo(@PathVariable("salesOrderId") Long salesOrderId) {
        SalesOrder salesOrder=this.salesOrderService.getById(salesOrderId);

        LambdaQueryWrapper<SalesOrderItem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(salesOrder.getSalesOrderId() != null, SalesOrderItem::getSalesOrderId, salesOrder.getSalesOrderId());
        List<SalesOrderItem> orderItemList= salesOrderItemService.list(queryWrapper);
        salesOrder.setSalesOrderItemList(orderItemList);
        return AjaxResult.success(salesOrder);
    }

    /**
     * 新增销售订单
     */
    @PreAuthorize("@ss.hasPermi('sales:order:add')")
    @Log(title = "销售订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SalesOrder salesOrder) {
        salesOrderService.saveOrder(salesOrder);
        return success();
    }

    /**
     * 修改销售订单
     */
    @PreAuthorize("@ss.hasPermi('sales:order:edit')")
    @Log(title = "销售订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SalesOrder salesOrder) {
        this.salesOrderService.updateById(salesOrder);
        if(salesOrder.getSalesOrderItemList().size()>0){
            salesOrderItemService.updateBatchById(salesOrder.getSalesOrderItemList());
        }
        return success();
    }

    /**
     * 删除销售订单
     */
    @PreAuthorize("@ss.hasPermi('sales:order:remove')")
    @Log(title = "销售订单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{salesOrderIds}")
    public AjaxResult remove(@PathVariable List<Long> salesOrderIds) {
        List<SalesOrder> salesOrders=salesOrderService.listByIds(salesOrderIds);
        for (SalesOrder li:salesOrders){
            LambdaQueryWrapper<SalesOrderItem> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SalesOrderItem::getSalesOrderId, li.getSalesOrderId());
            List<SalesOrderItem> orderItemList= salesOrderItemService.list(queryWrapper);
            if(orderItemList.size()>0){
                return AjaxResult.success("存在未删除的销售清单，不允许删除");
            }else{
                return toAjax( this.salesOrderService.removeByIds(salesOrderIds));
            }
        }
        return  AjaxResult.success();


    }
//    /**
//     * 生成生产订单
//     */
//    @PreAuthorize("@ss.hasPermi('sales:order:add')")
//    @Log(title = "客户订单", businessType = BusinessType.INSERT)
//    @PostMapping("/generateWorkOrder/{clientOrderId}")
//    public AjaxResult generateWorkOrder(@PathVariable("clientOrderId") String clientOrderId) {
//        SalesOrder salesOrder = this.salesOrderService.getById(clientOrderId);
//        // 数据校验
//        CheckInfo check = this.check(salesOrder);
//        // 未通过校验
//        Assert.isTrue(check.getIsCheckPassed(), () -> new BusinessException(check.getMsg()));
//        ProWorkorder workOrder = this.salesOrderService.generateWorkOrder(salesOrder);
//        return AjaxResult.success(workOrder);
//    }
//
//    /**
//     * 生成生产订单数据校验方法
//     */
//    private CheckInfo check(SalesOrder salesOrder) {
//        CheckInfo checkInfo = new CheckInfo();
//        // 默认未通过
//        checkInfo.setIsCheckPassed(Boolean.FALSE);
//        if (salesOrder == null) {
//            checkInfo.setMsg(MsgConstants.PARAM_ERROR);
//            return checkInfo;
//        }
//        // 查询是否已经添加需求物料数据
//        List<SalesOrderItem> itemList = this.salesOrderItemService.lambdaQuery()
//                .eq(SalesOrderItem::getSalesOrderId, salesOrder.getSalesOrderId())
//                .list();
//        if (CollectionUtil.isEmpty(itemList)) {
//            checkInfo.setMsg("未添加物料需求数据的客户订单,不允许生成生产订单");
//            return checkInfo;
//        }
//
////        // 最多只能添加4条数据,对应4个级别
////        if (itemList.size() > 4) {
////            checkInfo.setMsg("最多只能添加4行数据");
////            return checkInfo;
////        }
//
//        // 校验层级是否为空
////        List<ProClientOrderItem> levelList = itemList.stream().filter(f -> StringUtils.isBlank(f.getLevel())).toList();
////        if (CollectionUtil.isNotEmpty(levelList)) {
////            checkInfo.setMsg("存在层级为空的数据,不允许生成生产订单");
////            return checkInfo;
////        }
//
//        // 校验已经添加的需求物料数据中,数量是否有0的数据,如果有,不允许生成生产订单
//        List<SalesOrderItem> zeroQuantityList = itemList.stream().filter(f -> f.getSalesOrderQuantity().equals(new BigDecimal(0))).toList();
//        if (CollectionUtil.isNotEmpty(zeroQuantityList)) {
//            checkInfo.setMsg("订单中,存在订货数量0的数据,不允许生成生产订单");
//            return checkInfo;
//        }
//
//        // 校验是否已经生成过生产订单
//        if (ClientOrderStatusEnum.WORK_ORDER_FINISHED.getCode().equals(salesOrder.getStatus())) {
//            String msg = MessageFormat.format("已经生成过生产订单(生成订单的编号为:{0}),不允许再次生成", salesOrder.getWorkorderCode());
//            checkInfo.setMsg(msg);
//            return checkInfo;
//        }
//        // 校验通过
//        checkInfo.setIsCheckPassed(Boolean.TRUE);
//        return checkInfo;
//    }
    /**
    * 获取查询条件
    */
    public LambdaQueryWrapper<SalesOrder> getQueryWrapper(SalesOrder salesOrder) {
        LambdaQueryWrapper<SalesOrder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(salesOrder.getSalesOrderCode()), SalesOrder::getSalesOrderCode, salesOrder.getSalesOrderCode());
        queryWrapper.like(StringUtils.isNotEmpty(salesOrder.getSalesOrderName()), SalesOrder::getSalesOrderName, salesOrder.getSalesOrderName());
        Optional.ofNullable(salesOrder.getStatus()).ifPresent(status -> queryWrapper.eq( SalesOrder::getStatus, salesOrder.getStatus()));
        queryWrapper.like(StringUtils.isNotEmpty(salesOrder.getClientCode()), SalesOrder::getClientCode, salesOrder.getClientCode());
        queryWrapper.eq(salesOrder.getClientPoCode() != null, SalesOrder::getClientPoCode, salesOrder.getClientPoCode());
        queryWrapper.like(StringUtils.isNotEmpty(salesOrder.getClientName()), SalesOrder::getClientName, salesOrder.getClientName());
        queryWrapper.eq(salesOrder.getSalesOrderDate() != null, SalesOrder::getSalesOrderDate, salesOrder.getSalesOrderDate());
        queryWrapper.eq(salesOrder.getDeliveryDate() != null, SalesOrder::getDeliveryDate, salesOrder.getDeliveryDate());
        queryWrapper.eq(salesOrder.getCurrency() != null, SalesOrder::getCurrency, salesOrder.getCurrency());
        queryWrapper.eq(salesOrder.getPayUp() != null, SalesOrder::getPayUp, salesOrder.getPayUp());
        queryWrapper.eq(salesOrder.getOrderType() != null, SalesOrder::getOrderType, salesOrder.getOrderType());
        queryWrapper.eq(salesOrder.getFollowerMan() != null, SalesOrder::getFollowerMan, salesOrder.getFollowerMan());
        queryWrapper.eq(salesOrder.getSalesMan() != null, SalesOrder::getSalesMan, salesOrder.getSalesMan());
        queryWrapper.eq(salesOrder.getQualityRequirement() != null, SalesOrder::getQualityRequirement, salesOrder.getQualityRequirement());
        // 默认创建时间倒序
        queryWrapper.orderByDesc(SalesOrder::getCreateTime);
        Map<String, Object> params = salesOrder.getParams();
        queryWrapper.between(params.get("beginTime") != null && params.get("endTime") != null,SalesOrder::getCreateTime, params.get("beginTime"), params.get("endTime"));
        return queryWrapper;
    }
}
