package com.t3rik.mobile.mes.dto

import com.t3rik.mes.pro.domain.ProFeedback
import com.t3rik.mes.pro.domain.ProTask


/**
 *
 * @author t3rik
 * @date 2024/8/8 14:31
 */
class TaskAndFeedbackDto(val task: ProTask,val feedbackList: MutableList<ProFeedback>)