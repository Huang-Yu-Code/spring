package com.github.codingob.shiro.service;

import com.github.codingob.shiro.dto.Login;
import com.github.codingob.shiro.dto.Logon;
import com.github.codingob.shiro.util.CodeUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 注册/登录/注销
 *
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@Service
public class AuthService {
    public final static Map<String, String> ACCOUNTS = new ConcurrentHashMap<>();
    private final static int ERROR_NUM = 4;
    public final static Map<String, AtomicInteger> ACCOUNTS_LOCK = new ConcurrentHashMap<>();
    public final static Set<String> ROLES = new CopyOnWriteArraySet<>();
    public final static Set<String> PERMISSIONS = new CopyOnWriteArraySet<>();

    static {
        ROLES.add("user");
        PERMISSIONS.add("user:create");
        PERMISSIONS.add("user:read");
        PERMISSIONS.add("user:update");
        PERMISSIONS.add("user:delete");
    }

    public byte[] getCode(long timestamp) {
        String codeString = CodeUtils.createCodeString(4);
        Subject subject = SecurityUtils.getSubject();
        subject.getSession().setAttribute("code", codeString);
        subject.getSession().setAttribute("timestamp", timestamp);
        return CodeUtils.createCodeImage(100, 40, codeString);
    }

    public void checkCode(String codeString) {
        Subject subject = SecurityUtils.getSubject();
        String code = (String) subject.getSession().getAttribute("code");
        if (codeString == null) {
            throw new RuntimeException("验证码不能为空");
        }
        if (!codeString.equals(code)) {
            throw new RuntimeException("验证码不正确");
        }
    }

    public void logon(Logon logon) {
        checkCode(logon.getCode());
        if (logon.getUsername() == null) {
            throw new RuntimeException("账号不能为空");
        }
        if (ACCOUNTS.containsKey(logon.getUsername())) {
            throw new RuntimeException("账号已注册");
        }
        if (logon.getPassword() == null || logon.getRePassword() == null) {
            throw new RuntimeException("密码不能为空");
        }
        if (!logon.getPassword().equals(logon.getRePassword())) {
            throw new RuntimeException("两次密码不一致");
        }
        ACCOUNTS.put(logon.getUsername(), new Md5Hash(logon.getPassword(), "XXX", 1024).toHex());
        Subject subject = SecurityUtils.getSubject();
        subject.getSession().removeAttribute("code");
        subject.getSession().removeAttribute("timestamp");
    }

    public void login(Login login) {
        checkCode(login.getCode());
        if (login.getUsername() == null) {
            throw new AuthenticationException("账号不能为空");
        }
        if (ACCOUNTS_LOCK.containsKey(login.getUsername()) && ACCOUNTS_LOCK.get(login.getUsername()).get() > ERROR_NUM) {
            throw new LockedAccountException("账号状态异常");
        }
        if (login.getPassword() == null) {
            throw new AuthenticationException("密码不能为空");
        }
        try {
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(login.getUsername(), login.getPassword());
            subject.login(token);
            unLock(login.getUsername());
            subject.getSession().removeAttribute("code");
            subject.getSession().removeAttribute("timestamp");
        } catch (IncorrectCredentialsException e) {
            throw new IncorrectCredentialsException();
        }
    }

    public void logout() {
        SecurityUtils.getSubject().logout();
    }

    public void lock(String username) {
        if (ACCOUNTS_LOCK.containsKey(username)) {
            ACCOUNTS_LOCK.get(username).incrementAndGet();
        } else {
            ACCOUNTS_LOCK.put(username, new AtomicInteger());
        }
    }

    public void unLock(String username) {
        ACCOUNTS_LOCK.remove(username);
    }

    public boolean hasRole(String role) {
        return SecurityUtils.getSubject().hasRole(role);
    }

    public boolean hasPermission(String permission) {
        return SecurityUtils.getSubject().isPermitted(permission);
    }

}
