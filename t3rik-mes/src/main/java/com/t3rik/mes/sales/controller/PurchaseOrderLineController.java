package com.t3rik.mes.sales.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.t3rik.mes.sales.domain.PurchaseOrderLine;
import com.t3rik.mes.sales.service.IPurchaseOrderLineService;
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
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.common.core.page.TableDataInfo;

/**
 * 采购订单列表Controller
 *
 * @author t3rik
 * @date 2024-11-02
 */
@RestController
@RequestMapping("/purchase/purchase_line")
public class PurchaseOrderLineController extends BaseController {
    @Autowired
    private IPurchaseOrderLineService purchaseOrderLineService;

    /**
     * 查询采购订单列表列表
     */
    @PreAuthorize("@ss.hasPermi('purchase:purchase_line:list')")
    @GetMapping("/list")
    public TableDataInfo list(PurchaseOrderLine purchaseOrderLine) {
        // 获取查询条件
        LambdaQueryWrapper<PurchaseOrderLine> queryWrapper = getQueryWrapper(purchaseOrderLine);
        // 组装分页
        Page<PurchaseOrderLine> page = getMPPage(purchaseOrderLine);
        // 查询
        this.purchaseOrderLineService.page(page, queryWrapper);
        return getDataTableWithPage(page);
    }

    /**
     * 导出采购订单列表列表
     */
    @PreAuthorize("@ss.hasPermi('purchase:purchase_line:export')")
    @Log(title = "采购订单列表", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PurchaseOrderLine purchaseOrderLine) {
        // 获取查询条件
        LambdaQueryWrapper<PurchaseOrderLine> queryWrapper = getQueryWrapper(purchaseOrderLine);
        List<PurchaseOrderLine> list = this.purchaseOrderLineService.list(queryWrapper);
        ExcelUtil<PurchaseOrderLine> util = new ExcelUtil<PurchaseOrderLine>(PurchaseOrderLine. class);
        util.exportExcel(response, list, "采购订单列表数据");
    }

