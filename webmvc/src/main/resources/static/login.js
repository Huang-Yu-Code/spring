const App = new Vue({
    data() {
        return {
            codeURL: '/code/' + new Date().getTime(),
            form: {
                user: {
                    username: null,
                    password: null,
                },
                code: null,
            },
        };
    },
    methods: {
        goBack() {
            history.back();
        },
        flushCode() {
            this.codeURL = '/code/' + new Date().getTime();
        },
        submitForm() {
            axios({
                method: 'post',
                url: '/login',
                data: this.form,
            }).then((res => {
                if (res.data['status']) {
                    this.$notify.success({
                        title: '系统通知',
                        message: '登录成功',
                    });
                    location.reload();
                } else {
                    this.$notify.error({
                        title: '系统通知',
                        message: res.data['info'],
                    });
                    this.flushCode();
                    this.form.code = null;
                }
            })).catch((err) => {
                console.log(err)
            });
        },
        resetForm() {
            this.form = {
                user: {
                    username: null,
                    password: null,
                },
                code: null,
            };
        }
    }
}).$mount('#app')