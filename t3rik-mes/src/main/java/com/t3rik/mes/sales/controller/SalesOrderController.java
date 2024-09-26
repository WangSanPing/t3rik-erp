package com.t3rik.mes.sales.controller;

// -t3rik
// 多用格式化代码，并且去掉无用的包
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
import com.t3rik.common.enums.mes.OrderStatusEnum;
import com.t3rik.common.exception.BusinessException;
import com.t3rik.mes.api.service.IMesWorkOrderService;
import com.t3rik.mes.md.domain.MdProductBom;
import com.t3rik.mes.md.service.IMdProductBomService;
import com.t3rik.mes.pro.domain.*;
import com.t3rik.mes.pro.service.IProClientOrderItemService;
import com.t3rik.mes.pro.service.IProWorkorderService;
import com.t3rik.mes.pro.service.impl.ProClientOrderServiceImpl;
import com.t3rik.mes.sales.domain.SalesOrderItem;
import com.t3rik.mes.sales.service.ISalesOrderItemService;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
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
    // -t3rik
    // 用Resource或者构造函数的方式做依赖注入 Resource至少没有警告
    @Autowired
    private ISalesOrderService salesOrderService;
    @Autowired
    private ISalesOrderItemService salesOrderItemService;

    @Resource
    private IProClientOrderItemService proClientOrderItemService;
    @Resource
    private IMesWorkOrderService mesWorkOrderService;

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
        ExcelUtil<SalesOrder> util = new ExcelUtil<SalesOrder>(SalesOrder.class);
        util.exportExcel(response, list, "销售订单数据");
    }

    /**
     * 获取销售订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('sales:order:query')")
    @GetMapping(value = "/{salesOrderId}")
    public AjaxResult getInfo(@PathVariable("salesOrderId") Long salesOrderId) {
        SalesOrder salesOrder = this.salesOrderService.getById(salesOrderId);
        // 查询是否已经添加销售列
        List<SalesOrderItem> itemList = this.salesOrderItemService.lambdaQuery()
                .eq(SalesOrderItem::getSalesOrderId, salesOrder.getSalesOrderId())
                .list();
        salesOrder.setSalesOrderItemList(itemList);
        return AjaxResult.success(salesOrder);
    }

    /**
     * 新增销售订单
     */
    @PreAuthorize("@ss.hasPermi('sales:order:add')")
    @Log(title = "销售订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody SalesOrder salesOrder) {
        return AjaxResult.success(this.salesOrderService.saveOrder(salesOrder));
    }

    /**
     * 修改销售订单
     */
    @PreAuthorize("@ss.hasPermi('sales:order:edit')")
    @Log(title = "销售订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody SalesOrder salesOrder) {
        return AjaxResult.success(this.salesOrderService.updateSalesOrder(salesOrder));
    }

    /**
     * 删除销售订单
     */
    @PreAuthorize("@ss.hasPermi('sales:order:remove')")
    @Log(title = "销售订单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{salesOrderIds}")
    public AjaxResult remove(@PathVariable List<Long> salesOrderIds) {
        // -t3rik
        // 多用格式化代码，并且去掉无用的包
        StringBuffer sb=this.salesOrderService.deleteByIds(salesOrderIds);
        // -t3rik
        // 简单的if else 用三元运算符
        // if(sb.length()>0){
        //     return AjaxResult.error(sb.toString());
        // }else{
        //     return  AjaxResult.success();
        // }

        return sb.isEmpty() ? AjaxResult.success() : AjaxResult.error(sb.toString());
    }

    /**
     * 删除销售订单产品列
     */
    @PreAuthorize("@ss.hasPermi('sales:order:remove')")
    @Log(title = "销售订单", businessType = BusinessType.DELETE)
    @DeleteMapping("/delOrderItem/{itemIds}")
    public AjaxResult delOrderItem(@PathVariable List<Long> itemIds) {
        return  AjaxResult.success(this.salesOrderItemService.removeByIds(itemIds));
    }

    /**
     * 查询销售订单产品列列表
     */
    @PreAuthorize("@ss.hasPermi('sales:item:selectItem')")
    @GetMapping("/selectItem")
    public TableDataInfo itemList(SalesOrder salesOrder) {
        // 获取查询条件
        LambdaQueryWrapper<SalesOrder> queryWrapper = getQueryWrapper(salesOrder);
        List<SalesOrder> salesOrderList = this.salesOrderService.list(queryWrapper);
        return getDataTable(this.salesOrderItemService.getItemList(salesOrderList));
    }

    /**
     * 审批（提交、拒绝）
     */
    @PreAuthorize("@ss.hasPermi('sales:order:edit')")
    @Log(title = "销售订单审批", businessType = BusinessType.UPDATE)
    @Transactional
    @PutMapping("/refuse/{salesOrderId},{status}")
    public AjaxResult refuse(@PathVariable("salesOrderId") Long salesOrderId, @PathVariable("status") String status) {
        // -t3rik
        // 没记错的话，salesOrderId这个参数应该永远不会为null，不太确定，你可以用postman试试，
        if (StringUtils.isNull(salesOrderId)) {
            return AjaxResult.error("请先保存单据");
        }
        SalesOrder salesOrder = this.salesOrderService.getById(salesOrderId);
        // -t3rik
        // 这里也判断下数据是否能查询得到
        // Optional.ofNullable(salesOrder).orElseThrow(() -> new BusinessException(MsgConstants.PARAM_ERROR));
        // 状态类的参数最好不要用前端传的，前端的参数都不可靠
        salesOrder.setStatus(status);

        return AjaxResult.success(this.salesOrderService.refuse(salesOrder));
    }

    /**
     * 审批通过并生成生产订单
     */
    @PreAuthorize("@ss.hasPermi('sales:order:execute')")
    @Log(title = "客户订单", businessType = BusinessType.INSERT)
    @PostMapping("/execute/{salesOrderId}")
    public AjaxResult generateWorkOrder(@PathVariable("salesOrderId") String salesOrderId) throws Exception {
        // 重复代码最好想办法提取出来 -t3rik
        SalesOrder salesOrder = this.salesOrderService.getById(salesOrderId);
        // 查询是否已经添加销售列
        List<SalesOrderItem> itemList = this.salesOrderItemService.lambdaQuery()
                .eq(SalesOrderItem::getSalesOrderId, salesOrder.getSalesOrderId())
                .list();
        salesOrder.setSalesOrderItemList(itemList);
        // 数据校验
        CheckInfo check = this.check(salesOrder);
        // 未通过校验
        Assert.isTrue(check.getIsCheckPassed(), () -> new BusinessException(check.getMsg()));
        List<ProWorkorder> workOrders = this.salesOrderService.execute(salesOrder);
        return AjaxResult.success(workOrders);
    }

    /**
     * 推送mes
     */
    @PreAuthorize("@ss.hasPermi('sales:order:push')")
    @Log(title = "客户订单", businessType = BusinessType.INSERT)
    @PostMapping("/push/{salesOrderId}")
    public AjaxResult push(@PathVariable("salesOrderId") String salesOrderId) throws Exception {
        // 重复代码最好想办法提取出来 -t3rik
        SalesOrder salesOrder = this.salesOrderService.getById(salesOrderId);
        // 查询是否已经添加销售列
        List<SalesOrderItem> itemList = this.salesOrderItemService.lambdaQuery()
                .eq(SalesOrderItem::getSalesOrderId, salesOrder.getSalesOrderId())
                .list();
        salesOrder.setSalesOrderItemList(itemList);
        // 数据校验
        CheckInfo check = this.check(salesOrder);
        // 未通过校验
        Assert.isTrue(check.getIsCheckPassed(), () -> new BusinessException(check.getMsg()));

        return AjaxResult.success(mesWorkOrderService.pushMesWorkOrder(salesOrder));
    }

    /**
     * 生成生产订单数据校验方法
     */
    private CheckInfo check(SalesOrder salesOrder) {
        CheckInfo checkInfo = new CheckInfo();
        // 默认未通过
        checkInfo.setIsCheckPassed(Boolean.FALSE);
        if (salesOrder == null) {
            checkInfo.setMsg(MsgConstants.PARAM_ERROR);
            return checkInfo;
        }
        // 查询是否已经添加销售列
        List<SalesOrderItem> itemList = salesOrder.getSalesOrderItemList();
        if (CollectionUtil.isEmpty(itemList)) {
            checkInfo.setMsg("此单据下没有销售列,不允许生成生产订单");
            return checkInfo;
        }
        itemList.stream().forEach(f -> {
            // 查询是否已经添加需求物料数据
            // 这里的这个list就没用到吧？这段逻辑我记得好像是客户订单的，你确定一下你是否要用。 -t3rik
            // 另外forEach不需要先调用stream()，直接forEach就可以
            List<ProClientOrderItem> list = this.proClientOrderItemService.lambdaQuery()
                    .eq(ProClientOrderItem::getClientOrderId, f.getSalesOrderItemId())
                    .list();
            if (CollectionUtil.isEmpty(itemList)) {
                checkInfo.setMsg("未添加物料需求数据的客户订单,不允许生成生产订单");
            } else {
                checkInfo.setIsCheckPassed(Boolean.TRUE);
            }
        });

        // 校验已经添加的需求物料数据中,数量是否有0的数据,如果有,不允许生成生产订单
        List<SalesOrderItem> zeroQuantityList = itemList.stream().filter(f -> f.getSalesOrderQuantity().equals(new BigDecimal(0))).toList();
        if (CollectionUtil.isNotEmpty(zeroQuantityList)) {
            checkInfo.setMsg("订单中,存在订货数量0的数据,不允许生成生产订单");
            return checkInfo;
        }
        itemList.stream().forEach(f -> {
            // -t3rik
            // 这个for循环有问题吧？如果都不等于空，不是一直覆盖了吗？
            if (f.getWorkorderCode() != null) {
                String msg = MessageFormat.format("已经生成过生产订单(生成订单的编号为:{0}),不允许再次生成", f.getWorkorderCode());
                checkInfo.setMsg(msg);
            } else {
                // 校验通过
                checkInfo.setIsCheckPassed(Boolean.TRUE);
            }
        });

        return checkInfo;
    }

    /**
     * 获取查询条件
     */
    public LambdaQueryWrapper<SalesOrder> getQueryWrapper(SalesOrder salesOrder) {
        LambdaQueryWrapper<SalesOrder> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(salesOrder.getSalesOrderCode()), SalesOrder::getSalesOrderCode, salesOrder.getSalesOrderCode());
        queryWrapper.like(StringUtils.isNotEmpty(salesOrder.getSalesOrderName()), SalesOrder::getSalesOrderName, salesOrder.getSalesOrderName());
        Optional.ofNullable(salesOrder.getStatus()).ifPresent(status -> queryWrapper.eq(SalesOrder::getStatus, salesOrder.getStatus()));
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
        queryWrapper.between(params.get("beginTime") != null && params.get("endTime") != null, SalesOrder::getCreateTime, params.get("beginTime"), params.get("endTime"));
        return queryWrapper;
    }
}
