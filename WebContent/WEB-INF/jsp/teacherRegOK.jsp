<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>成績チェッカー</title>
</head>
<body>
<p>ようこそ<%= request.getSession().getAttribute("teacherId") %>さん</p>
<a href="/seisekiChecker/WelcomeServlet">トップへ</a>
<a href="/seisekiChecker/AddSubjectServlet">科目作成</a>
</body>
</html>