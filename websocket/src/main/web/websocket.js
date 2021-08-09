if (typeof (WebSocket) != "function") {
    alert("不支持WebSocket");
}
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
            let socket = new SockJS("/websocket", null, {timeout: 15000})
            app.websocket = Stomp.over(socket);
            app.websocket.connect({}, () => {
                app.disabled = true;
                app.websocket.subscribe('/topic/spring', function (message) {
                    console.log(message);
                    app.$notify.info({
                        title: '系统通知: ',
                        message: message.body,
                    });
                });
            });
        },
        disconnect() {
            if (app.websocket != null) {
                app.websocket.disconnect();
                app.disabled = false;
            }
        },
        sendMessage() {
            app.websocket.send("/app/spring", {}, JSON.stringify({
                username: 'websocket',
                message: 'Hello World',
            }))
        }
    }
})