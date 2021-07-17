package com.demo.spring.mvc.controller;

import com.demo.spring.mvc.model.Json;
import com.demo.spring.mvc.service.WebMvcService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @author codingob
 */
@Controller
@RequestMapping("/")
public class WebMvcController {
    @Resource
    private WebMvcService webMvcService;

    @GetMapping("")
    public String index(Model model) {
        model.addAttribute("location", "首页");
        return "index";
    }

    @GetMapping("login")
    public String login(Model model) {
        model.addAttribute("location", "登录认证");
        return "login";
    }

    @PostMapping("login")
    public String login(Model model,
                        HttpServletRequest request,
                        @RequestParam("username") String username,
                        @RequestParam("password") String password) {
        boolean login = webMvcService.login(request, username, password);
        if (login) {
            return home(model);
        }
        model.addAttribute("location", "登录失败");
        model.addAttribute("info", "账号或密码错误");
        return "login";
    }

    @GetMapping("home")
    public String home(Model model) {
        model.addAttribute("location", "用户首页");
        return "home";
    }

    @GetMapping("json")
    @ResponseBody
    public Json json() {
        return new Json();
    }

    @GetMapping("mail")
    public String mail() {
        return "mail";
    }

    @PostMapping("logout")
    public String logout(HttpServletRequest request){
        webMvcService.logout(request);
        return "login";
    }
}
