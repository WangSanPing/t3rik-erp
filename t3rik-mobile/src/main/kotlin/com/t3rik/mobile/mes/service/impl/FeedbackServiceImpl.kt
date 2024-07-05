package com.t3rik.mobile.mes.service.impl

import com.t3rik.common.enums.mes.OrderStatusEnum
import com.t3rik.mobile.common.enums.CurrentIndexEnum
import com.t3rik.mobile.mes.service.IFeedbackService
import org.springframework.stereotype.Service


/**
 * 报工相关
 * @author t3rik
 * @date 2024/7/3 23:16
 */
@Service
class FeedbackServiceImpl : IFeedbackService {

    /**
     * 根据传入的前端页码，返回不同的单据状态
     */
    override fun getParamByCurrentIndex(currentIndex: String?): List<String> {
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