    /**
     * 获取采购订单列表详细信息
     */
    @PreAuthorize("@ss.hasPermi('purchase:purchase_line:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") String id) {
        return AjaxResult.success(this.purchaseOrderLineService.getById(id));
    }

    /**
     * 新增采购订单列表
     */
    @PreAuthorize("@ss.hasPermi('purchase:purchase_line:add')")
    @Log(title = "采购订单列表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PurchaseOrderLine purchaseOrderLine) {
        return toAjax(this.purchaseOrderLineService.save(purchaseOrderLine));
    }

    /**
     * 修改采购订单列表
     */
    @PreAuthorize("@ss.hasPermi('purchase:purchase_line:edit')")
    @Log(title = "采购订单列表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PurchaseOrderLine purchaseOrderLine) {
        return toAjax(this.purchaseOrderLineService.updateById(purchaseOrderLine));
    }

    /**
     * 删除采购订单列表
     */
    @PreAuthorize("@ss.hasPermi('purchase:purchase_line:remove')")
    @Log(title = "采购订单列表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable List<String> ids) {
        return toAjax(this.purchaseOrderLineService.removeByIds(ids));
    }

    /**
    * 获取查询条件
    */
    public LambdaQueryWrapper<PurchaseOrderLine> getQueryWrapper(PurchaseOrderLine purchaseOrderLine) {
        LambdaQueryWrapper<PurchaseOrderLine> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(purchaseOrderLine.getPoOrderLineNo() != null, PurchaseOrderLine::getPoOrderLineNo, purchaseOrderLine.getPoOrderLineNo());
        queryWrapper.eq(purchaseOrderLine.getPoOrderNo() != null, PurchaseOrderLine::getPoOrderNo, purchaseOrderLine.getPoOrderNo());
        queryWrapper.eq(purchaseOrderLine.getProductNo() != null, PurchaseOrderLine::getProductNo, purchaseOrderLine.getProductNo());
        queryWrapper.like(StringUtils.isNotEmpty(purchaseOrderLine.getProductName()), PurchaseOrderLine::getProductName, purchaseOrderLine.getProductName());
        queryWrapper.eq(purchaseOrderLine.getColorNo() != null, PurchaseOrderLine::getColorNo, purchaseOrderLine.getColorNo());
        queryWrapper.like(StringUtils.isNotEmpty(purchaseOrderLine.getColorName()), PurchaseOrderLine::getColorName, purchaseOrderLine.getColorName());
        queryWrapper.eq(purchaseOrderLine.getUnit() != null, PurchaseOrderLine::getUnit, purchaseOrderLine.getUnit());
        queryWrapper.eq(purchaseOrderLine.getQty() != null, PurchaseOrderLine::getQty, purchaseOrderLine.getQty());
        queryWrapper.eq(purchaseOrderLine.getWeight() != null, PurchaseOrderLine::getWeight, purchaseOrderLine.getWeight());
        queryWrapper.eq(purchaseOrderLine.getPrice() != null, PurchaseOrderLine::getPrice, purchaseOrderLine.getPrice());
        queryWrapper.eq(purchaseOrderLine.getDiscount() != null, PurchaseOrderLine::getDiscount, purchaseOrderLine.getDiscount());
        queryWrapper.eq(purchaseOrderLine.getAddMoney() != null, PurchaseOrderLine::getAddMoney, purchaseOrderLine.getAddMoney());
        queryWrapper.eq(purchaseOrderLine.getMoney() != null, PurchaseOrderLine::getMoney, purchaseOrderLine.getMoney());
        queryWrapper.eq(purchaseOrderLine.getTranDate() != null, PurchaseOrderLine::getTranDate, purchaseOrderLine.getTranDate());
        queryWrapper.eq(purchaseOrderLine.getMemo() != null, PurchaseOrderLine::getMemo, purchaseOrderLine.getMemo());
        queryWrapper.eq(purchaseOrderLine.getShQty() != null, PurchaseOrderLine::getShQty, purchaseOrderLine.getShQty());
        queryWrapper.eq(purchaseOrderLine.getThQty() != null, PurchaseOrderLine::getThQty, purchaseOrderLine.getThQty());
        queryWrapper.eq(purchaseOrderLine.getInQty() != null, PurchaseOrderLine::getInQty, purchaseOrderLine.getInQty());
//        Optional.ofNullable(purchaseOrderLine.getStatus()).ifPresent(status -> queryWrapper.eq( PurchaseOrderLine::getStatus, purchaseOrderLine.getStatus().getCode()));
        queryWrapper.eq(purchaseOrderLine.getPayUp() != null, PurchaseOrderLine::getPayUp, purchaseOrderLine.getPayUp());
        queryWrapper.like(StringUtils.isNotEmpty(purchaseOrderLine.getCusNo()), PurchaseOrderLine::getCusNo, purchaseOrderLine.getCusNo());
        queryWrapper.eq(purchaseOrderLine.getSsQty() != null, PurchaseOrderLine::getSsQty, purchaseOrderLine.getSsQty());
        queryWrapper.eq(purchaseOrderLine.getDeleted() != null, PurchaseOrderLine::getDeleted, purchaseOrderLine.getDeleted());
        queryWrapper.eq(purchaseOrderLine.getVersion() != null, PurchaseOrderLine::getVersion, purchaseOrderLine.getVersion());
        queryWrapper.eq(purchaseOrderLine.getTotalWeight() != null, PurchaseOrderLine::getTotalWeight, purchaseOrderLine.getTotalWeight());
        // 默认创建时间倒序
        queryWrapper.orderByDesc(PurchaseOrderLine::getCreateTime);
        Map<String, Object> params = purchaseOrderLine.getParams();
        queryWrapper.between(params.get("beginTime") != null && params.get("endTime") != null,PurchaseOrderLine::getCreateTime, params.get("beginTime"), params.get("endTime"));
        return queryWrapper;
    }
}
