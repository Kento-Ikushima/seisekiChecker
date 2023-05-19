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

//全観点の情報ありの成績一覧
@WebServlet("/AllListOfTestResultsServlet")
public class ListOfTestResultsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//リクエストパラメータを受け取る。科目idと
		request.setCharacterEncoding("UTF-8");
		String subjectId = request.getParameter("subjectId");


		//★以下お決まりセット
		//テストIDに対応するテスト情報を取得
		TestDAO testDAO = new TestDAO();
		List<Test> testList = null;
		try {
			testList = testDAO.findTestsBySubjectId(subjectId);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//受講生徒情報を取得
		CourseStudentDAO courseStudentDAO = new CourseStudentDAO();
		List<Student> studentList = courseStudentDAO.findStudentsBySubjectId(subjectId);

		//科目IDに対応する科目情報を取得
		SubjectDAO subjectDAO = new SubjectDAO();
		Subject subject = subjectDAO.findSubjectById(subjectId);

		//観点IDに対応する観点名を取得
		CriterionDAO criterionDAO = new CriterionDAO();
        List<Criterion> criterionList = criterionDAO.findAllCriterions();


        //★以下新情報
        //××テストIDと紐づく科目IDで、限定したテスト結果情報の取得
        TestResultDAO testResultDAO = new TestResultDAO();

		//観点n評点の取得
        List<CriterionNScore> criterionNScoreList = testResultDAO.calculateCriterionNScore(subjectId);

        //観点n評点の平均の取得(3件)
        List<AverageCriterionNScore> averageCriterionNScoreList = testResultDAO.calculateAverageCriterionNScore(subjectId);

        //生徒の評価の取得
        List<Evaluation> evaluationList;
        evaluationList = null;
		try {
			evaluationList = testResultDAO.calculateEvaluation(subjectId);
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

		//リクエストパラメータを設定
        request.setAttribute("testList", testList);
        request.setAttribute("studentList", studentList);
        request.setAttribute("subject", subject);
        request.setAttribute("criterionList", criterionList);
 //       request.setAttribute("testResultList", testResultList);
        request.setAttribute("criterionNScoreList", criterionNScoreList);
        request.setAttribute("averageCriterionNScoreList", averageCriterionNScoreList);
        request.setAttribute("evaluationList", evaluationList);



		//全観点成績情報画面へフォワード
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/allTestResultList.jsp");
        dispatcher.forward(request, response);
	}
}