package com.github.codingob.webmvc.service;

import com.github.codingob.webmvc.dao.UserDao;
import com.github.codingob.webmvc.model.Login;
import com.github.codingob.webmvc.model.Response;
import com.github.codingob.webmvc.model.User;
import com.github.codingob.webmvc.util.CodeUtils;
import com.github.codingob.webmvc.util.DownloadUtils;
import com.github.codingob.webmvc.util.UploadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import javax.servlet.http.HttpSession;

/**
 * Service
 *
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@Service
public class WebMvcService {
    private UserDao userDao;

    @Autowired
    public void setUserDao(UserDao userDao) {
        this.userDao = userDao;
    }

    /**
     * 登录验证
     *
     * @param login   DTO
     * @param session session
     * @return 登录结果
     */
    public Response login(Login login, HttpSession session) {
        System.out.println(login);
        String code = (String) session.getAttribute("code");
        code = code.toLowerCase();
        User user = login.getUser();
        Response response = new Response();
        if (!StringUtils.hasLength(login.getCode())) {
            response.setInfo("验证码不能为空!");
            return response;
        }
        if (!code.equals(login.getCode().toLowerCase())) {
            response.setInfo("验证码错误!");
            return response;
        }
        if (!StringUtils.hasLength(user.getUsername())) {
            response.setInfo("用户名不能为空!");
            return response;
        }
        if (!StringUtils.hasLength(user.getPassword())) {
            response.setInfo("密码不能为空!");
            return response;
        }

        if (!user.getUsername().equals(userDao.getUser().getUsername()) || !user.getPassword().equals(userDao.getUser().getUsername())) {
            response.setInfo("账号或密码错误!");
            return response;
        }
        response.setStatus(true);
        response.setInfo("登录成功!");
        session.setAttribute("username", user.getUsername());
        session.removeAttribute("timestamp");
        session.removeAttribute("code");
        return response;
    }

    /**
     * 获取验证码
     *
     * @param session   session
     * @param timestamp 时间戳
     * @return 验证码图片字节数组
     */
    public byte[] code(HttpSession session, long timestamp) {
        long time = System.currentTimeMillis() - timestamp;
        if (time > 0 && time < CodeUtils.TIMEOUT) {
            String code = CodeUtils.getCode(4);
            session.setAttribute("timestamp", timestamp);
            session.setAttribute("code", code);
            return CodeUtils.getImage(200, 30, code);
        }
        return null;
    }

    /**
     * 退出登录(清空session)
     *
     * @param session session
     */
    public void logout(HttpSession session) {
        session.invalidate();
    }

    /**
     * 文件上传
     *
     * @param file 文件
     * @param path 保存路径
     */
    public void upload(CommonsMultipartFile file, String path) {
        UploadUtils.save(file, path);
    }

    /**
     * 文件下载
     *
     * @param file 文件名
     * @return 文件字节数组
     */
    public byte[] download(String file) {
        return DownloadUtils.downloadForClassResource(file);
    }
}
