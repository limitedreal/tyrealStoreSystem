package com.tyreal.myTest.core;

import com.tyreal.myTest.core.configuration.ExceptionCodeConfiguration;
import com.tyreal.myTest.exception.http.HttpException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import java.util.List;

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
    public ResponseEntity<UnifyResponse> handleGttpException(HttpServletRequest req, HttpException e) {
        System.out.println("http异常");
        String requestUrl = req.getRequestURI();
        String method = req.getMethod();
        UnifyResponse message = new UnifyResponse(e.getCode(), exceptionCodeConfiguration.getMessage(e.getCode()), method + " | " + requestUrl);
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        //这里是需要动态设置ResponseStatus
        HttpStatus httpStatus = HttpStatus.resolve(e.getHttpStatusCode());
        return new ResponseEntity<>(message, headers, httpStatus);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    // 400
    @ResponseBody
    public UnifyResponse handleBeanValidationException(HttpServletRequest req, MethodArgumentNotValidException e) {
        String requestUrl = req.getRequestURI();
        String method = req.getMethod();
        List<ObjectError> errors = e.getBindingResult().getAllErrors();
        String message = formatAllErrorMessage(errors);
        return new UnifyResponse(10001, message, method + " | " + requestUrl);
    }

    private String formatAllErrorMessage(List<ObjectError> errors) {
        StringBuffer errorMsg = new StringBuffer();
        errors.forEach(error -> errorMsg.append(error.getDefaultMessage()).append("。"));
        return errorMsg.toString();
    }

    //ConstraintViolationException
    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    @ResponseBody
    public UnifyResponse handleConstraintException(HttpServletRequest req, ConstraintViolationException e) {
        String requestUrl = req.getRequestURI();
        String method = req.getMethod();
        StringBuilder messageStr = new StringBuilder();

        //e本身就有getMessage方法，但是此方法不适合订制，所以还是循环拼接更好
        for (ConstraintViolation error : e.getConstraintViolations()) {
            messageStr.append(error.getMessage()).append("。");
        }
        String message = messageStr.toString();
        return new UnifyResponse(10001, message, method + " | " + requestUrl);
    }

    //指定处理哪种异常，而且不会接受到上面已经被捕获过的异常
    //处理未知异常
    @ResponseBody
    @ExceptionHandler(value = Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public UnifyResponse handleException(HttpServletRequest req, Exception e) {
        System.out.println(e.getMessage());
        String requestUrl = req.getRequestURI();
        String method = req.getMethod();
        return new UnifyResponse(9999, "服务器系统异常", method + " | " + requestUrl);
    }
}
