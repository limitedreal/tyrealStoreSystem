package com.tianlb.myTest.exception.http;

public class ForbiddenException extends HttpException{
    public ForbiddenException(int code) {
        this.code=code;
        this.httpStatusCode = 403;
        //权限不足
    }
}
