<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List"%>
<%@ page import="model.Subject" %>
<%
    List<Subject> subjectList = (List<Subject>)request.getAttribute("subjectList");
%>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>テスト作成画面</title>
</head>
<body>
	<h1>テストを入力してください</h1>
	<form action="/seisekiChecker/AddTestServlet" method="post">
		テスト名:<input type="text" name="testName"><br>
		科目の選択:
		<select name="subjectId">
			<%= request.getAttribute("subjectOptions") %>
		</select><br>
		観点の選択:
		<select name="criterionId">
			<option value="1">知識</option>
			<option value="2">思考</option>
			<option value="3">主体性</option>
		</select><br>
		満点:<input type="number" name="fullScore"><br>
		重みの選択:
		<select name="multiplier">
			<option value="1.0">軽い</option>
			<option value="2.0">ふつう</option>
			<option value="3.0">重い</option>
		</select><br>
		<input type="submit" value="テスト作成">
	</form>
</body>
</html>
