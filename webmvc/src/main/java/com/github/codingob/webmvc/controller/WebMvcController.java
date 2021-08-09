package com.github.codingob.webmvc.controller;

import com.github.codingob.webmvc.model.Login;
import com.github.codingob.webmvc.model.Response;
import com.github.codingob.webmvc.service.WebMvcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpServletRequest;

/**
 * Controller
 *
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@Controller
public class WebMvcController {
    private WebMvcService service;

    @Autowired
    public void setService(WebMvcService service) {
        this.service = service;
    }

    /**
     * 首页
     *
     * @return 视图
     */
    @GetMapping("/")
    public String index() {
        return "index";
    }

    /**
     * 错误视图
     *
     * @return 视图
     */
    @RequestMapping("/error")
    public String error() {
        return "error";
    }

    /**
     * 登录视图
     *
     * @param request 请求
     * @return 登录视图
     */
    @GetMapping("/login")
    public String login(HttpServletRequest request) {
        String username = (String) request.getSession().getAttribute("username");
        if (username != null) {
            return "home";
        }
        return "login";
    }

    /**
     * 验证码
     *
     * @param request   请求
     * @param timestamp 时间戳
     * @return 验证码数据流
     */
    @GetMapping("/code/{timestamp}")
    @ResponseBody
    public ResponseEntity<byte[]> code(HttpServletRequest request, @PathVariable long timestamp) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "image/jpeg");
        return new ResponseEntity<>(service.code(request.getSession(), timestamp), headers, HttpStatus.OK);
    }

    /**
     * 登录验证
     *
     * @param login   DTO
     * @param request 请求
     * @return 登录状态
     */
    @PostMapping("/login")
    @ResponseBody
    public ResponseEntity<Response> login(@RequestBody Login login, HttpServletRequest request) {
        return new ResponseEntity<>(service.login(login, request.getSession()), HttpStatus.OK);
    }

    /**
     * 登录后的主页
     *
     * @return 主页
     */
    @GetMapping("/home")
    public String home() {
        return "home";
    }

    /**
     * 文件上传
     *
     * @param file 文件
     * @return Void
     */
    @PostMapping("/home/upload")
    @ResponseBody
    public ResponseEntity<Void> upload(@RequestParam("file") CommonsMultipartFile file) {
        service.upload(file, "D:/");
        return new ResponseEntity<>(HttpStatus.OK);
    }

    /**
     * 文件下载
     *
     * @return 文件流
     */
    @GetMapping("/home/download")
    @ResponseBody
    public ResponseEntity<byte[]> download() {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=springmvc.png");
        return new ResponseEntity<>(service.download("static/springmvc.png"), headers, HttpStatus.OK);
    }

    /**
     * 退出登录
     *
     * @param request 请求
     * @return 登录视图
     */
    @PostMapping("/home/logout")
    @ResponseBody
    public ResponseEntity<Response> logout(HttpServletRequest request) {
        service.logout(request.getSession());
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
