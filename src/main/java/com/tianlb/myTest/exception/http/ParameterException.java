package com.tianlb.myTest.exception.http;

public class ParameterException extends HttpException{
    public ParameterException(int code) {
        this.code=code;
        this.httpStatusCode = 400;
        //权限不足
    }
}
