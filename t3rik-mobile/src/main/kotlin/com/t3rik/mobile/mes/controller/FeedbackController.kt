package com.t3rik.mobile.mes.controller

import com.baomidou.mybatisplus.core.toolkit.CollectionUtils
import com.t3rik.common.constant.UserConstants
import com.t3rik.common.core.controller.BaseController
import com.t3rik.common.core.page.TableDataInfo
import com.t3rik.common.enums.mes.OrderStatusEnum
import com.t3rik.common.utils.SecurityUtils
import com.t3rik.mes.pro.domain.ProTask
import com.t3rik.mes.pro.service.IProFeedbackService
import com.t3rik.mes.pro.service.IProTaskService
import com.t3rik.mobile.common.enums.CurrentIndexEnum
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


    @GetMapping("/list")
    fun getTaskList(task: ProTask): TableDataInfo {
        val mpPage = getMPPage(task)
        val paramByCurrentIndex = getParamByCurrentIndex(task.currentIndex)
        val page = this.taskService.lambdaQuery()
            .eq(ProTask::getTaskUserId, SecurityUtils.getUserId())
            .`in`(CollectionUtils.isNotEmpty(paramByCurrentIndex), ProTask::getStatus, paramByCurrentIndex)
            .orderByAsc(ProTask::getStatus)
            .orderByDesc(ProTask::getRequestDate)
            .page(mpPage)
        return getDataTableWithPage(page)
    }

    /**
     * 根据传入的页码查询对应的结果
     */
    fun getParamByCurrentIndex(currentIndex: String?): List<String> {
        val statusList = mutableListOf<String>()
        // 类似switch
        when (currentIndex) {
            // 未处理查询草稿和已确认
            CurrentIndexEnum.UNPROCESSED.code -> {
                return statusList.apply {
                    add(OrderStatusEnum.PREPARE.code)
                    add(OrderStatusEnum.CONFIRMED.code)
                }
            }
            // 已处理查询审批中，审批通过，已拒绝
            CurrentIndexEnum.PROCESSED.code -> {
                return statusList.apply {
                    add(OrderStatusEnum.APPROVING.code)
                    add(OrderStatusEnum.APPROVED.code)
                    add(OrderStatusEnum.REFUSE.code)
                }
            }
            // 已完成的单据
            CurrentIndexEnum.FINISHED.code -> {
                return statusList.apply {
                    add(OrderStatusEnum.FINISHED.code)
                }
            }
            // 查询全部
            else -> return statusList
        }
    }
}