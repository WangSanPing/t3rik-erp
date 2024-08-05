package com.t3rik.mobile.mes.service

import com.baomidou.mybatisplus.extension.plugins.pagination.Page
import com.t3rik.mes.pro.domain.ProFeedback
import com.t3rik.mes.pro.domain.ProTask


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

    /**
     * 根据传入的前端页码，返回数据
     */
    fun getPageByCurrentIndex(task: ProTask,page: Page<ProTask>): Page<ProTask>

    /**
     * 新增报工
     */
    fun addFeedback(proFeedback: ProFeedback): String
}