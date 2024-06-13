package com.t3rik.mobile.mes.controller

import com.t3rik.common.constant.UserConstants
import com.t3rik.common.core.controller.BaseController
import com.t3rik.common.core.page.TableDataInfo
import com.t3rik.common.utils.SecurityUtils
import com.t3rik.common.utils.StringUtils
import com.t3rik.mes.pro.domain.ProFeedback
import com.t3rik.mes.pro.service.IProFeedbackService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.annotation.Resource


@RestController
@RequestMapping(UserConstants.MOBILE_PATH + "/feedback")
class FeedbackController : BaseController() {

    @Resource
    lateinit var feedbackService: IProFeedbackService

    @GetMapping("/list")
    fun getFeedbackList(feedback: ProFeedback): TableDataInfo {
        val mpPage = getMPPage(feedback)
        val page = this.feedbackService.lambdaQuery()
            .eq(ProFeedback::getUserId, SecurityUtils.getUserId())
            .eq(StringUtils.isNotBlank(feedback.status), ProFeedback::getStatus, feedback.status)
            .page(mpPage)
        return getDataTableWithPage(page)
    }
}