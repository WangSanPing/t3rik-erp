package com.t3rik.mes.sales.controller;


import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.t3rik.common.annotation.Log;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.enums.BusinessType;
import com.t3rik.common.utils.StringUtils;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.mes.sales.domain.SalesOrder;
import com.t3rik.mes.sales.domain.SalesOrderItem;
import com.t3rik.mes.sales.domain.TranOrder;
import com.t3rik.mes.sales.domain.TranOrderLine;
import com.t3rik.mes.sales.service.ITranOrderLineService;
import com.t3rik.mes.sales.service.ITranOrderService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 销售送货单Controller
 *
 * @author t3rik
 * @date 2024-09-09
 */
@RestController
@RequestMapping("/sales/tranOrder")
public class TranOrderController extends BaseController {
    @Autowired
    private ITranOrderService tranOrderService;
    @Autowired
    private ITranOrderLineService tranOrderLineService;

    /**
     * 查询销售送货单列表
     */
    @PreAuthorize("@ss.hasPermi('sales:tranOrder:list')")
    @GetMapping("/list")
    public TableDataInfo list(TranOrder tranOrder) {
        // 获取查询条件
        LambdaQueryWrapper<TranOrder> queryWrapper = getQueryWrapper(tranOrder);
        // 组装分页
        Page<TranOrder> page = getMPPage(tranOrder);
        // 查询
        this.tranOrderService.page(page, queryWrapper);
        return getDataTableWithPage(page);
    }

    /**
     * 导出销售送货单列表
     */
    @PreAuthorize("@ss.hasPermi('sales:tranOrder:export')")
    @Log(title = "销售送货单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TranOrder tranOrder) {
        // 获取查询条件
        LambdaQueryWrapper<TranOrder> queryWrapper = getQueryWrapper(tranOrder);
        List<TranOrder> list = this.tranOrderService.list(queryWrapper);
        ExcelUtil<TranOrder> util = new ExcelUtil<TranOrder>(TranOrder. class);
        util.exportExcel(response, list, "销售送货单数据");
    }

    /**
     * 获取销售送货单详细信息
     */
    @PreAuthorize("@ss.hasPermi('sales:tranOrder:query')")
    @GetMapping(value = "/{tranOrderId}")
    public AjaxResult getInfo(@PathVariable("tranOrderId") Long tranOrderId) {
        TranOrder tranOrder=this.tranOrderService.getById(tranOrderId);

        LambdaQueryWrapper<TranOrderLine> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(tranOrder.getTranOrderId() != null, TranOrderLine::getTranOrderId, tranOrder.getTranOrderId());
        List<TranOrderLine> orderItemList= tranOrderLineService.list(queryWrapper);
        tranOrder.setTranOrderLineList(orderItemList);
        return AjaxResult.success(tranOrder);
    }

    /**
     * 新增销售送货单
     */
    @PreAuthorize("@ss.hasPermi('sales:tranOrder:add')")
    @Log(title = "销售送货单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TranOrder tranOrder) {
        this.tranOrderService.saveTranOrder(tranOrder);
        return success();
    }

    /**
     * 修改销售送货单
     */
    @PreAuthorize("@ss.hasPermi('sales:tranOrder:edit')")
    @Log(title = "销售送货单", businessType = BusinessType.UPDATE)
    @PutMapping
    @Transactional
    public AjaxResult edit(@RequestBody TranOrder tranOrder) {
        this.tranOrderService.updateById(tranOrder);
        if(tranOrder.getTranOrderLineList().size()>0){
            tranOrderLineService.updateBatchById(tranOrder.getTranOrderLineList());
        }
        return success();
    }

