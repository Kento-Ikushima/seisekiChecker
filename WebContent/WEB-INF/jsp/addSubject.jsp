<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>科目入力画面</title>
</head>
<body>
	<h1>科目を入力してください</h1>
	<form action="/seisekiChecker/AddSubjectServlet" method="post">
		科目ID:<input type="text" name="subjectId"><br>
		科目名:<input type="text" name="subjectName"><br>
		<input type="submit" value="科目登録">
	</form>
</body>
</html>
