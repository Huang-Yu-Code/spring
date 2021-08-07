package com.github.codingob.websocket.handler;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

/**
 * @author codingob
 * @version 1.0.0
 * @since JDK1.8
 */
public class WebSocketHandler extends TextWebSocketHandler {
    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

        String payload = message.getPayload();
        System.out.println("客户端发送的数据：" + payload);

        String response = "服务端发送的数据";

        session.sendMessage(new TextMessage(response));

        super.handleTextMessage(session, message);
    }
}