    /**
     * 删除销售送货单
     */
    @PreAuthorize("@ss.hasPermi('sales:tranOrder:remove')")
    @Log(title = "销售送货单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{tranOrderIds}")
    public AjaxResult remove(@PathVariable List<Long> tranOrderIds) {
        List<TranOrder> tranOrders=tranOrderService.listByIds(tranOrderIds);
        StringBuffer sb=new StringBuffer();
        for (TranOrder li:tranOrders){
            LambdaQueryWrapper<TranOrderLine> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(TranOrderLine::getTranOrderId, li.getTranOrderId());
            List<TranOrderLine> orderItemList= tranOrderLineService.list(queryWrapper);
            if(orderItemList.size()>0){
                sb.append("销售送货单"+li.getTranOrderCode()+"下还有未删除的清单，不允许删除"+"\n");
            }else{
                return toAjax(this.tranOrderService.removeByIds(tranOrderIds));
            }
        }
        return  AjaxResult.error(sb.toString());
    }


    /**
     * 获取查询条件
     */
    public LambdaQueryWrapper<TranOrder> getQueryWrapper(TranOrder tranOrde) {
        LambdaQueryWrapper<TranOrder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(tranOrde.getTranOrderCode() != null, TranOrder::getTranOrderCode, tranOrde.getTranOrderCode());
        queryWrapper.eq(tranOrde.getWarehouseId() != null, TranOrder::getWarehouseId, tranOrde.getWarehouseId());
        queryWrapper.eq(tranOrde.getTranOrderType() != null, TranOrder::getTranOrderType, tranOrde.getTranOrderType());
        queryWrapper.eq(tranOrde.getStatus() != null ,TranOrder::getStatus, tranOrde.getStatus());
        queryWrapper.eq(tranOrde.getClientId() != null, TranOrder::getClientId, tranOrde.getClientId());
        queryWrapper.eq(tranOrde.getClientCode() != null, TranOrder::getClientCode, tranOrde.getClientCode());
        queryWrapper.like(StringUtils.isNotEmpty(tranOrde.getClientName()), TranOrder::getClientName, tranOrde.getClientName());
        queryWrapper.eq(tranOrde.getTotalAmount() != null, TranOrder::getTotalAmount, tranOrde.getTotalAmount());
        queryWrapper.eq(tranOrde.getWeight() != null, TranOrder::getWeight, tranOrde.getWeight());
        queryWrapper.eq(tranOrde.getTolal() != null, TranOrder::getTolal, tranOrde.getTolal());
        queryWrapper.eq(tranOrde.getCurrency() != null, TranOrder::getCurrency, tranOrde.getCurrency());
        queryWrapper.eq(tranOrde.getPayUp() != null, TranOrder::getPayUp, tranOrde.getPayUp());
        queryWrapper.like(StringUtils.isNotEmpty(tranOrde.getFollowerMan()), TranOrder::getFollowerMan, tranOrde.getFollowerMan());
        queryWrapper.like(StringUtils.isNotEmpty(tranOrde.getBusMan()), TranOrder::getBusMan, tranOrde.getBusMan());
        queryWrapper.eq(tranOrde.getCreateBy() != null, TranOrder::getCreateBy, tranOrde.getCreateBy());
        queryWrapper.eq(tranOrde.getCreateTime() != null, TranOrder::getCreateTime, tranOrde.getCreateTime());
        queryWrapper.eq(tranOrde.getUpdateBy() != null, TranOrder::getUpdateBy, tranOrde.getUpdateBy());
        queryWrapper.eq(tranOrde.getUpdateTime() != null, TranOrder::getUpdateTime, tranOrde.getUpdateTime());
        queryWrapper.eq(tranOrde.getRemark() != null, TranOrder::getRemark, tranOrde.getRemark());
        // 默认创建时间倒序
        queryWrapper.orderByDesc(TranOrder::getCreateTime);
        Map<String, Object> params = tranOrde.getParams();
        queryWrapper.between(params.get("beginTime") != null && params.get("endTime") != null,TranOrder::getCreateTime, params.get("beginTime"), params.get("endTime"));
        return queryWrapper;
    }
}
