package com.t3rik.exception;

/**
 * oss异常
 *
 * @author t3rik
 * @date 2025/2/11 17:12
 */
public class T3rikOssException extends RuntimeException {

    public T3rikOssException() {
    }

    public T3rikOssException(String msg) {
        super(msg);
    }

    public T3rikOssException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
