package com.t3rik.mes.sales.controller;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import com.t3rik.common.constant.MsgConstants;
import com.t3rik.common.core.domain.CheckInfo;
import com.t3rik.common.exception.BusinessException;
import com.t3rik.mes.pro.domain.ProClientOrderItem;
import com.t3rik.mes.pro.domain.ProWorkorder;
import com.t3rik.mes.pro.service.IProClientOrderItemService;
import com.t3rik.mes.sales.domain.SalesOrder;
import com.t3rik.mes.sales.service.ISalesOrderService;
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
import com.t3rik.mes.sales.domain.SalesOrderItem;
import com.t3rik.mes.sales.service.ISalesOrderItemService;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.common.core.page.TableDataInfo;

/**
 * 销售订单产品列Controller
 *
 * @author 堇年
 * @date 2024-08-29
 */
@RestController
@RequestMapping("/sales/item")
public class SalesOrderItemController extends BaseController {
    @Resource
    private ISalesOrderItemService salesOrderItemService;
    @Resource
    private ISalesOrderService salesOrderService;
    @Resource
    private IProClientOrderItemService proClientOrderItemService;

    /**
     * 查询销售订单产品列列表
     */
    @PreAuthorize("@ss.hasPermi('sales:item:list')")
    @GetMapping("/list")
    public TableDataInfo list(SalesOrderItem salesOrderItem) {
        // 获取查询条件
        LambdaQueryWrapper<SalesOrderItem> queryWrapper = getQueryWrapper(salesOrderItem);
        // 组装分页
        Page<SalesOrderItem> page = getMPPage(salesOrderItem);
        // 查询
        this.salesOrderItemService.page(page, queryWrapper);
        return getDataTableWithPage(page);
    }

    /**
     * 导出销售订单产品列列表
     */
    @PreAuthorize("@ss.hasPermi('sales:item:export')")
    @Log(title = "销售订单产品列", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, SalesOrderItem salesOrderItem) {
        // 获取查询条件
        LambdaQueryWrapper<SalesOrderItem> queryWrapper = getQueryWrapper(salesOrderItem);
        List<SalesOrderItem> list = this.salesOrderItemService.list(queryWrapper);
        ExcelUtil<SalesOrderItem> util = new ExcelUtil<SalesOrderItem>(SalesOrderItem.class);
        util.exportExcel(response, list, "销售订单产品列数据");
    }

    /**
     * 获取销售订单产品列详细信息
     */
    @PreAuthorize("@ss.hasPermi('sales:item:query')")
    @GetMapping(value = "/{salesOrderItemId}")
    public AjaxResult getInfo(@PathVariable("salesOrderItemId") Long salesOrderItemId) {
        return AjaxResult.success(this.salesOrderItemService.getById(salesOrderItemId));
    }

    /**
     * 新增销售订单产品列
     */
    @PreAuthorize("@ss.hasPermi('sales:item:add')")
    @Log(title = "销售订单产品列", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SalesOrderItem salesOrderItem) {
        return toAjax(this.salesOrderItemService.save(salesOrderItem));
    }

    /**
     * 修改销售订单产品列
     */
    @PreAuthorize("@ss.hasPermi('sales:item:edit')")
    @Log(title = "销售订单产品列", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SalesOrderItem salesOrderItem) {
        return toAjax(this.salesOrderItemService.updateById(salesOrderItem));
    }

    /**
     * 删除销售订单产品列
     */
    @PreAuthorize("@ss.hasPermi('sales:item:remove')")
    @Log(title = "销售订单产品列", businessType = BusinessType.DELETE)
    @DeleteMapping("/{salesOrderIds}")
    public AjaxResult remove(@PathVariable List<Long> salesOrderIds) {
        return toAjax(this.salesOrderItemService.removeByIds(salesOrderIds));
    }

    /**
     * 生成生产订单
     */
    @PreAuthorize("@ss.hasPermi('sales:order:execute')")
    @Log(title = "销售订单", businessType = BusinessType.INSERT)
    @PostMapping("/execute/{itemId}")
    public AjaxResult generateWorkOrder(@PathVariable("itemId") Long itemId) throws Exception {
        SalesOrderItem salesOrderItem = salesOrderItemService.getById(itemId);
        // 数据校验
        CheckInfo check = this.check(salesOrderItem);
        // 未通过校验
        Assert.isTrue(check.getIsCheckPassed(), () -> new BusinessException(check.getMsg()));
        //获取主销售单信息
        SalesOrder salesOrder = this.salesOrderService.getById(salesOrderItem.getSalesOrderId());
        ProWorkorder proWorkorder = this.salesOrderItemService.execute(salesOrderItem,salesOrder);

        //更新后获取相关订单数据
        List<SalesOrderItem> salesOrderItems = salesOrderItemService.lambdaQuery()
                .eq(SalesOrderItem::getSalesOrderId, salesOrderItem.getSalesOrderId())
                .list();
        // 校验子单是否全部生成生产订单
        List<SalesOrderItem> nullWorkList = salesOrderItems.stream().filter(f -> f.getWorkorderCode().equals(null)).toList();
        if (CollectionUtil.isEmpty(nullWorkList)) {
            salesOrder.setStatus("WORK_ORDER_FINISHED");
            this.salesOrderService.updateById(salesOrder);
        }
        return AjaxResult.success(proWorkorder);
    }

