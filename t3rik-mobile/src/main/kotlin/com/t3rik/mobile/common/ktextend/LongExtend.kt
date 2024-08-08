package com.t3rik.mobile.common.ktextend

import com.t3rik.common.exception.BusinessException


/**
 * Long类型扩展
 * @author t3rik
 * @date 2024/8/8 13:49
 */

/**
 * 判断一个长整形数是否小于等于0
 * 如果小于等于0 抛异常
 */
fun Long.isNonPositive(errorMessage: () -> String){
    if(this <= 0L){
        throw BusinessException(errorMessage())
    }
}