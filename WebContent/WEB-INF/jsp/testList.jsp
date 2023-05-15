<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List"%>
<%@ page import="model.Subject" %>
<%@ page import="model.Test" %>
<%@ page import="model.Criterion" %>
<%
    List<Test> testList = (List<Test>)request.getAttribute("testList");
%>
<%
    List<Criterion> criterionList = (List<Criterion>)request.getAttribute("criterionList");
%>
<%
    List<Subject> subjectList = (List<Subject>)request.getAttribute("subjectList");
%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>テスト一覧画面</title>
</head>
<body>
	<h1>テスト一覧</h1>

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
			<%
				for (int i = 0; i < testList.size(); i++) {
					Test test = testList.get(i);
					Subject subject = subjectList.get(i);
					Criterion criterion = criterionList.get(test.getCriterionId() - 1);
			%>
					<tr>
						<td><a href="AddTestResultServlet?testId=<%= test.getTestId() %>"><%= test.getTestName() %></a></td>
						<td><%= subject.getSubjectName() %></td>
						<td><%= criterion.getCriterionName() %></td>
						<td><%= test.getFullScore() %></td>
						<td><%= test.getMultiplier() %></td>
					</tr>
			<%
				}
			%>
		</tbody>
	</table>
	<a href="/seisekiChecker/TeacherHomeServlet">ホームへ</a>
</body>
</html>
