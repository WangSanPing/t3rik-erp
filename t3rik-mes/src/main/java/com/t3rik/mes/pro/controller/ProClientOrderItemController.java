package com.t3rik.mes.pro.controller;

import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.t3rik.common.annotation.Log;
import com.t3rik.common.constant.MsgConstants;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.enums.BusinessType;
import com.t3rik.common.exception.BusinessException;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.mes.pro.domain.ProClientOrder;
import com.t3rik.mes.pro.domain.ProClientOrderItem;
import com.t3rik.mes.pro.service.IProClientOrderItemService;
import com.t3rik.mes.pro.service.IProClientOrderService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 客户订单材料
 * Controller
 *
 * @author t3rik
 * @date 2024-05-07
 */
@AllArgsConstructor
@RestController
@RequestMapping("/pro/client-order-item")
public class ProClientOrderItemController extends BaseController {

    private final IProClientOrderItemService proClientOrderItemService;

    private final IProClientOrderService proClientOrderService;


    /**
     * 查询客户订单材料
     * 列表
     */
    @PreAuthorize("@ss.hasPermi('pro:clientorderitem:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProClientOrderItem proClientOrderItem) {
        // 获取查询条件
        LambdaQueryWrapper<ProClientOrderItem> queryWrapper = getQueryWrapper(proClientOrderItem);
        // 组装分页
        Page<ProClientOrderItem> page = getMPPage(proClientOrderItem);
        // 查询
        this.proClientOrderItemService.page(page, queryWrapper);
        return getDataTableWithPage(page);
    }

    /**
     * 获取客户订单的级别
     */
    @PreAuthorize("@ss.hasPermi('pro:clientorderitem:add')")
    @GetMapping("/getClientOrderItemLevel/{clientOrderId}")
    public AjaxResult getClientOrderItemLevel(@PathVariable Long clientOrderId) {
        ProClientOrder clientOrder = this.proClientOrderService.getById(clientOrderId);
        Assert.notNull(clientOrder, () -> new BusinessException(MsgConstants.PARAM_ERROR));
        Integer level = this.proClientOrderItemService.getClientOrderItemLevel(clientOrderId);
        return AjaxResult.success(level);
    }

    /**
     * 导出客户订单材料
     * 列表
     */
    @PreAuthorize("@ss.hasPermi('pro:clientorderitem:export')")
    @Log(title = "客户订单材料", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProClientOrderItem proClientOrderItem) {
        // 获取查询条件
        LambdaQueryWrapper<ProClientOrderItem> queryWrapper = getQueryWrapper(proClientOrderItem);
        List<ProClientOrderItem> list = this.proClientOrderItemService.list(queryWrapper);
        ExcelUtil<ProClientOrderItem> util = new ExcelUtil<ProClientOrderItem>(ProClientOrderItem.class);
        util.exportExcel(response, list, "客户订单材料数据");
    }

    /**
     * 获取客户订单材料
     * 详细信息
     */
    @PreAuthorize("@ss.hasPermi('pro:clientorderitem:query')")
    @GetMapping(value = "/{orderItemId}")
    public AjaxResult getInfo(@PathVariable("orderItemId") Long orderItemId) {
        return AjaxResult.success(this.proClientOrderItemService.getById(orderItemId));
    }

    /**
     * 新增客户订单材料
     */
    @PreAuthorize("@ss.hasPermi('pro:clientorderitem:add')")
    @Log(title = "客户订单材料", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProClientOrderItem proClientOrderItem) {

        return toAjax(this.proClientOrderItemService.save(proClientOrderItem));
    }

    /**
     * 修改客户订单材料
     */
    @PreAuthorize("@ss.hasPermi('pro:clientorderitem:edit')")
    @Log(title = "客户订单材料", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProClientOrderItem proClientOrderItem) {
        return toAjax(this.proClientOrderItemService.updateById(proClientOrderItem));
    }

    /**
     * 删除客户订单材料
     */
    @PreAuthorize("@ss.hasPermi('pro:clientorderitem:remove')")
    @Log(title = "客户订单材料", businessType = BusinessType.DELETE)
    @DeleteMapping("/{orderItemIds}")
    public AjaxResult remove(@PathVariable List<Long> orderItemIds) {
        return toAjax(this.proClientOrderItemService.removeByIds(orderItemIds));
    }

    /**
     * 获取查询条件
     */
    public LambdaQueryWrapper<ProClientOrderItem> getQueryWrapper(ProClientOrderItem proClientOrderItem) {
        LambdaQueryWrapper<ProClientOrderItem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(proClientOrderItem.getClientOrderId() != null, ProClientOrderItem::getClientOrderId, proClientOrderItem.getClientOrderId());

        // 默认创建时间倒序
        queryWrapper.orderByAsc(ProClientOrderItem::getLevel);

        return queryWrapper;
    }
}
