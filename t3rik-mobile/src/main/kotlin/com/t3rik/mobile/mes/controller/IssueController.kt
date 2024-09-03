package com.t3rik.mobile.mes.controller

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils
import com.t3rik.common.annotation.RepeatSubmit
import com.t3rik.common.constant.MsgConstants
import com.t3rik.common.constant.UserConstants
import com.t3rik.common.core.controller.BaseController
import com.t3rik.common.core.domain.AjaxResult
import com.t3rik.common.core.page.TableDataInfo
import com.t3rik.common.exception.BusinessException
import com.t3rik.common.utils.SecurityUtils
import com.t3rik.common.utils.StringUtils
import com.t3rik.mes.pro.domain.ProTask
import com.t3rik.mes.pro.dto.TaskDto
import com.t3rik.mes.pro.service.IProTaskService
import com.t3rik.mobile.common.ktextend.isNonPositive
import com.t3rik.mobile.mes.dto.IssueRequestDTO
import com.t3rik.mobile.mes.service.IIssueService
import io.swagger.annotations.ApiOperation
import jakarta.annotation.Resource
import org.springframework.web.bind.annotation.*


/**
 * 领料
 * @author t3rik
 * @date 2024/8/22 18:29
 */
@RestController
@RequestMapping(UserConstants.MOBILE_PATH + "/issue")
class IssueController : BaseController() {

    @Resource
    lateinit var proTaskService: IProTaskService

    @Resource
    lateinit var issueService: IIssueService

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
    @RepeatSubmit
    @PostMapping
    fun issue(@RequestBody issueRequestDTO: IssueRequestDTO): AjaxResult {
        // 参数校验
        check(issueRequestDTO)
        // 领料申请
        this.issueService.issue(issueRequestDTO)
        return AjaxResult.success()
    }

    /**
     * 参数校验
     */
    private fun check(issueRequestDTO: IssueRequestDTO) {
        if (issueRequestDTO.workorderCode.isNullOrBlank()) {
            throw BusinessException(MsgConstants.PARAM_ERROR)
        }
        issueRequestDTO.workorderId.isNonPositive { MsgConstants.PARAM_ERROR }
        val tasks = this.proTaskService.lambdaQuery()
            .eq(ProTask::getWorkorderId, issueRequestDTO.workorderId)
            .eq(ProTask::getWorkorderCode, issueRequestDTO.workorderCode)
            .eq(ProTask::getTaskUserId, SecurityUtils.getUserId())
            .list()
        if (CollectionUtils.isEmpty(tasks)) {
            throw BusinessException(MsgConstants.NO_OPERATION_AUTH)
        }
        if (issueRequestDTO.issueLineList.isEmpty()) {
            throw BusinessException(MsgConstants.SELECT_AT_ADD_ONE)
        }
    }
}