<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="model.SubjectsInCharge" %>
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
<title>科目一覧</title>
</head>
<body>
    <h1>科目一覧</h1>
    <table>
        <thead>
            <tr>
                <th>科目ID</th>
				<th>科目名</th>
            </tr>
        </thead>
        <tbody>
            <% for (int i = 0; i < subjectList.size(); i++) { %>
                <tr>
                    <td><%= subjectList.get(i).getSubjectId() %></td>
                    <td><a href="SubjectDetailServlet?subjectId=<%= subjectList.get(i).getSubjectId() %>"><%= subjectList.get(i).getSubjectName() %></a></td>
                </tr>
            <% } %>
        </tbody>
    </table>
</body>
</html>
