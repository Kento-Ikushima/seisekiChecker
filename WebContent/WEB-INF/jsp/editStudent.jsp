<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%
    List<Student> studentList = (List<Student>)request.getAttribute("studentList");
%>
<%@ page import="model.Student" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List"%>
<!DOCTYPE html>
<html lang="ja">
<head>
    <meta charset="UTF-8">
    <title>生徒編集画面</title>
        <style>
        body {
            background-color: #edefea;
            color: #323232;
        }
    </style>
</head>
<body>
    <h1>生徒を編集してください</h1>

    <form method="post" action="<%=request.getContextPath()%>/EditStudentServlet">
        <input type="hidden" name="subjectId" value="<%=request.getAttribute("subjectId")%>">

        <h2>すでに受講している生徒（チェックを外すことでリストから外す）</h2>
        <ul>
            <% List<Student> selectedStudentList = (List<Student>) request.getAttribute("selectedStudentList");
               if (selectedStudentList != null && !selectedStudentList.isEmpty()) {
                   for (Student student : selectedStudentList) { %>
                       <li><input type="checkbox" name="selectedStudentIds" value="<%=student.getStudentId()%>" checked> <%=student.getStudentName()%></li>
            <%     }
               } else { %>
                   <li>まだ受講生徒はいません</li>
            <% } %>
        </ul>

        <h2>未受講の生徒（チェックしてリストに加える）</h2>
		<ul>
		  <% List<Student> allStudentList = (List<Student>) request.getAttribute("allStudentList");
		     if (allStudentList != null && !allStudentList.isEmpty()) {
		         boolean hasUnselectedStudent = false;  // 初期値を設定
		         for (Student student : allStudentList) {
		             boolean isSelected = false;
		             if (selectedStudentList != null) {
		                 for (Student selectedStudent : selectedStudentList) {
		                     if (selectedStudent.getStudentId().equals(student.getStudentId())) {
		                         isSelected = true;
		                         break;
		                     }
		                 }
		             }
		             if (!isSelected) {
		                 hasUnselectedStudent = true;
		                 %>
		                 <li><input type="checkbox" name="allStudentIds" value="<%=student.getStudentId()%>"> <%=student.getStudentName()%></li>
		                 <%
		             }
		         }
		         if (!hasUnselectedStudent) {
		             %>
		             <li>未受講の生徒はいません</li>
		             <%
		         }
		     } else {
		         %>
		         <li>生徒はいません</li>
		         <%
		     }
		  %>
		</ul>


        <input type="submit" value="Save">
    </form>
    <a href="SubjectDetailServlet?subjectId=<%=request.getAttribute("subjectId") %>">科目詳細に戻る</a>
</body>
</html>
