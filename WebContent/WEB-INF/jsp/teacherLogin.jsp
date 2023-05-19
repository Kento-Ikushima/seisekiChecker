<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>成績チェッカー</title>
    <style>
        body {
            background-color: #edefea;
            color: #323232;
        }
    </style>
</head>
<body>
<h1>成績チェッカー</h1>
<form action="/seisekiChecker/TeacherLoginServlet" method="post">
ユーザーID:<input type="text" name="teacherId"><br>
パスワード:<input type="password" name="teacherPassword"><br>
<input type="submit" value="ログイン">
</form>
</body>
</html>