<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>Spring-MVC</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">
    <link rel="stylesheet" href="webjars/bootstrap/5.0.1/css/bootstrap.min.css">
    <script src="webjars/bootstrap/5.0.1/js/bootstrap.min.js"></script>
</head>
<body>
<div class="container">
    <nav class="nav nav-pills nav-justified">
        <a class="nav-link active" aria-current="page" href="${pageContext.request.contextPath}">首页</a>
        <a class="nav-link" href="${pageContext.request.contextPath}/login">登录认证</a>
        <a class="nav-link" href="${pageContext.request.contextPath}/home">用户首页</a>
        <a class="nav-link" href="${pageContext.request.contextPath}/json">Json响应</a>
        <a class="nav-link" href="${pageContext.request.contextPath}/mail">发送邮件</a>
        <a class="nav-link" href="${pageContext.request.contextPath}/upload">上传文件</a>
        <a class="nav-link" href="${pageContext.request.contextPath}/download">下载文件</a>
    </nav>
    <img src="${pageContext.request.contextPath}/static/img/img.png" height="500" class="img-fluid" alt="spring-mvc">
</div>
</body>
</html>
