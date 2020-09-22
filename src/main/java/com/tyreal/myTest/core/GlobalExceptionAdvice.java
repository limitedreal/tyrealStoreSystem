package com.tyreal.myTest.core;

import com.tyreal.myTest.core.configuration.ExceptionCodeConfiguration;
import com.tyreal.myTest.exception.http.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;

/**
 * @author machenike
 * 此核心类用于全局处理错误
 */
@ControllerAdvice
public class GlobalExceptionAdvice {

    @Autowired
    private ExceptionCodeConfiguration exceptionCodeConfiguration;

    @ResponseBody
    @ExceptionHandler(value = HttpException.class)
    public ResponseEntity<UnifyResponse> handleGttpException(HttpServletRequest req,HttpException e){
        System.out.println("http异常");
        String requestUrl = req.getRequestURI();
        String method = req.getMethod();
        UnifyResponse message = new UnifyResponse(e.getCode(),exceptionCodeConfiguration.getMessage(e.getCode()),method+" | "+requestUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpStatus httpStatus = HttpStatus.resolve(e.getHttpStatusCode());
        ResponseEntity<UnifyResponse> re = new ResponseEntity<>(message, headers, httpStatus);
        return re;
    }

    //指定处理哪种异常，而且不会接受到上面已经被捕获过的异常
    //处理未知异常
    @ResponseBody
    @ExceptionHandler(value=Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public UnifyResponse handleException(HttpServletRequest req,Exception e){
        System.out.println(e);
        String requestUrl = req.getRequestURI();
        String method = req.getMethod();
        UnifyResponse message = new UnifyResponse(9999,"服务器系统异常",method+" | "+requestUrl);
        return message;
    }
}
