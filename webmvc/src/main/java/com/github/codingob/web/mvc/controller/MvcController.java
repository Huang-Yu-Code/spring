package com.github.codingob.web.mvc.controller;

import com.github.codingob.web.mvc.dto.Login;
import com.github.codingob.web.mvc.dto.Logon;
import com.github.codingob.web.mvc.dto.Response;
import com.github.codingob.web.mvc.service.MvcService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.logging.Logger;

/**
 * Controller
 *
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@RestController
public class MvcController {
    private static final Logger logger = Logger.getLogger("MvcController.class");

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
        logger.info("获取验证码");
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
            return Response.success();
        } catch (RuntimeException e) {
            return Response.fair(e.getMessage());
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
            return Response.success();
        } catch (RuntimeException e) {
            return Response.fair(e.getMessage());
        }
    }

    /**
     * 注销
     *
     * @return 注销结果
     */
    @PostMapping("/home/logout")
    public Response logout() {
        mvcService.logout();
        return Response.success();
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
            return Response.success();
        } catch (Exception e) {
            return Response.fair(e.getMessage());
        }
    }

    /**
     * 文件下载
     *
     * @return 文件流
     */
    @GetMapping("/home/download/{fileName}")
    public ResponseEntity<Object> download(@PathVariable String fileName) {
        String path = "D:/";
        File file = new File(path + fileName);
        try {
            HttpHeaders headers = new HttpHeaders();
            byte[] bytes = FileUtils.readFileToByteArray(file);
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDisposition(ContentDisposition.attachment().filename(fileName, StandardCharsets.UTF_8).build());
            return ResponseEntity.ok().headers(headers).body(bytes);
        } catch (IOException e) {
            return ResponseEntity.ok().body("资源不存在");
        }
    }

}
