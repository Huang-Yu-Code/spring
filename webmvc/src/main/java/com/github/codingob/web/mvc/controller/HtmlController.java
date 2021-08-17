package com.github.codingob.web.mvc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Html
 *
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@Controller
public class HtmlController {
    @GetMapping("/")
    public String index() {
        return "index";
    }
}
