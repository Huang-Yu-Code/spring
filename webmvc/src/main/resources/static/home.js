const App = new Vue({
    data() {
        return {
            fileList: [],
        }
    },
    methods: {
        logout(){
            axios({
                method: 'post',
                url:'/home/logout',
            }).then(res=>{
                location.reload();
            }).catch();
        },
        submitUpload() {
            this.$refs.upload.submit();
        },
        handlerSuccess() {
            this.$notify.success({
                title: '系统通知',
                message: '上传成功',
            });
        },
        downloadFile() {
            axios({
                method: 'get',
                url: '/home/download',
                responseType: 'blob',
            }).then((res) => {
                let blob = new Blob([res.data]);
                let downloadElement = document.createElement("a");
                let href = window.URL.createObjectURL(blob);
                downloadElement.href = href;
                downloadElement.download = res.headers['content-disposition'].split('=')[1];
                document.body.appendChild(downloadElement);
                downloadElement.click();
                document.body.removeChild(downloadElement);
                window.URL.revokeObjectURL(href);
            }).catch((err) => {

            });
        }
    }
}).$mount('#app')