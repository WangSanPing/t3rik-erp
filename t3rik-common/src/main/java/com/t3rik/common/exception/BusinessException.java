package com.t3rik.common.exception;

import com.t3rik.common.constant.HttpStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class BusinessException extends RuntimeException {
    private static final long serialVersionUID = -1L;

    /**
     * 错误码
     */
    private Integer code;

    /**
     * 错误提示
     */
    private String message;

    /**
     * 错误明细，内部调试错误
     * <p>
     * 和 {@link CommonResult#getDetailMessage()} 一致的设计
     */
    private String detailMessage;

    public BusinessException() {
        super();
        this.code = HttpStatus.ERROR;
    }


    public BusinessException(String msg) {
        super(msg);
        this.code = HttpStatus.ERROR;
        this.message = msg;
    }

    public BusinessException(String msg, Integer code) {
        super(msg);
        this.code = code;
        this.message = msg;
    }

    public BusinessException(String msg, Integer code, String detailMessage) {
        super(msg);
        this.code = code;
        this.message = msg;
        this.detailMessage = detailMessage;
    }

    public BusinessException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public BusinessException(Throwable cause) {
        super(cause);
    }

}
