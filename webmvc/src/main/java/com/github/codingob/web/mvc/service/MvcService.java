package com.github.codingob.web.mvc.service;

import com.github.codingob.web.mvc.dto.Login;
import com.github.codingob.web.mvc.dto.Logon;
import com.github.codingob.web.mvc.util.CodeUtils;
import com.github.codingob.web.mvc.util.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpSession;
import java.io.File;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Service
 *
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@Service
public class MvcService {

    private final static Map<String, String> ACCOUNTS = new ConcurrentHashMap<>();
    private final static int ERROR_NUM = 4;
    private final static Map<String, AtomicInteger> ACCOUNTS_LOCK = new ConcurrentHashMap<>();

    private HttpSession httpSession;

    @Autowired
    public void setHttpSession(HttpSession httpSession) {
        this.httpSession = httpSession;
    }

    /**
     * 验证码匹配
     * @param codeString 验证码
     */
    public void checkCode(String codeString) {
        String sessionCode = (String) httpSession.getAttribute("code");
        if (codeString == null) {
            throw new RuntimeException("验证码不能为空");
        }

        if (!codeString.equals(sessionCode)) {
            throw new RuntimeException("验证码不正确");
        }
    }

    /**
     * 注册
     * @param logon 注册表单DTO
     */
    public void logon(Logon logon) {
        checkCode(logon.getCode());
        String username = logon.getUsername();
        String password = logon.getPassword();
        if (username == null) {
            throw new RuntimeException("账号不能为空");
        }
        if (ACCOUNTS.containsKey(username)) {
            throw new RuntimeException("账号已注册");
        }
        if (password == null || logon.getRePassword() == null) {
            throw new RuntimeException("密码不能为空");
        }
        if (!password.equals(logon.getRePassword())) {
            throw new RuntimeException("两次密码不一致");
        }
        ACCOUNTS.put(logon.getUsername(), password);
    }

    /**
     * 登录
     *
     * @param login 登录表单DTO
     */
    public void login(Login login) {
        checkCode(login.getCode());
        String username = login.getUsername();
        String password = login.getPassword();
        if (username == null) {
            throw new RuntimeException("用户名不能为空!");
        }

        if (password == null) {
            throw new RuntimeException("密码不能为空!");
        }
        if (ACCOUNTS_LOCK.containsKey(username) && ACCOUNTS_LOCK.get(username).get() > ERROR_NUM) {
            throw new RuntimeException("账号状态异常");
        }
        if (!ACCOUNTS.containsKey(username) || !password.equals(ACCOUNTS.get(username))) {
            if (!ACCOUNTS_LOCK.containsKey(username)) {
                ACCOUNTS_LOCK.put(username, new AtomicInteger());
            }
            ACCOUNTS_LOCK.get(username).incrementAndGet();
            throw new RuntimeException("账号或密码错误!");
        }
        ACCOUNTS.put(username, password);
        ACCOUNTS_LOCK.remove(username);
        httpSession.setAttribute("username", username);
    }

    /**
     * 生成验证码
     *
     * @param timestamp 时间戳
     * @return 验证码图片字节数组
     */
    public byte[] getCode(long timestamp) {
        String code = CodeUtils.createCodeString(4);
        httpSession.setAttribute("timestamp", timestamp);
        httpSession.setAttribute("code", code);
        return CodeUtils.createCodeImage(100, 40, code);

    }

    /**
     * 注销(清空session)
     */
    public void logout() {
        httpSession.invalidate();
    }

    /**
     * 文件上传
     *
     * @param files 文件列表
     * @param path  保存路径
     */
    public void upload(CommonsMultipartFile[] files, String path) throws IOException {
        FileUtils.upload(files, path);
    }

    /**
     * 文件下载
     *
     * @param file 文件
     * @return 文件字节数组
     */
    public byte[] download(File file) throws IOException {
        return FileUtils.download(file);
    }
}
