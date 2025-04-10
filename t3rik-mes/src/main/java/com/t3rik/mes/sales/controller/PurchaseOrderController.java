package com.t3rik.mes.sales.controller;

import java.util.List;
import java.util.Map;

import com.t3rik.mes.sales.domain.PurchaseOrder;
import com.t3rik.mes.sales.service.IPurchaseOrderService;
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
import com.t3rik.common.enums.system.BusinessType;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.common.core.page.TableDataInfo;

/**
 * 采购订单Controller
 *
 * @author t3rik
 * @date 2024-11-02
 */
@RestController
@RequestMapping("/purchase/purchase_order")
public class PurchaseOrderController extends BaseController {
    @Autowired
    private IPurchaseOrderService purchaseOrderService;

    /**
     * 查询采购订单列表
     */
    @PreAuthorize("@ss.hasPermi('purchase:purchase_order:list')")
    @GetMapping("/list")
    public TableDataInfo list(PurchaseOrder purchaseOrder) {
        // 获取查询条件
        LambdaQueryWrapper<PurchaseOrder> queryWrapper = getQueryWrapper(purchaseOrder);
        // 组装分页
        Page<PurchaseOrder> page = getMPPage(purchaseOrder);
        // 查询
        this.purchaseOrderService.page(page, queryWrapper);
        return getDataTableWithPage(page);
    }

    /**
     * 导出采购订单列表
     */
    @PreAuthorize("@ss.hasPermi('purchase:purchase_order:export')")
    @Log(title = "采购订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PurchaseOrder purchaseOrder) {
        // 获取查询条件
        LambdaQueryWrapper<PurchaseOrder> queryWrapper = getQueryWrapper(purchaseOrder);
        List<PurchaseOrder> list = this.purchaseOrderService.list(queryWrapper);
        ExcelUtil<PurchaseOrder> util = new ExcelUtil<PurchaseOrder>(PurchaseOrder. class);
        util.exportExcel(response, list, "采购订单数据");
    }

    /**
     * 获取采购订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('purchase:purchase_order:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return AjaxResult.success(this.purchaseOrderService.getById(id));
    }

    /**
     * 新增采购订单
     */
    @PreAuthorize("@ss.hasPermi('purchase:purchase_order:add')")
    @Log(title = "采购订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PurchaseOrder purchaseOrder) {
        return toAjax(this.purchaseOrderService.save(purchaseOrder));
    }

    /**
     * 修改采购订单
     */
    @PreAuthorize("@ss.hasPermi('purchase:purchase_order:edit')")
    @Log(title = "采购订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PurchaseOrder purchaseOrder) {
        return toAjax(this.purchaseOrderService.updateById(purchaseOrder));
    }

    /**
     * 删除采购订单
     */
    @PreAuthorize("@ss.hasPermi('purchase:purchase_order:remove')")
    @Log(title = "采购订单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable List<String> ids) {
        return toAjax(this.purchaseOrderService.removeByIds(ids));
    }

    /**
    * 获取查询条件
    */
    public LambdaQueryWrapper<PurchaseOrder> getQueryWrapper(PurchaseOrder purchaseOrder) {
        LambdaQueryWrapper<PurchaseOrder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(purchaseOrder.getPoOrderNo()), PurchaseOrder::getPoOrderNo, purchaseOrder.getPoOrderNo());
        queryWrapper.like(StringUtils.isNotEmpty(purchaseOrder.getHwNo()), PurchaseOrder::getHwNo, purchaseOrder.getHwNo());
        queryWrapper.like(StringUtils.isNotEmpty(purchaseOrder.getVendorNo()), PurchaseOrder::getVendorNo, purchaseOrder.getVendorNo());
        queryWrapper.eq(purchaseOrder.getPoOrderDate() != null, PurchaseOrder::getPoOrderDate, purchaseOrder.getPoOrderDate());
        queryWrapper.eq(purchaseOrder.getPoOrderUser() != null, PurchaseOrder::getPoOrderUser, purchaseOrder.getPoOrderUser());
        queryWrapper.like(StringUtils.isNotEmpty(purchaseOrder.getTranAddress()), PurchaseOrder::getTranAddress, purchaseOrder.getTranAddress());
        queryWrapper.eq(purchaseOrder.getTranDate() != null, PurchaseOrder::getTranDate, purchaseOrder.getTranDate());
        queryWrapper.like(StringUtils.isNotEmpty(purchaseOrder.getRemake()), PurchaseOrder::getRemake, purchaseOrder.getRemake());
        queryWrapper.like(StringUtils.isNotEmpty(purchaseOrder.getBillUser()), PurchaseOrder::getBillUser, purchaseOrder.getBillUser());
        queryWrapper.eq(purchaseOrder.getReview() != null, PurchaseOrder::getReview, purchaseOrder.getReview());
//        Optional.ofNullable(purchaseOrder.getStatus()).ifPresent(status -> queryWrapper.eq( PurchaseOrder::getStatus, purchaseOrder.getStatus().getCode()));
        queryWrapper.eq(purchaseOrder.getPartDate() != null, PurchaseOrder::getPartDate, purchaseOrder.getPartDate());
        queryWrapper.like(StringUtils.isNotEmpty(purchaseOrder.getPartUser()), PurchaseOrder::getPartUser, purchaseOrder.getPartUser());
        queryWrapper.eq(purchaseOrder.getAuditDate() != null, PurchaseOrder::getAuditDate, purchaseOrder.getAuditDate());
        queryWrapper.like(StringUtils.isNotEmpty(purchaseOrder.getAuditUser()), PurchaseOrder::getAuditUser, purchaseOrder.getAuditUser());
        queryWrapper.eq(purchaseOrder.getEndDate() != null, PurchaseOrder::getEndDate, purchaseOrder.getEndDate());
        queryWrapper.like(StringUtils.isNotEmpty(purchaseOrder.getEndUser()), PurchaseOrder::getEndUser, purchaseOrder.getEndUser());
        queryWrapper.like(StringUtils.isNotEmpty(purchaseOrder.getCreateBy()), PurchaseOrder::getCreateBy, purchaseOrder.getCreateBy());
        queryWrapper.eq(purchaseOrder.getCreateTime() != null, PurchaseOrder::getCreateTime, purchaseOrder.getCreateTime());
        queryWrapper.like(StringUtils.isNotEmpty(purchaseOrder.getUpdateBy()), PurchaseOrder::getUpdateBy, purchaseOrder.getUpdateBy());
        queryWrapper.eq(purchaseOrder.getUpdateTime() != null, PurchaseOrder::getUpdateTime, purchaseOrder.getUpdateTime());
        // 默认创建时间倒序
        queryWrapper.orderByDesc(PurchaseOrder::getCreateTime);
        Map<String, Object> params = purchaseOrder.getParams();
        queryWrapper.between(params.get("beginTime") != null && params.get("endTime") != null,PurchaseOrder::getCreateTime, params.get("beginTime"), params.get("endTime"));
        return queryWrapper;
    }
}
