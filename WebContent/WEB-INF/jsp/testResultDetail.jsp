<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List"%>

<%@ page import="model.TestResultWithAll" %>

<%
    List<TestResultWithAll> testResultWithAllList = (List<TestResultWithAll>)request.getAttribute("testResultWithAllList");
%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<h1>第<%= testResultWithAllList.get(0).getTestRound() %>回 <%= testResultWithAllList.get(0).getTestName() %></h1>

//ここにテストの情報
<table>
<tr>
<td>観点</td>
<td><%= testResultWithAllList.get(0).getCriterionId() %></td>
<td>満点</td>
<td><%= testResultWithAllList.get(0).getFullScore() %></td>
<td>重み</td>
<td><%= testResultWithAllList.get(0).getMultiplier() %></td>
</tr>
</table>

//ここに点数の表（２行、ｎ列の表、列はリストで回すだけ）
<table>
<tr>
	<% for(int i=0; i<testResultWithAllList.size(); i++) { %>
		<td><%= testResultWithAllList.get(i).getStudentName() %></td>
	<% } %>
</tr>
<tr>
	<% for(int i=0; i<testResultWithAllList.size(); i++) { %>
		<td><%= (testResultWithAllList.get(i).getScore()) %></td>
	<% } %>
</tr>
</table>

//削除ボタン、ほかの削除ボタンとサーブレット自体を参照。引数とかね
<form action="/seisekiChecker/DeleteTestResultServlet" method="post">
  <input type="hidden" name="testResultId" value="<%= testResultWithAllList.get(0).getTestResultId() %>">
  <input type="submit" value="削除">
</form>
</body>
</html>