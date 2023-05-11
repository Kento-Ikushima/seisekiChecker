<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>成績チェッカー</title>
</head>
<body>
<p>ようこそ<%= request.getSession().getAttribute("teacherId") %>さん(ホーム画面)</p>
<a href="/seisekiChecker/WelcomeServlet">トップへ</a>
<a href="/seisekiChecker/AddSubjectServlet">科目作成</a>
<a href="/seisekiChecker/ListOfSubjectsServlet">科目一覧</a>
<a href="/seisekiChecker/DeleteSubjectsServlet">科目削除</a>
<a href="/seisekiChecker/AddTestServlet">テスト入力</a>
<a href="/seisekiChecker/ListOfTestsServlet">テスト一覧</a>
</body>
</html>