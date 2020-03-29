package com.hong.order.exception;

/**
 * @author wanghong
 * @date 2020/03/29 17:19
 **/
public class CodeException extends Exception {
    private static final long serialVersionUID = 7724252889850188629L;
    private final String code;

    public CodeException(String code) {
        this.code = code;
    }

    public CodeException(String code, String message) {
        super(message);
        this.code = code;
    }

    public CodeException(String code, Throwable cause) {
        super(cause);
        this.code = code;
    }

    public CodeException(String errorCode, String message, Throwable cause) {
        super(message, cause);
        this.code = errorCode;
    }

    public String getCode() {
        return this.code;
    }
}

