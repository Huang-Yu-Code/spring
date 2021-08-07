if (typeof (WebSocket) != "function") {
    alert("不支持WebSocket");
}
let websocket = null;
let app = new Vue({
    el: '#app',
    data() {
        return {
            disabled: false,
        };
    },
    methods: {
        connect() {
            let socket = new SockJS("http://localhost:8080/websocket")
            websocket = Stomp.over(socket);
            websocket.connect({}, () => {
                app.disabled = true;
                websocket.subscribe('/topic/spring', function (message) {
                    console.log(message);
                    app.$notify.info({
                        title: '系统通知: ',
                        message: message.body,
                    });
                });
            });
        },
        disconnect() {
            if (websocket != null) {
                websocket.disconnect();
                app.disabled = false;
            }
        },
        sendMessage() {
            websocket.send("/app/hello", {}, JSON.stringify({
                username: 'websocket',
                message: 'Hello World',
            }))
        }
    }
})