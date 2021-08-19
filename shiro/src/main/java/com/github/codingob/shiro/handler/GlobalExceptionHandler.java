package com.github.codingob.shiro.handler;

import com.github.codingob.shiro.dto.Response;
import org.apache.shiro.authz.UnauthenticatedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理
 *
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UnauthenticatedException.class)
    public Response authorizationException() {
        return new Response("无访问权限");
    }
}
