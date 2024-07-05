package com.t3rik.mobile.mes.service


/**
 * 报工相关
 * @author t3rik
 * @date 2024/7/3 23:15
 */
interface IFeedbackService {

    /**
     * 根据传入的前端页码，返回不同的单据状态
     */
    fun getParamByCurrentIndex(currentIndex: String?): List<String>
}