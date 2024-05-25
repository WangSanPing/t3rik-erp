package com.t3rik.common.core.domain;

import lombok.Data;

/**
 * 用于参数\数据等是否正确的校验信息类
 *
 * @author t3rik
 * @date 2024/5/25 21:49
 */
@Data
public class CheckInfo {

    /**
     * 错误信息
     */
    private String msg;

    /**
     * 是否通过检查
     * true 通过
     * false 没通过
     */
    private Boolean isCheckPassed;
}