    /**
     * 生成生产订单数据校验方法
     */
    private CheckInfo check(SalesOrderItem salesOrderItem) {
        CheckInfo checkInfo = new CheckInfo();
        // 默认未通过
        checkInfo.setIsCheckPassed(Boolean.FALSE);
        if (salesOrderItem == null) {
            checkInfo.setMsg(MsgConstants.PARAM_ERROR);
            return checkInfo;
        }
        // 查询是否已经添加需求物料数据
        List<ProClientOrderItem> list = this.proClientOrderItemService.lambdaQuery()
                .eq(ProClientOrderItem::getClientOrderId, salesOrderItem.getSalesOrderItemId())
                .list();
        if (CollectionUtil.isEmpty(list)) {
            checkInfo.setMsg("未添加物料需求数据的客户订单,不允许生成生产订单");
        } else {
            checkInfo.setIsCheckPassed(Boolean.TRUE);
        }

        // 校验已经添加的需求物料数据中,数量是否有0的数据,如果有,不允许生成生产订单
        if (salesOrderItem.getSalesOrderQuantity().equals(new BigDecimal(0))) {
            checkInfo.setMsg("订单中,存在订货数量0的数据,不允许生成生产订单");
            return checkInfo;
        }
        return checkInfo;
    }

    /**
     * 获取查询条件
     */
    public LambdaQueryWrapper<SalesOrderItem> getQueryWrapper(SalesOrderItem salesOrderItem) {
        LambdaQueryWrapper<SalesOrderItem> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(salesOrderItem.getProductCode()), SalesOrderItem::getProductCode, salesOrderItem.getProductCode());
        queryWrapper.like(StringUtils.isNotEmpty(salesOrderItem.getProductName()), SalesOrderItem::getProductName, salesOrderItem.getProductName());
        queryWrapper.like(StringUtils.isNotEmpty(salesOrderItem.getProductSpec()), SalesOrderItem::getProductSpec, salesOrderItem.getProductSpec());
        queryWrapper.like(StringUtils.isNotEmpty(salesOrderItem.getQuality()), SalesOrderItem::getQuality, salesOrderItem.getQuality());
        queryWrapper.eq(salesOrderItem.getColorName() != null, SalesOrderItem::getColorName, salesOrderItem.getColorName());
        queryWrapper.eq(salesOrderItem.getUnitOfMeasure() != null, SalesOrderItem::getUnitOfMeasure, salesOrderItem.getUnitOfMeasure());
        queryWrapper.eq(salesOrderItem.getDeliveryDate() != null, SalesOrderItem::getDeliveryDate, salesOrderItem.getDeliveryDate());
        queryWrapper.like(StringUtils.isNotEmpty(salesOrderItem.getClientProductName()), SalesOrderItem::getClientProductName, salesOrderItem.getClientProductName());
        queryWrapper.eq(salesOrderItem.getStockNum() != null, SalesOrderItem::getStockNum, salesOrderItem.getStockNum());
        queryWrapper.eq(salesOrderItem.getSaleQty() != null, SalesOrderItem::getSaleQty, salesOrderItem.getSaleQty());
        queryWrapper.eq(salesOrderItem.getSaleThqty() != null, SalesOrderItem::getSaleThqty, salesOrderItem.getSaleThqty());
        queryWrapper.eq(salesOrderItem.getSaleSgqty() != null, SalesOrderItem::getSaleSgqty, salesOrderItem.getSaleSgqty());
        // 默认创建时间倒序
        queryWrapper.orderByDesc(SalesOrderItem::getCreateTime);
        Map<String, Object> params = salesOrderItem.getParams();
        queryWrapper.between(params.get("beginTime") != null && params.get("endTime") != null, SalesOrderItem::getCreateTime, params.get("beginTime"), params.get("endTime"));
        return queryWrapper;
    }


}
