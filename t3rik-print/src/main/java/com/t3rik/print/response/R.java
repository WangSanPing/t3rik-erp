package com.t3rik.print.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class R<T> implements Serializable {
    /**
     * 成功失败标识
     */
    private boolean flag;

    /**
     * 响应数据
     */
    private T data;
    /**
     * 状态码
     */
    private Integer code;
    /**
     * 响应消息
     */
    private String message;

    public static Integer SUCCESS_200 = 200;
    public static Integer FAIL_500 = 500;

    public static <T> R<T> success() {
        return R.success(null);
    }

    public static <T> R<T> success(T result) {
        R<T> systemResult = new R<>();
        systemResult.setFlag(true);
        systemResult.setData(result);
        systemResult.setMessage("成功");
        systemResult.setCode(SUCCESS_200);
        return systemResult;
    }

    public static <T> R<T> success(String msg) {
        R<T> systemResult = new R<>();
        systemResult.setFlag(true);
        systemResult.setMessage(msg);
        return systemResult;
    }

    public static <T> R<T> fail(T result) {
        R<T> systemResult = new R<>();
        systemResult.setFlag(false);
        systemResult.setCode(FAIL_500);
        systemResult.setData(result);
        return systemResult;
    }

    public static <T> R<T> fail(String msg) {
        R<T> systemResult = new R<>();
        systemResult.setFlag(false);
        systemResult.setCode(FAIL_500);
        systemResult.setMessage(msg);
        return systemResult;
    }

    public static <T> R<T> fail(T result, String msg) {
        R<T> systemResult = new R<>();
        systemResult.setFlag(false);
        systemResult.setCode(FAIL_500);
        systemResult.setMessage(msg);
        systemResult.setData(result);
        return systemResult;
    }
}
