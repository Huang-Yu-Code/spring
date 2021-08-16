package com.github.coding.shiro.controller;

import com.github.coding.shiro.dto.Login;
import com.github.coding.shiro.dto.Logon;
import com.github.coding.shiro.dto.Response;
import com.github.coding.shiro.service.AuthService;
import org.apache.shiro.authc.AuthenticationException;
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

    @RequiresRoles({"user"})
    @GetMapping("/logout")
    public Response login(){
        authService.logout();
        return new Response(true);
    }

    @GetMapping("/code/{timestamp}")
    public ResponseEntity<byte[]> code(@PathVariable long timestamp) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "image/jpeg");
        return ResponseEntity.ok().headers(headers).body(authService.getCode(timestamp));
    }

    @RequiresAuthentication
    @GetMapping("/roles/{name}")
    public Response hasRole(@PathVariable String name) {
        return new Response(authService.hasRole(name));
    }

    @RequiresAuthentication
    @GetMapping("/permissions/{name}")
    public Response hasPermission(@PathVariable String name) {
        return new Response(authService.hasPermission(name));
    }
}
