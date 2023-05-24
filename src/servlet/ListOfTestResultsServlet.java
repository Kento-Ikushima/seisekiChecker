package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CourseStudentDAO;
import dao.CriterionDAO;
import dao.SubjectDAO;
import dao.TestDAO;
import dao.TestResultDAO;
import model.AverageCriterionNScore;
import model.Criterion;
import model.CriterionNEvaluation;
import model.CriterionNScore;
import model.Evaluation;
import model.FinalResult;
import model.Student;
import model.Subject;
import model.Test;
import model.TestResultAndTest;

//全観点の情報ありの成績一覧
@WebServlet("/ListOfTestResultsServlet")
public class ListOfTestResultsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//リクエストパラメータを受け取る。科目idと
		request.setCharacterEncoding("UTF-8");
		String subjectId = request.getParameter("subjectId");
		String criterionIdString = request.getParameter("criterionId");

		//★以下お決まりセット
		//テストIDに対応するテスト情報を取得
		TestDAO testDAO = new TestDAO();
		List<Test> testList = null;
		try {
			testList = testDAO.findTestsBySubjectId(subjectId);
			request.setAttribute("testList", testList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//受講生徒情報を取得
		CourseStudentDAO courseStudentDAO = new CourseStudentDAO();
		List<Student> studentList = courseStudentDAO.findStudentsBySubjectId(subjectId);
		request.setAttribute("studentList", studentList);

		//科目IDに対応する科目情報を取得
		SubjectDAO subjectDAO = new SubjectDAO();
		Subject subject = subjectDAO.findSubjectById(subjectId);
		request.setAttribute("subject", subject);

		//観点IDに対応する観点名を取得
		CriterionDAO criterionDAO = new CriterionDAO();
        List<Criterion> criterionList = criterionDAO.findAllCriterions();
        request.setAttribute("criterionList", criterionList);

		if(criterionIdString == null) {//全観点ページ用
			//テストIDと紐づく科目IDで、限定したテスト結果情報の取得
			TestResultDAO testResultDAO = new TestResultDAO();
			List<TestResultAndTest> testResultList = testResultDAO.findTestResultBySubjectId(subjectId);
			request.setAttribute("testResultList", testResultList);

			//観点n評点の取得
			List<CriterionNScore> criterionNScoreList = testResultDAO.calculateCriterionNScore(subjectId);
			request.setAttribute("criterionNScoreList", criterionNScoreList);

			//観点n評点の平均の取得(3件)
			List<AverageCriterionNScore> averageCriterionNScoreList = testResultDAO.calculateAverageCriterionNScore(subjectId);
			request.setAttribute("averageCriterionNScoreList", averageCriterionNScoreList);

			//生徒の評価の取得
			List<Evaluation> evaluationList;
			evaluationList = null;
			try {
				evaluationList = testResultDAO.calculateEvaluation(subjectId);
				request.setAttribute("evaluationList", evaluationList);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			//生徒の評価の平均の取得(1件)
			int averageEvaluation;
			try {
				averageEvaluation = testResultDAO.calculateAverageEvaluation(subjectId);
				request.setAttribute("averageEvaluation", averageEvaluation);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			//観点別評価の読み替え情報の取得
			try {
				List<CriterionNEvaluation> criterionNEvaluationList = testResultDAO.translateToEvaluation(subjectId);
				request.setAttribute("criterionNEvaluationList", criterionNEvaluationList);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			//評定の読み替え情報の取得
			try {
				List<FinalResult> finalResultList = testResultDAO.translateToFinalResult(subjectId);
				request.setAttribute("finalResultList", finalResultList);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			//評定の平均の取得(1件)
			try {
				int averageFinalResult = testResultDAO.calculateAverageFinalResult(subjectId);
				request.setAttribute("averageFinalResult", averageFinalResult);
			} catch (SQLException e) {
				e.printStackTrace();
			}

			//全観点成績情報画面へフォワード
			RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/allTestResultList.jsp");
			dispatcher.forward(request, response);

		} else {//観点別用
			TestResultDAO testResultDAO = new TestResultDAO();
			int criterionId = Integer.parseInt(criterionIdString);
			List<TestResultAndTest> testResultListOfCriterionN = testResultDAO.findTestResultBySubjectIdAndCriterionId(subjectId, criterionId);
			request.setAttribute("testResultListOfCriterionN", testResultListOfCriterionN);
System.out.println(criterionId);
			RequestDispatcher dispatcher;
			if (criterionId == 1) {
				System.out.println(criterionId);
			    dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/Criterion1TestResultList.jsp");
			} else if (criterionId == 2) {
			    dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/Criterion2TestResultList.jsp");
			} else if (criterionId == 3) {
			    dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/Criterion3TestResultList.jsp");
			} else {
			    // 不明なcriterionIdに対する処理
			    response.sendError(HttpServletResponse.SC_NOT_FOUND);
			    return;
			}
			dispatcher.forward(request, response);
		}
	}
}

