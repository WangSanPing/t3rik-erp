package com.t3rik.mes.pro.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.t3rik.common.annotation.Log;
import com.t3rik.common.core.controller.BaseController;
import com.t3rik.common.core.domain.AjaxResult;
import com.t3rik.common.core.page.TableDataInfo;
import com.t3rik.common.enums.BusinessType;
import com.t3rik.common.utils.StringUtils;
import com.t3rik.common.utils.poi.ExcelUtil;
import com.t3rik.mes.pro.domain.*;
import com.t3rik.mes.pro.dto.AssignUsersDTO;
import com.t3rik.mes.pro.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.groupingBy;

/**
 * 生产派单Controller
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
     * 查询生产派单列表
     */
    @PreAuthorize("@ss.hasPermi('pro:taskdispatch:list')")
    @GetMapping("/list")
    public TableDataInfo list(ProTask proTask) {
        // 获取查询条件
        LambdaQueryWrapper<ProTask> queryWrapper = getQueryWrapper(proTask);
        // 组装分页
        Page<ProTask> page = getMPPage(proTask);
        //根据工单分组展示
        proTaskService.listGroupByWorkOrder(queryWrapper, page);
        return getDataTableWithPage(page);
    }

    /**
     * 导出生产派单列表
     */
    @PreAuthorize("@ss.hasPermi('pro:taskdispatch:export')")
    @Log(title = "生产派单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, ProTask proTask) {
        // 获取查询条件
        LambdaQueryWrapper<ProTask> queryWrapper = getQueryWrapper(proTask);
        List<ProTask> list = this.proTaskService.list(queryWrapper);
        ExcelUtil<ProTask> util = new ExcelUtil<ProTask>(ProTask.class);
        util.exportExcel(response, list, "生产派单数据");
    }

    /**
     * 获取生产派单详细信息
     */
    @PreAuthorize("@ss.hasPermi('pro:taskdispatch:query')")
    @GetMapping(value = "/{taskId}")
    public AjaxResult getInfo(@PathVariable("taskId") Long taskId) {
        return AjaxResult.success(this.proTaskService.getById(taskId));
    }


    /**
     * 生产派单指派用户-新增/修改
     */
    @PreAuthorize("@ss.hasPermi('pro:taskdispatch:addAssignUsers')")
    @Log(title = "生产派单", businessType = BusinessType.UPDATE)
    @PostMapping("/addAssignUsers")
    public AjaxResult addAssignUsers(@RequestBody AssignUsersDTO assignUsersDto) {
        return AjaxResult.success(proTaskService.addAssignUsers(assignUsersDto.getTaskIds(), assignUsersDto.getTaskUserId(), assignUsersDto.getTaskBy()));
    }

    /**
     * 删除生产任务指派人
     */
    @PreAuthorize("@ss.hasPermi('pro:taskdispatch:removeAssignUsers')")
    @Log(title = "生产派单", businessType = BusinessType.UPDATE)
    @PutMapping("/removeAssignUsers/{taskIds}")
    public AjaxResult removeAssignUsers(@PathVariable List<Long> taskIds) {
        return toAjax(this.proTaskService
                .lambdaUpdate()
                .in(ProTask::getTaskId, taskIds)
                .set(ProTask::getTaskBy, null)
                .set(ProTask::getTaskUserId, null)
                .update(new ProTask()));
    }

    /**
     * 获取查询条件
     */
    public LambdaQueryWrapper<ProTask> getQueryWrapper(ProTask proTask) {
        LambdaQueryWrapper<ProTask> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(proTask.getTaskCode() != null, ProTask::getTaskCode, proTask.getTaskCode());
        queryWrapper.like(StringUtils.isNotEmpty(proTask.getTaskName()), ProTask::getTaskName, proTask.getTaskName());
        queryWrapper.like(StringUtils.isNotEmpty(proTask.getWorkorderCode()), ProTask::getWorkorderCode, proTask.getWorkorderCode());
        queryWrapper.like(StringUtils.isNotEmpty(proTask.getWorkorderName()), ProTask::getWorkorderName, proTask.getWorkorderName());
        queryWrapper.like(StringUtils.isNotEmpty(proTask.getProcessName()), ProTask::getProcessName, proTask.getProcessName());
        Map<String, Object> params = proTask.getParams();
        queryWrapper.between(params.get("beginTime") != null && params.get("endTime") != null, ProTask::getCreateTime, params.get("beginTime"), params.get("endTime"));
        queryWrapper.groupBy(ProTask::getTaskId);
        return queryWrapper;
    }
}
