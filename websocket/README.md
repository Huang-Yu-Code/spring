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