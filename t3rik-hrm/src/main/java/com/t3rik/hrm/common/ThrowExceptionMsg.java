package com.t3rik.hrm.common;

/**
 * 抛异常信息
 *
 * @author t3rik
 * @date 2024/9/12 21:25
 */

@FunctionalInterface
public interface ThrowExceptionMsg {
    void throwMsg(String msg);
}
