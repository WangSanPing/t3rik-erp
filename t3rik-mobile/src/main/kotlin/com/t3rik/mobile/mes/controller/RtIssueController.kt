package com.t3rik.mobile.mes.controller

import com.t3rik.common.constant.UserConstants
import com.t3rik.common.core.controller.BaseController
import com.t3rik.common.core.page.TableDataInfo
import com.t3rik.mes.pro.domain.ProTask
import com.t3rik.mobile.mes.service.IFeedbackService
import io.swagger.annotations.ApiOperation
import jakarta.annotation.Resource
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController


/**
 * 退料Controller
 * @author t3rik
 * @date 2025/3/9 22:43
 */
@RestController
@RequestMapping(UserConstants.MOBILE_PATH + "/rtissue")
class RtIssueController : BaseController() {

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
}