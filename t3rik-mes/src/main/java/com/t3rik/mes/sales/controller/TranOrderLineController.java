package com.t3rik.mes.sales.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

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
import com.t3rik.mes.sales.domain.TranOrderLine;
import com.t3rik.mes.sales.service.ITranOrderLineService;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.common.core.page.TableDataInfo;

/**
 * 销售送货单列Controller
 *
 * @author t3rik
 * @date 2024-09-13
 */
@RestController
@RequestMapping("/sales/tranOrder/line")
public class TranOrderLineController extends BaseController {
    @Autowired
    private ITranOrderLineService tranOrderLineService;

    /**
     * 查询销售送货单列列表
     */
    @PreAuthorize("@ss.hasPermi('sales:tranOrder:line:list')")
    @GetMapping("/list")
    public TableDataInfo list(TranOrderLine tranOrderLine) {
        // 获取查询条件
        LambdaQueryWrapper<TranOrderLine> queryWrapper = getQueryWrapper(tranOrderLine);
        // 组装分页
        Page<TranOrderLine> page = getMPPage(tranOrderLine);
        // 查询
        this.tranOrderLineService.page(page, queryWrapper);
        return getDataTableWithPage(page);
    }

    /**
     * 导出销售送货单列列表
     */
    @PreAuthorize("@ss.hasPermi('sales:tranOrder:line:export')")
    @Log(title = "销售送货单列", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TranOrderLine tranOrderLine) {
        // 获取查询条件
        LambdaQueryWrapper<TranOrderLine> queryWrapper = getQueryWrapper(tranOrderLine);
        List<TranOrderLine> list = this.tranOrderLineService.list(queryWrapper);
        ExcelUtil<TranOrderLine> util = new ExcelUtil<TranOrderLine>(TranOrderLine. class);
        util.exportExcel(response, list, "销售送货单列数据");
    }

    /**
     * 获取销售送货单列详细信息
     */
    @PreAuthorize("@ss.hasPermi('sales:tranOrder:line:query')")
    @GetMapping(value = "/{tranOrderLineId}")
    public AjaxResult getInfo(@PathVariable("tranOrderLineId") Long tranOrderLineId) {
        return AjaxResult.success(this.tranOrderLineService.getById(tranOrderLineId));
    }

    /**
     * 新增销售送货单列
     */
    @PreAuthorize("@ss.hasPermi('sales:tranOrder:line:add')")
    @Log(title = "销售送货单列", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TranOrderLine tranOrderLine) {
        return toAjax(this.tranOrderLineService.save(tranOrderLine));
    }

    /**
     * 修改销售送货单列
     */
    @PreAuthorize("@ss.hasPermi('sales:tranOrder:line:edit')")
    @Log(title = "销售送货单列", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TranOrderLine tranOrderLine) {
        return toAjax(this.tranOrderLineService.updateById(tranOrderLine));
    }

    /**
     * 删除销售送货单列
     */
    @PreAuthorize("@ss.hasPermi('sales:tranOrder:line:remove')")
    @Log(title = "销售送货单列", businessType = BusinessType.DELETE)
    @DeleteMapping("/{tranOrderLineIds}")
    public AjaxResult remove(@PathVariable List<Long> tranOrderLineIds) {
        return toAjax(this.tranOrderLineService.removeByIds(tranOrderLineIds));
    }

