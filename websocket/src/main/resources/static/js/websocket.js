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
            if (typeof (WebSocket) != "function") {
                alert("不支持WebSocket");
            }
            let socket = new SockJS("http://localhost:8080/websocket")
            websocket = Stomp.over(socket);
            console.log('ok');
            websocket.connect({}, () => {
                this.disabled = true;
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
                this.disabled = false;
            }
        },
        sendMessage() {
            websocket.send("/app/spring", {}, JSON.stringify({
                username: 'websocket',
                message: 'Hello World',
            }))
        }
    }
})