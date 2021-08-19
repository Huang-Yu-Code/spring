package com.github.codingob.test.controller;

import com.github.codingob.test.service.WebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller
 *
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@RestController
public class WebController {
    private WebService service;

    @Autowired
    public void setService(WebService service) {
        this.service = service;
    }

    public void xxxCreate() {
        service.xxxCreate();
    }

    public void xxxRead() {
        service.xxxRead();
    }

    public void xxxUpdate() {
        service.xxxUpdate();
    }

    public void xxxDelete() {
        service.xxxDelete();
    }
}
