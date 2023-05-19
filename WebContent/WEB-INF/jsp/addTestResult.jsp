<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List"%>
<%@ page import="model.Subject" %>
<%@ page import="model.Test" %>
<%@ page import="model.Criterion" %>
<%@ page import="model.Student" %>
<%
    List<Criterion> criterionList = (List<Criterion>)request.getAttribute("criterionList");
%>
<%
    List<Student> studentList = (List<Student>)request.getAttribute("studentList");
%>
<%
    Test test = (Test)request.getAttribute("test");
%>
<%
    Subject subject = (Subject)request.getAttribute("subject");
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>テスト結果入力画面</title>
	    <style>
        body {
            background-color: #edefea;
            color: #323232;
        }
	    </style>
</head>
<body>
	<h1>テストの結果を入力してください</h1>
	<h2>テスト情報</h2>
	<table border="1">
        <thead>
            <tr>
                <th>テスト名</th>
                <th>科目名</th>
                <th>観点</th>
                <th>満点</th>
                <th>重み</th>
            </tr>
        </thead>
        <tbody>
            <tr>
                <td><%= test.getTestName() %></td>
                <td><%= subject.getSubjectName() %></td>
                <td><%= criterionList.get(test.getCriterionId() - 1).getCriterionName() %></td>
                <td><%= test.getFullScore() %></td>
                <td><%= test.getMultiplier() %></td>
                </tr>
        </tbody>
    </table>
	<hr>


	<form action="/seisekiChecker/AddTestResultServlet?subjectId=<%= subject.getSubjectId() %>" method="post">
		<table border="1">
  		<tr>
    		<th>受講生徒</th>
 		    <th>点数</th>
 		</tr>
  		<%
  			for (Student student : studentList) {
		%>
    	<tr>
      		<td><%= student.getStudentName() %></td>
      		<td><input type="number" name="score-<%= student.getStudentId() %>"></td>
    	</tr>
		<%
 		 }
		%>
		</table>
		<br>
		<input type="hidden" name="testResultId" value="0">
		<input type="hidden" name="testId" value="${test.testId}">
		<input type="submit" value="テスト結果登録">
	</form>
</body>
</html>
