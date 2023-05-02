<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%
    List<Teacher> teacherList = (List<Teacher>)request.getAttribute("teacherList");
%>
<%
    List<Student> studentList = (List<Student>)request.getAttribute("studentList");
%>
<%
    Subject subject = (Subject)request.getAttribute("subject");
%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List"%>
<%@ page import="model.Subject" %>
<%@ page import="model.Student" %>
<%@ page import="model.Teacher" %>

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
	<hr>
	<a href="/seisekiChecker/ListOfSubjectsServlet">科目一覧</a>
</body>
</html>