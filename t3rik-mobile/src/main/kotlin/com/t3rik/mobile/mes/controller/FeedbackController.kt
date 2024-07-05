package com.t3rik.mobile.mes.controller

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils
import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.t3rik.common.constant.UserConstants
import com.t3rik.common.core.controller.BaseController
import com.t3rik.common.core.page.TableDataInfo
import com.t3rik.common.utils.SecurityUtils
import com.t3rik.mes.pro.domain.ProTask
import com.t3rik.mes.pro.service.IProTaskService
import com.t3rik.mobile.mes.service.IFeedbackService
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.annotation.Resource

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
     * 查询报工列表
     */
    @ApiOperation("查询报工列表")
    @GetMapping("/list")
    fun getTaskList(task: ProTask): TableDataInfo {
        val mpPage = getMPPage(task)
        val paramByCurrentIndex = feedbackService.getParamByCurrentIndex(task.currentIndex)
        val page = this.taskService.lambdaQuery()
            .eq(ProTask::getTaskUserId, SecurityUtils.getUserId())
            .`in`(CollectionUtils.isNotEmpty(paramByCurrentIndex), ProTask::getStatus, paramByCurrentIndex)
            .orderByAsc(ProTask::getStatus)
            .orderByDesc(ProTask::getRequestDate)
            .page(mpPage)
        return getDataTableWithPage(page)
    }
}