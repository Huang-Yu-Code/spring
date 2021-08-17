# Spring WebSocket + SockJS + Stomp

依赖

```xml

<dependencies>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-websocket</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-messaging</artifactId>
    </dependency>
    <dependency>
        <groupId>javax.servlet</groupId>
        <artifactId>javax.servlet-api</artifactId>
    </dependency>
    <dependency>
        <groupId>javax.servlet.jsp</groupId>
        <artifactId>javax.servlet.jsp-api</artifactId>
    </dependency>
    <dependency>
        <groupId>org.springframework</groupId>
        <artifactId>spring-webmvc</artifactId>
    </dependency>
</dependencies>
```

配置
xml
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xmlns:websocket="http://www.springframework.org/schema/websocket"
       xsi:schemaLocation="http://www.springframework.org/schema/beans
       http://www.springframework.org/schema/beans/spring-beans.xsd
       http://www.springframework.org/schema/websocket
       http://www.springframework.org/schema/websocket/spring-websocket.xsd">

    <websocket:message-broker application-destination-prefix="/app" path-matcher="pathMatcher" preserve-publish-order="true">
        <websocket:transport send-timeout="15000" send-buffer-size="524288" />
        <websocket:stomp-endpoint path="/websocket">
            <websocket:sockjs/>
        </websocket:stomp-endpoint>
        <websocket:simple-broker prefix="/topic"/>
    </websocket:message-broker>

    <bean id="pathMatcher" class="org.springframework.util.AntPathMatcher">
        <constructor-arg index="0" value="."/>
    </bean>
</beans>
```

java

```java

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketStompConfig implements WebSocketMessageBrokerConfigurer {

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/websocket").withSockJS();
    }

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic");
        registry.setApplicationDestinationPrefixes("/app");
    }
}
```

Controller

```java

@Controller
public class WebSocketController {
    @GetMapping("/")
    public String index() {
        return "index";
    }

    @MessageMapping("/spring")
    @SendTo("/topic/spring")
    public String response(String message) {
        System.out.println(message);
        return "Hello World";
    }

}
```

前端Vue.js index.html

```html
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Spring WebSocket</title>
    <link rel="shortcut icon" type="image/x-icon" href="static/favicon.ico">
    <link rel="stylesheet" href="https://unpkg.com/element-ui/lib/theme-chalk/index.css">
    <script src="https://unpkg.com/vue/dist/vue.js"></script>
    <script src="https://unpkg.com/element-ui/lib/index.js"></script>
    <script src="https://cdn.bootcdn.net/ajax/libs/sockjs-client/1.5.1/sockjs.js"></script>
    <script src="https://cdn.bootcdn.net/ajax/libs/stomp.js/2.3.3/stomp.js"></script>
</head>
<body>
<div id="app">
    <el-button type="primary" @click="connect" :disabled="disabled">连接</el-button>
    <el-button type="success" @click="sendMessage" :disabled="!disabled">发送</el-button>
    <el-button type="danger" @click="disconnect" :disabled="!disabled">断开</el-button>
</div>
<script src="static/js/websocket.js"></script>
</body>
</html>
```

websocket.js

```js
let app = new Vue({
    el: '#app',
    data() {
        return {
            websocket: null,
            disabled: false,
        };
    },

    methods: {
        connect() {
            if (typeof (WebSocket) != "function") {
                alert("不支持WebSocket");
            }
            let socket = new SockJS("/websocket")
            this.websocket = Stomp.over(socket);
            console.log('ok');
            this.websocket.connect({}, () => {
                this.disabled = true;
                this.websocket.subscribe('/topic/spring', function (message) {
                    console.log(message);
                    app.$notify.info({
                        title: '系统通知: ',
                        message: message.body,
                    });
                });
            });
        },
        disconnect() {
            if (this.websocket != null) {
                this.websocket.disconnect();
                this.disabled = false;
            }
        },
        sendMessage() {
            this.websocket.send("/app/spring", {}, JSON.stringify({
                username: 'websocket',
                message: 'Hello World',
            }))
        }
    }
})
```