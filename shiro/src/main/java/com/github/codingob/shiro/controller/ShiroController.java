package com.github.codingob.shiro.controller;

import com.github.codingob.shiro.dto.Login;
import com.github.codingob.shiro.dto.Logon;
import com.github.codingob.shiro.dto.Response;
import com.github.codingob.shiro.service.AuthService;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authz.annotation.RequiresAuthentication;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller
 *
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@RestController
public class ShiroController {
    private AuthService authService;

    @Autowired
    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    /**
     * 注册
     * @param logon 注册表单DTO
     * @return 注册结果
     */
    @PostMapping("/logon")
    public Response logon(@RequestBody Logon logon) {
        System.out.println(logon);
        Response response = new Response();
        try {
            authService.logon(logon);
            response.setStatus(true);
        } catch (RuntimeException e) {
            response.setInfo(e.getMessage());
        }
        return response;
    }

    /**
     * 登录
     * @param login 登录表单DTO
     * @return 登录结果
     */
    @PostMapping("/login")
    public Response login(@RequestBody Login login) {
        System.out.println(login);
        Response response = new Response();
        try {
            authService.login(login);
            response.setStatus(true);
        } catch (UnknownAccountException e) {
            response.setInfo("账号或密码错误");
        } catch (IncorrectCredentialsException e) {
            authService.lock(login.getUsername());
            response.setInfo("账号或密码错误");
        } catch (LockedAccountException e) {
            response.setInfo("账号状态异常");
        } catch (RuntimeException e) {
            response.setInfo(e.getMessage());
        }
        return response;
    }

    /**
     * 注销
     * @return 注销结果
     */
    @RequiresRoles({"user"})
    @GetMapping("/logout")
    public Response login(){
        authService.logout();
        return new Response(true);
    }

    /**
     * 验证码
     * @param timestamp 时间戳
     * @return 验证码数组
     */
    @GetMapping("/code/{timestamp}")
    public ResponseEntity<byte[]> code(@PathVariable long timestamp) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "image/jpeg");
        return ResponseEntity.ok().headers(headers).body(authService.getCode(timestamp));
    }

    /**
     * 查询身份
     * @param name 身份名
     * @return 是否具有该身份
     */
    @RequiresAuthentication
    @GetMapping("/roles/{name}")
    public Response hasRole(@PathVariable String name) {
        return new Response(authService.hasRole(name));
    }

    /**
     * 查询权限
     * @param name 权限名
     * @return 是否具有该权限
     */
    @RequiresAuthentication
    @GetMapping("/permissions/{name}")
    public Response hasPermission(@PathVariable String name) {
        return new Response(authService.hasPermission(name));
    }
}
