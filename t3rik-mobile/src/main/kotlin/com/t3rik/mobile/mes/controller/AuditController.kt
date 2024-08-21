package com.t3rik.mobile.mes.controller

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper
import com.baomidou.mybatisplus.core.toolkit.CollectionUtils
import com.t3rik.common.constant.MsgConstants
import com.t3rik.common.constant.UserConstants
import com.t3rik.common.core.controller.BaseController
import com.t3rik.common.core.domain.AjaxResult
import com.t3rik.common.core.page.TableDataInfo
import com.t3rik.common.enums.mes.OrderStatusEnum
import com.t3rik.common.exception.BusinessException
import com.t3rik.common.utils.SecurityUtils
import com.t3rik.common.utils.StringUtils
import com.t3rik.mes.pro.domain.ProFeedback
import com.t3rik.mes.pro.domain.ProTask
import com.t3rik.mes.pro.dto.TaskDto
import com.t3rik.mes.pro.service.IProFeedbackService
import com.t3rik.mes.pro.service.IProTaskService
import com.t3rik.mobile.common.enums.CurrentIndexEnum
import com.t3rik.mobile.common.ktextend.isNonPositive
import com.t3rik.mobile.mes.service.IFeedbackService
import io.swagger.annotations.ApiOperation
import jakarta.annotation.Resource
import kotlinx.coroutines.runBlocking
import org.springframework.web.bind.annotation.*


/**
 * 审核
 * @author t3rik
 * @date 2024/8/12 15:50
 */
@RestController
@RequestMapping(UserConstants.MOBILE_PATH + "/audit")
class AuditController : BaseController() {

    @Resource
    lateinit var proTaskService: IProTaskService

    @Resource
    lateinit var proFeedbackService: IProFeedbackService

    @Resource
    lateinit var feedbackService: IFeedbackService


    /**
     * 查询任务和报工信息
     */
    @ApiOperation("查询报工详细信息")
    @GetMapping("/getTaskAndAudit/{taskId}")
    fun getTaskAndFeedback(@PathVariable taskId: Long): AjaxResult {
        // 小于等于0 抛异常
        taskId.isNonPositive { MsgConstants.PARAM_ERROR }
        val taskAndFeedback = runBlocking { feedbackService.getTaskAndFeedback(taskId, OrderStatusEnum.APPROVING) }
        return AjaxResult.success(taskAndFeedback)
    }

    /**
     * 审核通过
     */
    @ApiOperation("审核通过")
    @PutMapping("/pass")
    fun passed(@RequestParam ids: MutableList<Long>): AjaxResult {
        checkParam(ids)
        this.proFeedbackService.lambdaUpdate()
            .set(ProFeedback::getStatus, OrderStatusEnum.FINISHED.code)
            .`in`(ProFeedback::getRecordId, ids)
            .update(ProFeedback())
        return AjaxResult.success()
    }

    /**
     * 审核拒绝
     */
    @ApiOperation("审核拒绝")
    @PutMapping("/refuse")
    fun refused(@RequestParam ids: MutableList<Long>): AjaxResult {
        checkParam(ids)
        this.proFeedbackService.lambdaUpdate()
            .set(ProFeedback::getStatus, OrderStatusEnum.REFUSE.code)
            .`in`(ProFeedback::getRecordId, ids)
            .update(ProFeedback())
        return AjaxResult.success()
    }

    private fun checkParam(ids: MutableList<Long>) {
        if (CollectionUtils.isEmpty(ids)) {
            throw BusinessException(MsgConstants.SELECT_AT_LEAST_ONE)
        }
        if (!SecurityUtils.isAdmin(SecurityUtils.getUserId())) {
            throw BusinessException(MsgConstants.NO_AUDIT_AUTH)
        }
    }

    /**
     * 查询任务列表
     */
    @ApiOperation("查询报工列表")
    @GetMapping("/list")
    fun getTaskList(task: ProTask): TableDataInfo {
        val page = getMPPage(TaskDto())
        val queryWrapper = QueryWrapper<TaskDto>()
        queryWrapper.gt(task.currentIndex.equals(CurrentIndexEnum.UNPROCESSED.code), "approvingCount", 0)
        queryWrapper.likeRight(StringUtils.isNotBlank(task.taskName), "task_name", task.taskName)
        return getDataTableWithPage(this.proTaskService.getTaskListAndFeedbackCount(page, queryWrapper))
    }

}