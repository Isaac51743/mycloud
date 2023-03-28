<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>login</title>
</head>
<body>
<form action="/login" method="post"> <!--method must be POST-->
    <input type="hidden" value="${_csrf.token}" name="_csrf">
    username:<input type="text" name="my_username"/><br/> <!--name must be "username" (can be customized)-->
    password:<input type="password" name="my_password"/><br/> <!--name must be "password" (can be customized)-->
    remember me:<input type="checkbox" name="remember-me" value="true"/><br/><br/> <!--name must be "remember-me" (can be customized)-->
    <input type="submit" value="login">
</form>

</body>
</html>