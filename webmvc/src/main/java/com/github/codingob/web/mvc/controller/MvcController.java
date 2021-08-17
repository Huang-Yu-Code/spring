package com.github.codingob.web.mvc.controller;

import com.github.codingob.web.mvc.dto.Login;
import com.github.codingob.web.mvc.dto.Logon;
import com.github.codingob.web.mvc.dto.Response;
import com.github.codingob.web.mvc.service.MvcService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;

/**
 * Controller
 *
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@RestController
public class MvcController {

    private MvcService mvcService;

    @Autowired
    public void setService(MvcService mvcService) {
        this.mvcService = mvcService;
    }

    /**
     * 验证码
     *
     * @param timestamp 时间戳
     * @return 验证码数据流
     */
    @GetMapping("/code/{timestamp}")
    public ResponseEntity<byte[]> code(@PathVariable long timestamp) {
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "image/jpeg");
        return ResponseEntity.ok().headers(headers).body(mvcService.getCode(timestamp));
    }

    /**
     * 注册
     *
     * @param logon 注册表单DTO
     * @return 注册结果
     */
    @PostMapping("/logon")
    public Response logon(@RequestBody Logon logon) {
        try {
            mvcService.logon(logon);
            return new Response(true);
        } catch (RuntimeException e) {
            return new Response(e.getMessage());
        }
    }

    /**
     * 登录
     *
     * @param login DTO
     * @return 登录状态
     */
    @PostMapping("/login")
    public Response login(@RequestBody Login login) {
        try {
            mvcService.login(login);
            return new Response(true);
        } catch (RuntimeException exception) {
            return new Response(exception.getMessage());
        }
    }

    /**
     * 注销
     *
     * @return 注销结果
     */
    @PostMapping("/home/logout")
    public ResponseEntity<Response> logout() {
        mvcService.logout();
        return ResponseEntity.ok().build();
    }

    /**
     * 文件上传
     *
     * @param files 文件列表
     * @return Void
     */
    @PostMapping("/home/upload")
    public Response upload(@RequestParam("file") CommonsMultipartFile[] files) {
        try {
            mvcService.upload(files, "D:/");
            return new Response(true);
        } catch (Exception e) {
            return new Response(e.getMessage());
        }
    }

    /**
     * 文件下载
     *
     * @return 文件流
     */
    @GetMapping("/home/download")
    public ResponseEntity<Object> download() {
        String file = "file.txt";
        String path = "D:/";
        try {
            byte[] bytes = mvcService.download(new File(path + file));
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, "application/octet-stream");
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment;filename=" + file);
            return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
        }
        catch (Exception e) {
            return new ResponseEntity<>(new Response("上传失败"), new HttpHeaders(), HttpStatus.BAD_REQUEST);
        }
    }

}
