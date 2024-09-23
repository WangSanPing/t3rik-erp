package com.t3rik.mes.api.controller;

import com.t3rik.common.core.controller.BaseController;
import com.t3rik.mes.api.service.IMesWorkOrderService;
import com.t3rik.mes.pro.service.IProClientOrderItemService;
import com.t3rik.mes.sales.service.ISalesOrderItemService;
import com.t3rik.mes.sales.service.ISalesOrderService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * MES工单Controller
 *
 * @author t3rik
 * @date 2024-09-19
 */
@RestController
@RequestMapping("/open/work")
public class WorkOrderController  extends BaseController {
    @Autowired
    private ISalesOrderService salesOrderService;
    @Autowired
    private ISalesOrderItemService salesOrderItemService;
    @Resource
    private IMesWorkOrderService mesWorkOrderService;
}
