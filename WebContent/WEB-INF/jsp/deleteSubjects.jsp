<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List"%>
<%@ page import="model.Subject" %>
<%
     List<Subject> subjectList = (List<Subject>) request.getAttribute("subjectList");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>削除対象の科目を選択してください</title>
    <style>
        body {
            background-color: #edefea;
            color: #323232;
        }
    </style>
</head>
<body>
    <h1>削除対象の科目を選択してください</h1>
    <form method="post" action="/seisekiChecker/DeleteSubjectsServlet">
        <table>
            <thead>
                <tr>
                    <th>削除</th>
                    <th>科目名</th>
                </tr>
            </thead>
            <tbody>
            <% for (int i = 0; i < subjectList.size(); i++) { %>
    			    <tr>
         			   <td><input type="checkbox" name="deleteSubjects" value="<%= subjectList.get(i).getSubjectId() %>"></td>
     			       <td><%= subjectList.get(i).getSubjectName() %></td>
     			   </tr>
                 <% } %>
            </tbody>
        </table>
        <input type="submit" value="削除">
    </form>
    <br>
    <a href="/seisekiChecker/TeacherHomeServlet">ホームへ</a>
</body>
</html>
