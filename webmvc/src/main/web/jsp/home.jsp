<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Spring-MVC</title>
</head>
<body>
当前位置:${location}
<form action="${pageContext.request.contextPath}/logout" method="post">
    <input type="submit" value="退出">
</form>
</body>
</html>
