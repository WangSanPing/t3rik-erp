package com.t3rik.mes.pro.controller;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.t3rik.common.annotation.Log;
import com.t3rik.common.constant.UserConstants;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.enums.BusinessType;
import com.t3rik.common.utils.StringUtils;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.mes.pro.domain.*;
import com.t3rik.mes.pro.service.*;
import com.t3rik.system.strategy.AutoCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * 生产任务Controller
 *
 * @author yinjinlu
 * @date 2022-05-14
 */

@RestController
@RequestMapping("/pro/task-dispatch")
public class ProTaskDispatchController extends BaseController {
    @Autowired
    private IProTaskService proTaskService;

    /**
     * 查询生产任务列表
     */
    @PreAuthorize("@ss.hasPermi('pro:taskdispatch:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProTask proTask) {
        // 获取查询条件
        LambdaQueryWrapper<ProTask> queryWrapper = getQueryWrapper(proTask);
        // 组装分页
        Page<ProTask> page = getMPPage(proTask);
        // 查询
        this.proTaskService.page(page, queryWrapper);
        return getDataTableWithPage(page);
    }

    /**
     * 导出生产任务列表
     */
    @PreAuthorize("@ss.hasPermi('pro:taskdispatch:export')")
    @Log(title = "生产任务", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProTask proTask) {
        // 获取查询条件
        LambdaQueryWrapper<ProTask> queryWrapper = getQueryWrapper(proTask);
        List<ProTask> list = this.proTaskService.list(queryWrapper);
        ExcelUtil<ProTask> util = new ExcelUtil<ProTask>(ProTask.class);
        util.exportExcel(response, list, "生产任务数据");
    }

    /**
     * 获取生产任务详细信息
     */
    @PreAuthorize("@ss.hasPermi('pro:taskdispatch:query')")
    @GetMapping(value = "/{taskId}")
    public AjaxResult getInfo(@PathVariable("taskId") Long taskId) {
        return AjaxResult.success(this.proTaskService.getById(taskId));
    }

    /**
     * 新增生产任务
     */
    @PreAuthorize("@ss.hasPermi('pro:taskdispatch:add')")
    @Log(title = "生产任务", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody ProTask proTask) {
        return toAjax(this.proTaskService.save(proTask));
    }

    /**
     * 修改生产任务
     */
    @PreAuthorize("@ss.hasPermi('pro:taskdispatch:edit')")
    @Log(title = "生产任务", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody ProTask proTask) {
        return toAjax(this.proTaskService.updateById(proTask));
    }

    /**
     * 删除生产任务
     */
    @PreAuthorize("@ss.hasPermi('pro:taskdispatch:remove')")
    @Log(title = "生产任务", businessType = BusinessType.DELETE)
    @DeleteMapping("/{taskIds}")
    public AjaxResult remove(@PathVariable List<Long> taskIds) {
        return toAjax(this.proTaskService.removeByIds(taskIds));
    }

    /**
     * 获取查询条件
     */
    public LambdaQueryWrapper<ProTask> getQueryWrapper(ProTask proTask) {
        LambdaQueryWrapper<ProTask> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(proTask.getTaskCode() != null, ProTask::getTaskCode, proTask.getTaskCode());
        queryWrapper.like(StringUtils.isNotEmpty(proTask.getTaskName()), ProTask::getTaskName, proTask.getTaskName());
        queryWrapper.eq(proTask.getWorkorderId() != null, ProTask::getWorkorderId, proTask.getWorkorderId());
        queryWrapper.eq(proTask.getWorkorderCode() != null, ProTask::getWorkorderCode, proTask.getWorkorderCode());
        queryWrapper.like(StringUtils.isNotEmpty(proTask.getWorkorderName()), ProTask::getWorkorderName, proTask.getWorkorderName());
        queryWrapper.eq(proTask.getWorkstationId() != null, ProTask::getWorkstationId, proTask.getWorkstationId());
        queryWrapper.eq(proTask.getWorkstationCode() != null, ProTask::getWorkstationCode, proTask.getWorkstationCode());
        queryWrapper.like(StringUtils.isNotEmpty(proTask.getWorkstationName()), ProTask::getWorkstationName, proTask.getWorkstationName());

        Map<String, Object> params = proTask.getParams();
        queryWrapper.between(params.get("beginTime") != null && params.get("endTime") != null, ProTask::getCreateTime, params.get("beginTime"), params.get("endTime"));
        return queryWrapper;
    }
}
