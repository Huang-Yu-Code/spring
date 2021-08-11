package com.github.codingob.websocket.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controller
 *
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
@Controller
public class WebSocketController {
    @GetMapping("/")
    public String index(){
        return "index";
    }

    @MessageMapping("/spring")
    @SendTo("/topic/spring")
    public String response(String message) {
        System.out.println(message);
        return "Hello World";
    }

}
