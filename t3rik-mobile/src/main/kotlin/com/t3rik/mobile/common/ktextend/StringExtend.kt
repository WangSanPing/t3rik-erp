package com.t3rik.mobile.common.ktextend

import com.t3rik.common.constant.MsgConstants


/**
 * String类扩展
 * @author t3rik
 * @date 2025/3/15 23:28
 */

/**
 * 字符串为空后抛异常
 */
fun String?.requireNotNullOrBlank(errorMessage: String = MsgConstants.PARAM_ERROR) {
    require(!isNullOrBlank()) { errorMessage }
}