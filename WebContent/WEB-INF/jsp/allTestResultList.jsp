<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.List"%>
<%@ page import="model.Evaluation" %>
<%@ page import="model.Subject" %>
<%@ page import="model.Test" %>
<%@ page import="model.Criterion" %>
<%@ page import="model.Student" %>
<%@ page import="model.CriterionNScore" %>
<%@ page import="model.TestResult" %>
<%@ page import="model.AverageCriterionNScore" %>
<%@ page import="model.Evaluation" %>
<%@ page import="model.CriterionNEvaluation" %>
<%@ page import="model.FinalResult" %>
<%
    List<Evaluation> evaluationList = (List<Evaluation>)request.getAttribute("evaluationList");
%>
<%
    List<CriterionNScore> criterionNScoreList = (List<CriterionNScore>)request.getAttribute("criterionNScoreList");
%>
<%
    List<Test> testList = (List<Test>)request.getAttribute("testList");
%>
<%
    List<Student> studentList = (List<Student>)request.getAttribute("studentList");
%>
<%
    List<Subject> subjectList = (List<Subject>)request.getAttribute("subjectList");
%>
<%
    List<Criterion> criterionList = (List<Criterion>)request.getAttribute("criterionList");
%>
<%
    List<TestResult> testResultList = (List<TestResult>)request.getAttribute("testResultList");
%>
<%
    List<AverageCriterionNScore> averageCriterionNScoreList = (List<AverageCriterionNScore>)request.getAttribute("averageCriterionNScoreList");
%>
<%
    int averageEvaluation = (int)request.getAttribute("averageEvaluation");
%>
<%
    List<CriterionNEvaluation> criterionNEvaluationList = (List<CriterionNEvaluation>) request.getAttribute("criterionNEvaluationList");
%>
<%
    List<FinalResult> finalResultList = (List<FinalResult>)request.getAttribute("finalResultList");
%>
<%
    int averageFinalResult = (int)request.getAttribute("averageFinalResult");
%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>All Test Results</title>
</head>
<body>
    <h1>成績、テスト結果一覧</h1>
	<h2>成績情報</h2>
    <table border = "1">
        <tr>
            <th>生徒名</th>
            <th>評定</th>
			<th>評価</th>
            <th>観点１評点</th>
            <th>観点２評点</th>
            <th>観点３評点</th>
            <th>観点１評価</th>
            <th>観点２評価</th>
            <th>観点３評価</th>
        </tr>
        <% for (Evaluation evaluation : evaluationList) { %>
            <tr>
                <td><%= evaluation.getStudentId() %></td>
                <% for (FinalResult finalResult : finalResultList) { %>
                    <% if (finalResult.getStudentId().equals(evaluation.getStudentId())) { %>
                		<td><%= finalResult.getFinalResult() %></td>
                    <% } %>
                <% } %>
                <td><%= evaluation.getEvaluation() %></td>
                <% for (CriterionNScore criterionNScore : criterionNScoreList) { %>
                    <% if (criterionNScore.getStudentId().equals(evaluation.getStudentId())) { %>
                        <td><%= criterionNScore.getCriterionNScore() %></td>
                    <% } %>
                <% } %>
                <% for (CriterionNEvaluation criterionNEvaluation : criterionNEvaluationList) { %>
                    <% if (criterionNEvaluation.getStudentId().equals(evaluation.getStudentId())) { %>
                        <td><%= criterionNEvaluation.getCriterionNEvaluation() %></td>
                    <% } %>
                <% } %>

            </tr>
        <% } %>
        <tr>
            <td>平均</td>
            <td><%= averageFinalResult %></td>
            <td><%= averageEvaluation %></td>
            <% for (AverageCriterionNScore averageCriterionNScore : averageCriterionNScoreList) { %>
                <td><%= averageCriterionNScore.getAverageCriterionNScore() %></td>
            <% } %>
			<td></td>
			<td></td>
			<td></td>
        </tr>
    </table>
</body>
</html>
