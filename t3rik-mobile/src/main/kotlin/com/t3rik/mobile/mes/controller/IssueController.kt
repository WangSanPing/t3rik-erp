package com.t3rik.mobile.mes.controller

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.t3rik.common.constant.UserConstants
import com.t3rik.common.core.controller.BaseController
import com.t3rik.common.core.domain.AjaxResult
import com.t3rik.common.core.page.TableDataInfo
import com.t3rik.common.utils.SecurityUtils
import com.t3rik.common.utils.StringUtils
import com.t3rik.mes.pro.domain.ProTask
import com.t3rik.mes.pro.dto.TaskDto
import com.t3rik.mes.pro.service.IProTaskService
import com.t3rik.mobile.mes.dto.IssueRequestDTO
import io.swagger.annotations.ApiOperation
import jakarta.annotation.Resource
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


/**
 * 领料
 * @author t3rik
 * @date 2024/8/22 18:29
 */
@RestController
@RequestMapping(UserConstants.MOBILE_PATH + "/issue")
class IssueController : BaseController() {

    @Resource
    lateinit var proTaskService: IProTaskService;

    /**
     * 查询任务列表
     */
    @ApiOperation("查询报工列表")
    @GetMapping("/list")
    fun getTaskList(task: ProTask): TableDataInfo {
        val page = getMPPage(TaskDto())
        val queryWrapper = QueryWrapper<TaskDto>()
        queryWrapper.likeRight(StringUtils.isNotBlank(task.taskName), "task_name", task.taskName)
        queryWrapper.eq("task_user_id", SecurityUtils.getUserId())
        return getDataTableWithPage(this.proTaskService.getTaskListAndIssueCount(page, queryWrapper))
    }

    @ApiOperation("领料申请")
    @PostMapping
    fun issue(@RequestBody issueRequestDTO: IssueRequestDTO):AjaxResult{
        logger.info(issueRequestDTO.toString())
        return AjaxResult.success()
    }
}