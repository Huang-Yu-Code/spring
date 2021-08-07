<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Spring-MVC</title>
</head>
<body>
当前位置:${location}
<form action="${pageContext.request.contextPath}/login" method="post">

    <label>username:
        <input type="text" name="username" value="root">
    </label>
    <br>
    <label>password:
        <input type="password" name="password" value="root">
    </label>
    <br>
    <input type="submit" value="登录">
</form>
</body>
</html>
