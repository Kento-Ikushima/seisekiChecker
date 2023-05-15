<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    List<Teacher> teacherList = (List<Teacher>)request.getAttribute("teacherList");
%>
<%
    List<Student> studentList = (List<Student>)request.getAttribute("studentList");
%>
<%
    List<Test> testList = (List<Test>)request.getAttribute("testList");
%>
<%
    List<Criterion> criterionList = (List<Criterion>)request.getAttribute("criterionList");
%>
<%
    Subject subject = (Subject)request.getAttribute("subject");
%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List"%>
<%@ page import="model.Subject" %>
<%@ page import="model.Student" %>
<%@ page import="model.Teacher" %>
<%@ page import="model.Test" %>
<%@ page import="model.Criterion" %>

<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>科目詳細</title>
</head>
<body>
	<h1>科目詳細</h1>
	<hr>
	<p>科目ID: <%= subject.getSubjectId() %></p>
	<p>科目名: <%= subject.getSubjectName() %></p>
	<p>担当教員:
		<%
			for (Teacher teacher : teacherList) {
				out.println(teacher.getTeacherName() + " ");
			}
		%>
	</p>
	<p>受講生:
		<%
			for (Student student : studentList) {
				out.println(student.getStudentName() + " ");
			}
		%>
	</p>
	<a href="EditStudentServlet?subjectId=<%= subject.getSubjectId() %>">生徒登録</a>
	<hr>
	<h2>テスト一覧</h2>
    <table border="1">
        <thead>
            <tr>
                <th>テスト名</th>
                <th>観点</th>
                <th>満点</th>
                <th>重み</th>
            </tr>
        </thead>
        <tbody>
            <% for (Test test : testList) { %>
                <tr>
                    <td><a href="AddTestResultServlet?testId=<%= test.getTestId() %>"><%= test.getTestName() %></a></td>
                    <td><%= criterionList.get(test.getCriterionId() - 1).getCriterionName() %></td>
                    <td><%= test.getFullScore() %></td>
                    <td><%= test.getMultiplier() %></td>
                </tr>
            <% } %>
        </tbody>
    </table>
    <hr>
	<a href="/seisekiChecker/DeleteTestsServlet?subjectId=<%= subject.getSubjectId() %>">テスト削除</a>
	<a href="/seisekiChecker/ListOfSubjectsServlet">科目一覧</a>
</body>
</html>