package com.t3rik.mes.pro.controller;

import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletResponse;

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
import com.t3rik.mes.pro.domain.ProClientOrder;
import com.t3rik.mes.pro.service.IProClientOrderService;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.common.core.page.TableDataInfo;

/**
 * 客户订单Controller
 *
 * @author t3rik
 * @date 2024-05-01
 */
@RestController
@RequestMapping("/pro/client-order")
public class ProClientOrderController extends BaseController {
    @Autowired
    private IProClientOrderService proClientOrderService;

    /**
     * 查询客户订单列表
     */
    @PreAuthorize("@ss.hasPermi('pro:clientorder:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProClientOrder proClientOrder) {
        // 获取查询条件
        LambdaQueryWrapper<ProClientOrder> queryWrapper = getQueryWrapper(proClientOrder);
        // 组装分页
        Page<ProClientOrder> page = getMPPage(proClientOrder);
        // 查询
        this.proClientOrderService.page(page, queryWrapper);
        return getDataTableWithPage(page);
    }

    /**
     * 导出客户订单列表
     */
    @PreAuthorize("@ss.hasPermi('pro:clientorder:export')")
    @Log(title = "客户订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProClientOrder proClientOrder) {
        // 获取查询条件
        LambdaQueryWrapper<ProClientOrder> queryWrapper = getQueryWrapper(proClientOrder);
        List<ProClientOrder> list = this.proClientOrderService.list(queryWrapper);
        ExcelUtil<ProClientOrder> util = new ExcelUtil<ProClientOrder>(ProClientOrder. class);
        util.exportExcel(response, list, "客户订单数据");
    }

    /**
     * 获取客户订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('pro:clientorder:query')")
    @GetMapping(value = "/{clientOrderId}")
    public AjaxResult getInfo(@PathVariable("clientOrderId") Long clientOrderId) {
        return AjaxResult.success(this.proClientOrderService.getById(clientOrderId));
    }

    /**
     * 新增客户订单
     */
    @PreAuthorize("@ss.hasPermi('pro:clientorder:add')")
    @Log(title = "客户订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProClientOrder proClientOrder) {
        return toAjax(this.proClientOrderService.save(proClientOrder));
    }

    /**
     * 修改客户订单
     */
    @PreAuthorize("@ss.hasPermi('pro:clientorder:edit')")
    @Log(title = "客户订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProClientOrder proClientOrder) {
        return toAjax(this.proClientOrderService.updateById(proClientOrder));
    }

    /**
     * 删除客户订单
     */
    @PreAuthorize("@ss.hasPermi('pro:clientorder:remove')")
    @Log(title = "客户订单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{clientOrderIds}")
    public AjaxResult remove(@PathVariable List<Long> clientOrderIds) {
        return toAjax(this.proClientOrderService.removeByIds(clientOrderIds));
    }

    /**
    * 获取查询条件
    */
    public LambdaQueryWrapper<ProClientOrder> getQueryWrapper(ProClientOrder proClientOrder) {
        LambdaQueryWrapper<ProClientOrder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(proClientOrder.getClientOrderCode() != null, ProClientOrder::getClientOrderCode, proClientOrder.getClientOrderCode());
        queryWrapper.like(StringUtils.isNotEmpty(proClientOrder.getClientName()), ProClientOrder::getClientName, proClientOrder.getClientName());
        queryWrapper.eq(proClientOrder.getOrderDate() != null, ProClientOrder::getOrderDate, proClientOrder.getOrderDate());
        queryWrapper.eq(proClientOrder.getDeliveryDate() != null, ProClientOrder::getDeliveryDate, proClientOrder.getDeliveryDate());
        // 默认创建时间倒序
        queryWrapper.orderByDesc(ProClientOrder::getCreateTime);
        Map<String, Object> params = proClientOrder.getParams();
        queryWrapper.between(params.get("beginTime") != null && params.get("endTime") != null,ProClientOrder::getCreateTime, params.get("beginTime"), params.get("endTime"));
        return queryWrapper;
    }
}