    /**
     * 获取查询条件
     */
    public LambdaQueryWrapper<TranOrderLine> getQueryWrapper(TranOrderLine tranOrderLine) {
        LambdaQueryWrapper<TranOrderLine> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(tranOrderLine.getTranOrderLineId() != null, TranOrderLine::getTranOrderLineId, tranOrderLine.getTranOrderLineId());
        queryWrapper.eq(tranOrderLine.getTranOrderId() != null, TranOrderLine::getTranOrderId, tranOrderLine.getTranOrderId());
        queryWrapper.eq(tranOrderLine.getClientId() != null, TranOrderLine::getClientId, tranOrderLine.getClientId());
        queryWrapper.eq(tranOrderLine.getClientCode() != null, TranOrderLine::getClientCode, tranOrderLine.getClientCode());
        queryWrapper.like(StringUtils.isNotEmpty(tranOrderLine.getClientName()), TranOrderLine::getClientName, tranOrderLine.getClientName());
        queryWrapper.eq(tranOrderLine.getFollowerMan() != null, TranOrderLine::getFollowerMan, tranOrderLine.getFollowerMan());
        queryWrapper.eq(tranOrderLine.getBusMan() != null, TranOrderLine::getBusMan, tranOrderLine.getBusMan());
        queryWrapper.eq(tranOrderLine.getTranOrderType() != null, TranOrderLine::getTranOrderType, tranOrderLine.getTranOrderType());
        queryWrapper.eq(tranOrderLine.getTranDate() != null, TranOrderLine::getTranDate, tranOrderLine.getTranDate());
        queryWrapper.eq(tranOrderLine.getTranCode() != null, TranOrderLine::getTranCode, tranOrderLine.getTranCode());
        queryWrapper.eq(tranOrderLine.getWarehouseId() != null, TranOrderLine::getWarehouseId, tranOrderLine.getWarehouseId());
        queryWrapper.eq(tranOrderLine.getWarehouseCode() != null, TranOrderLine::getWarehouseCode, tranOrderLine.getWarehouseCode());
        queryWrapper.like(StringUtils.isNotEmpty(tranOrderLine.getWarehouseName()), TranOrderLine::getWarehouseName, tranOrderLine.getWarehouseName());
        queryWrapper.eq(tranOrderLine.getSalesOrderId() != null, TranOrderLine::getSalesOrderId, tranOrderLine.getSalesOrderId());
        queryWrapper.like(StringUtils.isNotEmpty(tranOrderLine.getSalesOrderCode()), TranOrderLine::getSalesOrderCode, tranOrderLine.getSalesOrderCode());
        queryWrapper.eq(tranOrderLine.getProductId() != null, TranOrderLine::getProductId, tranOrderLine.getProductId());
        queryWrapper.like(StringUtils.isNotEmpty(tranOrderLine.getProductCode()), TranOrderLine::getProductCode, tranOrderLine.getProductCode());
        queryWrapper.like(StringUtils.isNotEmpty(tranOrderLine.getProductName()), TranOrderLine::getProductName, tranOrderLine.getProductName());
        queryWrapper.like(StringUtils.isNotEmpty(tranOrderLine.getProductSpec()), TranOrderLine::getProductSpec, tranOrderLine.getProductSpec());
        queryWrapper.eq(tranOrderLine.getQuality() != null, TranOrderLine::getQuality, tranOrderLine.getQuality());
        queryWrapper.eq(tranOrderLine.getLevel() != null, TranOrderLine::getLevel, tranOrderLine.getLevel());
        queryWrapper.like(StringUtils.isNotEmpty(tranOrderLine.getColorName()), TranOrderLine::getColorName, tranOrderLine.getColorName());
        queryWrapper.eq(tranOrderLine.getUnitOfMeasure() != null, TranOrderLine::getUnitOfMeasure, tranOrderLine.getUnitOfMeasure());
        queryWrapper.eq(tranOrderLine.getSaleQty() != null, TranOrderLine::getSaleQty, tranOrderLine.getSaleQty());
        queryWrapper.eq(tranOrderLine.getSaleThqty() != null, TranOrderLine::getSaleThqty, tranOrderLine.getSaleThqty());
        queryWrapper.eq(tranOrderLine.getAmount() != null, TranOrderLine::getAmount, tranOrderLine.getAmount());
        queryWrapper.eq(tranOrderLine.getSaleSgqty() != null, TranOrderLine::getSaleSgqty, tranOrderLine.getSaleSgqty());
        queryWrapper.eq(tranOrderLine.getWeight() != null, TranOrderLine::getWeight, tranOrderLine.getWeight());
        queryWrapper.eq(tranOrderLine.getTax() != null, TranOrderLine::getTax, tranOrderLine.getTax());
        queryWrapper.eq(tranOrderLine.getTotalAmount() != null, TranOrderLine::getTotalAmount, tranOrderLine.getTotalAmount());
        queryWrapper.eq(tranOrderLine.getExtra() != null, TranOrderLine::getExtra, tranOrderLine.getExtra());
        queryWrapper.eq(tranOrderLine.getPicNum() != null, TranOrderLine::getPicNum, tranOrderLine.getPicNum());
        queryWrapper.eq(tranOrderLine.getPo() != null, TranOrderLine::getPo, tranOrderLine.getPo());
        queryWrapper.eq(tranOrderLine.getSku() != null, TranOrderLine::getSku, tranOrderLine.getSku());
        queryWrapper.eq(tranOrderLine.getClientSpec() != null, TranOrderLine::getClientSpec, tranOrderLine.getClientSpec());
        queryWrapper.like(StringUtils.isNotEmpty(tranOrderLine.getClientProductName()), TranOrderLine::getClientProductName, tranOrderLine.getClientProductName());
        queryWrapper.eq(tranOrderLine.getClientColor() != null, TranOrderLine::getClientColor, tranOrderLine.getClientColor());
        queryWrapper.eq(tranOrderLine.getWorkorderId() != null, TranOrderLine::getWorkorderId, tranOrderLine.getWorkorderId());
        queryWrapper.eq(tranOrderLine.getWorkorderCode() != null, TranOrderLine::getWorkorderCode, tranOrderLine.getWorkorderCode());
        queryWrapper.eq(tranOrderLine.getStatus() != null, TranOrderLine::getStatus, tranOrderLine.getStatus());
//        Optional.ofNullable(tranOrderLine.getStatus()).ifPresent(status -> queryWrapper.eq( TranOrderLine::getStatus, tranOrderLine.getStatus().getCode()));
        queryWrapper.eq(tranOrderLine.getCreateUserId() != null, TranOrderLine::getCreateUserId, tranOrderLine.getCreateUserId());
        queryWrapper.eq(tranOrderLine.getUpdateUserId() != null, TranOrderLine::getUpdateUserId, tranOrderLine.getUpdateUserId());
        // 默认创建时间倒序
        queryWrapper.orderByDesc(TranOrderLine::getCreateTime);
        Map<String, Object> params = tranOrderLine.getParams();
        queryWrapper.between(params.get("beginTime") != null && params.get("endTime") != null,TranOrderLine::getCreateTime, params.get("beginTime"), params.get("endTime"));
        return queryWrapper;
    }
}
