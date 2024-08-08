package com.t3rik.mes.pro.controller;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.lang.Assert;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.t3rik.common.annotation.Log;
import com.t3rik.common.constant.MsgConstants;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.domain.CheckInfo;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.enums.BusinessType;
import com.t3rik.common.enums.mes.ClientOrderStatusEnum;
import com.t3rik.common.exception.BusinessException;
import com.t3rik.common.utils.StringUtils;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.common.validated.group.UpdateGroup;
import com.t3rik.mes.pro.domain.ProClientOrder;
import com.t3rik.mes.pro.domain.ProClientOrderItem;
import com.t3rik.mes.pro.domain.ProWorkorder;
import com.t3rik.mes.pro.service.IProClientOrderItemService;
import com.t3rik.mes.pro.service.IProClientOrderService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.text.MessageFormat;
import java.util.List;
import java.util.Map;

/**
 * 客户订单Controller
 *
 * @author t3rik
 * @date 2024-05-01
 */
@RestController
@RequestMapping("/pro/client-order")
public class ProClientOrderController extends BaseController {

    @Resource
    private IProClientOrderService proClientOrderService;
    @Resource
    private IProClientOrderItemService proClientOrderItemService;


    // @GetMapping("/test/test")
    // @EnableOnOff
    // public void test(Boolean onOff){
    //     System.out.println(onOff);
    // }

    /**
     * 新增客户订单
     */
    @PreAuthorize("@ss.hasPermi('pro:clientorder:add')")
    @Log(title = "客户订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody @Validated ProClientOrder proClientOrder) {
        ProClientOrder data = this.proClientOrderService.lambdaQuery()
                .eq(ProClientOrder::getClientOrderCode, proClientOrder.getClientOrderCode()).one();
        // 校验是否存在相同的订单编码
        Assert.isNull(data, () -> new BusinessException("存在相同的订单编码,请修改订单编码后再重试"));
        this.proClientOrderService.saveClientOrder(proClientOrder);


        return AjaxResult.success(proClientOrder.getClientOrderId());
    }

    /**
     * 生成生产订单
     */
    @PreAuthorize("@ss.hasPermi('pro:clientorder:add')")
    @Log(title = "客户订单", businessType = BusinessType.INSERT)
    @PostMapping("/generateWorkOrder/{clientOrderId}")
    public AjaxResult generateWorkOrder(@PathVariable("clientOrderId") String clientOrderId) {
        ProClientOrder clientOrder = this.proClientOrderService.getById(clientOrderId);
        // 数据校验
        CheckInfo check = this.check(clientOrder);
        // 未通过校验
        Assert.isTrue(check.getIsCheckPassed(), () -> new BusinessException(check.getMsg()));
        ProWorkorder workOrder = this.proClientOrderService.generateWorkOrder(clientOrder);
        return AjaxResult.success(workOrder);
    }

    /**
     * 生成生产订单数据校验方法
     */
    private CheckInfo check(ProClientOrder clientOrder) {
        CheckInfo checkInfo = new CheckInfo();
        // 默认未通过
        checkInfo.setIsCheckPassed(Boolean.FALSE);
        if (clientOrder == null) {
            checkInfo.setMsg(MsgConstants.PARAM_ERROR);
            return checkInfo;
        }
        // 查询是否已经添加需求物料数据
        List<ProClientOrderItem> itemList = this.proClientOrderItemService.lambdaQuery()
                .eq(ProClientOrderItem::getClientOrderId, clientOrder.getClientOrderId())
                .list();
        if (CollectionUtil.isEmpty(itemList)) {
            checkInfo.setMsg("未添加物料需求数据的客户订单,不允许生成生产订单");
            return checkInfo;
        }

        // 最多只能添加4条数据,对应4个级别
        if (itemList.size() > 4) {
            checkInfo.setMsg("最多只能添加4行数据");
            return checkInfo;
        }

        // 校验层级是否为空
        List<ProClientOrderItem> levelList = itemList.stream().filter(f -> StringUtils.isBlank(f.getLevel())).toList();
        if (CollectionUtil.isNotEmpty(levelList)) {
            checkInfo.setMsg("存在层级为空的数据,不允许生成生产订单");
            return checkInfo;
        }

        // 校验已经添加的需求物料数据中,数量是否有0的数据,如果有,不允许生成生产订单
        List<ProClientOrderItem> zeroQuantityList = itemList.stream().filter(f -> f.getQuantity().equals(new BigDecimal(0))).toList();
        if (CollectionUtil.isNotEmpty(zeroQuantityList)) {
            checkInfo.setMsg("已添加的物料中,需求数量存在0的数据,不允许生成生产订单");
            return checkInfo;
        }

        // 校验是否已经生成过生产订单
        if (ClientOrderStatusEnum.WORK_ORDER_FINISHED.getCode().equals(clientOrder.getStatus())) {
            String msg = MessageFormat.format("已经生成过生产订单(生成订单的编号为:{0}),不允许再次生成", clientOrder.getWorkorderCode());
            checkInfo.setMsg(msg);
            return checkInfo;
        }
        // 校验通过
        checkInfo.setIsCheckPassed(Boolean.TRUE);
        return checkInfo;
    }

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
        ExcelUtil<ProClientOrder> util = new ExcelUtil<ProClientOrder>(ProClientOrder.class);
        util.exportExcel(response, list, "客户订单数据");
    }

    /**
     * 获取客户订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('pro:clientorder:query')")
    @GetMapping(value = "/{clientOrderId}")
    public AjaxResult getInfo(@PathVariable("clientOrderId") Long clientOrderId) {
        ProClientOrder data = this.proClientOrderService.getById(clientOrderId);
        // 校验数据是否存在
        Assert.notNull(data, () -> new BusinessException(MsgConstants.PARAM_ERROR));
        return AjaxResult.success(data);
    }

    /**
     * 修改客户订单
     */
    @PreAuthorize("@ss.hasPermi('pro:clientorder:edit')")
    @Log(title = "客户订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody @Validated(UpdateGroup.class) ProClientOrder proClientOrder) {
        ProClientOrder data = this.proClientOrderService.lambdaQuery()
                .eq(ProClientOrder::getClientOrderId, proClientOrder.getClientOrderId()).one();
        // 校验数据是否存在
        Assert.notNull(data, () -> new BusinessException(MsgConstants.PARAM_ERROR));
        return toAjax(this.proClientOrderService.updateById(proClientOrder));
    }

    /**
     * 删除客户订单
     */
    @PreAuthorize("@ss.hasPermi('pro:clientorder:remove')")
    @Log(title = "客户订单", businessType = BusinessType.DELETE)
    @DeleteMapping("/{clientOrderIds}")
    public AjaxResult remove(@PathVariable List<Long> clientOrderIds) {
        // 删除子表
        this.proClientOrderItemService
                .remove(new LambdaQueryWrapper<ProClientOrderItem>()
                        .in(ProClientOrderItem::getClientOrderId, clientOrderIds));
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
        queryWrapper.between(params.get("beginTime") != null && params.get("endTime") != null, ProClientOrder::getCreateTime, params.get("beginTime"), params.get("endTime"));
        return queryWrapper;
    }
}
