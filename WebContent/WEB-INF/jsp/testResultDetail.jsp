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

<form action="/seisekiChecker/DeleteTestResultServlet" method="post">
  <input type="hidden" name="testId" value="<%= testResultWithAllList.get(0).getTestId() %>">
  <input type="hidden" name="testRound" value="<%= testResultWithAllList.get(0).getTestRound() %>">
  <input type="submit" value="削除">
</form>
</body>
</html>