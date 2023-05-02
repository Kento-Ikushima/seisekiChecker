<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>成績チェッカー</title>
</head>
<body>
<form action="/seisekiChecker/TeacherRegServlet" method="post">
ユーザーID:<input type="text" name="teacherId"><br>
パスワード:<input type="password" name="teacherPassword"><br>
メール:<input type="text" name="teacherMail"><br>
名前:<input type="text" name="teacherName"><br>
<input type="submit" value="ユーザー登録">
</form>
</body>
</html>