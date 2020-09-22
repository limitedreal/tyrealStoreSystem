package com.tyreal.myTest.exception.http;

public class HttpException extends RuntimeException {
    protected Integer code;
    protected Integer httpStatusCode;

    public Integer getCode() {
        return code;
    }

    public Integer getHttpStatusCode() {
        return httpStatusCode;
    }
}
