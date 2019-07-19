package com.example.li.springboot_shiro_jwt_demo.controller;

import com.example.li.springboot_shiro_jwt_demo.bean.ResponseBean;
import com.example.li.springboot_shiro_jwt_demo.exception.UnauthorizedException;
import org.apache.shiro.ShiroException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * 描述：全局异常处理
 * @author LJH
 * @date 2019/7/18-22:59
 * @QQ 1755497577
 */
@RestControllerAdvice
public class ExceptionController {

    // 捕捉shiro的异常
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(ShiroException.class)
    public ResponseBean handle401(ShiroException e) {
        return new ResponseBean(401, e.getMessage(), null);
    }

    // 捕捉UnauthorizedException
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedException.class)
    public ResponseBean handle401() {
        return new ResponseBean(401, "Unauthorized", null);
    }

    // 捕捉其他所有异常
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseBean globalException(HttpServletRequest request, Throwable ex) {
        return new ResponseBean(getStatus(request).value(), ex.getMessage(), null);
    }

    private HttpStatus getStatus(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        if (statusCode == null) {
            return HttpStatus.INTERNAL_SERVER_ERROR;
        }
        return HttpStatus.valueOf(statusCode);
    }
}

