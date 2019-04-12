<%@page contentType="text/html; charset=GBK" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>登陆</title>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
    <form method="POST" action="j_security_check">
        <p>用户<input type="text" name="j_username"></p>
        <p>密码<input type="password" name="j_password"></p>
        <input type="submit" value="Enter">
    </form>
</body>
</html>
