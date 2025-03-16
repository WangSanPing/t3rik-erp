package com.t3rik.mobile.common.ktextend

import com.t3rik.common.constant.MsgConstants


/**
 * Long类型扩展
 * @author t3rik
 * @date 2024/8/8 13:49
 */

/**
 * 判断一个长整形数是否小于等于0或者为null
 * 如果小于等于0或者为null 抛异常
 */
fun Long?.requireNotNullOrPositive(errorMessage: String = MsgConstants.PARAM_ERROR) {
    require(!(this == null || this <= 0L)) { errorMessage }
}