package com.tianlb.myTest.exception.http;

public class NotFoundException extends HttpException{
    public NotFoundException(int code){
        this.httpStatusCode = 404;
        //资源未找到
        this.code=code;
    }
}
