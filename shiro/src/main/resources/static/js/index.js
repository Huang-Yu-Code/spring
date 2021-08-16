const app = new Vue({
    data() {
        let validatePassword = (rule, value, callback) => {
            if (value === '') {
                callback(new Error('请输入密码'));
            } else {
                if (value !== this.logonForm.password) {
                    callback(new Error('两次输入密码不一致!'));
                }
                callback();
            }
        }
        return {
            activeTab: 'logon',
            codeURL: 'code/' + new Date().getTime(),
            logonForm: {
                username: null,
                password: null,
                rePassword: null,
                code: null,
            },
            rulesLogon: {
                username: [
                    {required: true, message: '请输入账号', trigger: 'blur'},
                    {min: 5, max: 32, message: '长度在 5 到 32 个字符', trigger: 'blur'}
                ],
                password: [
                    {required: true, message: '请输入密码', trigger: 'blur'},
                    {min: 8, max: 32, message: '长度在 8 到 32 个字符', trigger: 'blur'}
                ],
                rePassword: [
                    {validator: validatePassword, trigger: 'blur'},
                    {required: true, message: '请输入重复密码', trigger: 'blur'},
                    {min: 6, max: 32, message: '长度在 6 到 32 个字符', trigger: 'blur'}
                ],
                code: [
                    {required: true, message: '请输入验证码', trigger: 'blur'},
                    {min: 4, max: 4, message: '长度 4 个字符', trigger: 'blur'}
                ],
            },
            rulesLogin: {
                username: [
                    {required: true, message: '请输入账号', trigger: 'blur'},
                    {min: 5, max: 32, message: '长度在 5 到 32 个字符', trigger: 'blur'}
                ],
                password: [
                    {required: true, message: '请输入密码', trigger: 'blur'},
                    {min: 8, max: 32, message: '长度在 8 到 32 个字符', trigger: 'blur'}
                ],
                code: [
                    {required: true, message: '请输入验证码', trigger: 'blur'},
                    {min: 4, max: 4, message: '长度 4 个字符', trigger: 'blur'}
                ],
            },
            loginForm: {
                username: null,
                password: null,
                code: null,
            },
            disabled: true,
        }
    },
    methods: {
        codeFlush() {
            this.codeURL = 'code/' + new Date().getTime();
        },
        clickLogon(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    axios.post('/logon', this.logonForm, {
                        baseURL: 'http://localhost:8080/shiro'
                    })
                        .then((response) => {
                            const body = response.data;
                            if (body.status) {
                                this.$message.success('注册成功');
                                this.logonForm = {
                                    username: null,
                                    password: null,
                                    rePassword: null,
                                    code: null,
                                };
                            } else {
                                this.codeFlush();
                                this.logonForm.code = null;
                                this.$message.warning(body.info)
                            }
                        });
                } else {
                    return false;
                }
            });
        },
        clickLogin(formName) {
            this.$refs[formName].validate((valid) => {
                if (valid) {
                    axios.post('/login', this.loginForm, {
                        baseURL: 'http://localhost:8080/shiro'
                    })
                        .then((response) => {
                            const body = response.data;
                            if (body.status) {
                                this.$message.success('登录成功');
                                this.loginForm = {
                                    username: null,
                                    password: null,
                                    code: null,
                                };
                                this.disabled = !this.disabled;
                            } else {
                                this.codeFlush();
                                this.loginForm.code = null;
                                this.$message.warning(body.info)
                            }
                        });
                } else {
                    return false;
                }
            });
        },
        clickLogout() {
            axios.get('/logout', {
                baseURL: 'http://localhost:8080/shiro'
            })
                .then(() => {
                    this.$message.success('已注销');
                    this.disabled = !this.disabled;
                });
        },
        hasRole(role) {
            axios.get('/roles/' + role, {
                baseURL: 'http://localhost:8080/shiro'
            })
                .then((response) => {
                    const body = response.data;
                    if (body.status) {
                        this.$message.success('您拥有该身份');
                    } else {
                        this.$message.error('您没有该身份')
                    }
                });
        },
        hasPermission(permission) {
            axios.get('/permissions/' + permission, {
                baseURL: 'http://localhost:8080/shiro'
            })
                .then((response) => {
                    const body = response.data;
                    if (body.status) {
                        this.$message.success('您拥有该权限');
                    } else {
                        this.$message.error('您没有该权限')
                    }
                });
        }
    }
}).$mount('#app');