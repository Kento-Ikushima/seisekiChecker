<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List"%>
<%@ page import="model.Subject" %>
<%@ page import="model.Test" %>
<%@ page import="model.Criterion" %>
<%
     List<Test> testList = (List<Test>) request.getAttribute("testList");
%>
<%
    List<Criterion> criterionList = (List<Criterion>)request.getAttribute("criterionList");
%>
<%
    Subject subject = (Subject)request.getAttribute("subject");
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>科目IDに紐づくテスト削除画面</title>
</head>
<body>
    <h1>削除対象のテストを選択してください</h1>
    <form method="post" action="/seisekiChecker/DeleteTestsServlet">
    	<input type="hidden" name="subjectId" value="<%=request.getAttribute("subjectId")%>">
        <table border="1">
        <thead>
            <tr>
            	<th>削除</th>
                <th>テスト名</th>
                <th>観点</th>
                <th>満点</th>
                <th>重み</th>
            </tr>
        </thead>
        <tbody>
            <%
            	for (int i = 0; i < testList.size(); i++) {
					Test test = testList.get(i);
					Criterion criterion = criterionList.get(test.getCriterionId() - 1);
			%>
                <tr>
                	<td><input type="checkbox" name="deleteTestsString" value="<%= testList.get(i).getTestId() %>"></td>
                    <td><%= test.getTestName() %></td>
                    <td><%= criterionList.get(test.getCriterionId() - 1).getCriterionName() %></td>
                    <td><%= test.getFullScore() %></td>
                    <td><%= test.getMultiplier() %></td>
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