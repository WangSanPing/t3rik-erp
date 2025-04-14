package com.t3rik.mes.analytics.controller;

import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.mes.md.service.IItemTypeService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据分析
 *
 * @author t3rik
 * @date 2025/4/13 17:06
 */
@RestController
@RequestMapping("/analytics")
public class AnalyticsController {

    @Resource
    private IItemTypeService iItemTypeService;


    @GetMapping("/getStock")
    public AjaxResult getStock() {
        return AjaxResult.success();
    }

    @GetMapping("/getWorkorder")
    public AjaxResult getWorkorder() {
        return AjaxResult.success();
    }

    @GetMapping("/getItem")
    public AjaxResult getItem() {
        return AjaxResult.success();
    }


}
