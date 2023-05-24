<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List"%>
<%@ page import="model.Subject" %>
<%@ page import="model.Test" %>
<%@ page import="model.Criterion" %>
<%@ page import="model.Student" %>
<%@ page import="model.CriterionNScore" %>
<%@ page import="model.TestResult" %>
<%@ page import="model.TestResultAndTest" %>


<%
    List<Test> testList = (List<Test>)request.getAttribute("testList");
%>
<%
    List<Student> studentList = (List<Student>)request.getAttribute("studentList");
%>
<%
    Subject subjectList = (Subject)request.getAttribute("subjectList");
%>
<%
    List<Criterion> criterionList = (List<Criterion>)request.getAttribute("criterionList");
%>
<%
    List<TestResultAndTest> testResultListOfCriterionN = (List<TestResultAndTest>)request.getAttribute("testResultListOfCriterionN");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>All Test Results</title>
    <style>
        body {
            background-color: #edefea;
            color: #323232;
        }
    </style>
</head>
<body>
    <h1>観点１</h1>
    <h2>テスト結果一覧</h2>
    <table border="1" style="border-collapse: collapse">
        <tr>
        	<th>生徒名</th>
    		<% for (TestResultAndTest testResult : testResultListOfCriterionN) { %>
        		<th><%= testResult.getTestName() %>(第<%= testResult.getTestRound() %>回)</th>
    		<% } %>
    	</tr>
        <% for (Student student : studentList) { %>
        <tr>
            <td><%= student.getStudentName() %></td>
            <% for (TestResultAndTest testResult : testResultListOfCriterionN) { %>
            	<td><%= testResult.getScore() %></td>
            <% } %>
        </tr>
    	<% } %>
	</table>
    <a href="/seisekiChecker/TeacherHomeServlet">ホームへ</a>
</body>
</html>