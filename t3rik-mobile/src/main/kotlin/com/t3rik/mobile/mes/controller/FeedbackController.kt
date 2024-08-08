package com.t3rik.mobile.mes.controller

import com.t3rik.common.annotation.RepeatSubmit
import com.t3rik.common.constant.MsgConstants
import com.t3rik.common.constant.UserConstants
import com.t3rik.common.core.controller.BaseController
import com.t3rik.common.core.domain.AjaxResult
import com.t3rik.common.core.page.TableDataInfo
import com.t3rik.common.exception.BusinessException
import com.t3rik.common.utils.SecurityUtils
import com.t3rik.mes.pro.domain.ProFeedback
import com.t3rik.mes.pro.domain.ProTask
import com.t3rik.mes.pro.service.IProTaskService
import com.t3rik.mobile.common.ktextend.isNonPositive
import com.t3rik.mobile.mes.service.IFeedbackService
import io.swagger.annotations.ApiOperation
import isZero
import jakarta.annotation.Resource
import kotlinx.coroutines.runBlocking
import org.springframework.web.bind.annotation.*

/**
 * 报工相关
 * @author t3rik
 * @date 2024/6/13
 */
@RestController
@RequestMapping(UserConstants.MOBILE_PATH + "/feedback")
class FeedbackController : BaseController() {

    @Resource
    lateinit var taskService: IProTaskService

    @Resource
    lateinit var feedbackService: IFeedbackService;


    /**
     * 查询任务列表
     */
    @ApiOperation("查询报工列表")
    @GetMapping("/list")
    fun getTaskList(task: ProTask): TableDataInfo {
        return getDataTableWithPage(feedbackService.getPageByCurrentIndex(task, getMPPage(task)))
    }

    /**
     * 查询任务详细信息
     */
    @ApiOperation("查询报工详细信息")
    @GetMapping("/{taskId}")
    fun getTaskInfo(@PathVariable taskId: Long): AjaxResult {
        // 小于等于0 抛异常
        taskId.isNonPositive { MsgConstants.PARAM_ERROR }
        val task = this.taskService.lambdaQuery()
            .eq(ProTask::getTaskId, taskId)
            .eq(ProTask::getTaskUserId, SecurityUtils.getUserId())
            .one() ?: throw BusinessException(MsgConstants.PARAM_ERROR)
        return AjaxResult.success(task)
    }

    /**
     * 查询任务和报工信息
     */
    @ApiOperation("查询报工详细信息")
    @GetMapping("/getTaskAndFeedback/{taskId}")
    fun getTaskAndFeedback(@PathVariable taskId: Long): AjaxResult {
        // 小于等于0 抛异常
        taskId.isNonPositive { MsgConstants.PARAM_ERROR }
        val taskAndFeedback = runBlocking { feedbackService.getTaskAndFeedback(taskId) }
        return AjaxResult.success(taskAndFeedback)
    }

    @ApiOperation("报工")
    @RepeatSubmit
    @PostMapping
    fun feedback(@RequestBody feedback: ProFeedback): AjaxResult {
        val task = this.taskService.getById(feedback.taskId) ?: return AjaxResult.error(MsgConstants.PARAM_ERROR)
        if (feedback.quantityQualified == null || feedback.quantityQualified.isZero()) {
            return AjaxResult.error(MsgConstants.CAN_NOT_BE_ZERO)
        }
        // 确认排产数量
        feedback.quantity = task.quantity
        return AjaxResult.success(this.feedbackService.addFeedback(feedback))
    }
}
