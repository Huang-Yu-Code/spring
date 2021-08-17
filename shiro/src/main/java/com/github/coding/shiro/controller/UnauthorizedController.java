package com.github.coding.shiro.controller;

import com.github.coding.shiro.dto.Response;
import org.apache.shiro.authz.AuthorizationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 无访问权限
 *
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@ControllerAdvice
public class UnauthorizedController {
    @ResponseBody
    @ExceptionHandler(AuthorizationException.class)
    public Response authorizationException() {
        return new Response("无访问权限");
    }
}
