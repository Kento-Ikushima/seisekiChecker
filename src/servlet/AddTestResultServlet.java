package servlet;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

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
import model.Criterion;
import model.Student;
import model.Subject;
import model.Test;
import model.TestResult;


@WebServlet("/AddTestResultServlet")
public class AddTestResultServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//jspからリクエストパラメータtest_idを取得
		String testIdString = request.getParameter("testId");
		int testId = Integer.parseInt(testIdString);

		//test_idに対するテスト情報を取得（科目名と観点名もついでに取得）
		TestDAO testDAO = new TestDAO();
		Test test = testDAO.findTestById(testId);

		String subjectId = test.getSubjectId();
		SubjectDAO subjectDAO = new SubjectDAO();
		Subject subject = subjectDAO.findSubjectById(subjectId);

		CriterionDAO criterionDAO = new CriterionDAO();
        List<Criterion> criterionList = criterionDAO.findAllCriterions();

		//test_idに対するsubject_id、それに紐づくstudent_idを取得（student_nameに変換）
        CourseStudentDAO courseStudentDAO = new CourseStudentDAO();
        List<Student> studentList = courseStudentDAO.findStudentsBySubjectId(subjectId);

		//リクエストパラメータに設置
        request.setAttribute("test", test);
        request.setAttribute("subject", subject);
        request.setAttribute("criterionList", criterionList);
        request.setAttribute("studentList", studentList);

		//テスト結果入力画面にフォワード
        request.getRequestDispatcher("/WEB-INF/jsp/addTestResult.jsp").forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    //jspからリクエストパラメータ取得
	    String testResultIdString = request.getParameter("testResultId");
	    String testIdString = request.getParameter("testId");
	    String subjectId = request.getParameter("subjectId");

		//スコアが入力されているかチェックする
		boolean hasScores = false;
		Map<String, String[]> parameterMap = request.getParameterMap();
		for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
			String parameterName = entry.getKey();
			if (parameterName.startsWith("score-")) {
				String[] parameterValues = entry.getValue();
				if (parameterValues != null && parameterValues.length > 0 && !parameterValues[0].isEmpty()) {
					hasScores = true;
					break;
				}
			}
		}

	    //testResultIdとtestIdのnullチェックする
	    if(testResultIdString != null && testIdString != null) {
	        int testResultId = Integer.parseInt(testResultIdString);
	        int testId = Integer.parseInt(testIdString);
	        TestResultDAO testResultDAO = new TestResultDAO();

	        //スコアが入力されている場合のみTestRoundを更新して取得する
	        if (hasScores) {
	        	int testRound = testResultDAO.determineTestRoundForNewResults(testId);
	        	//生徒のidと成績を取得する
	        	for (Map.Entry<String, String[]> entry : parameterMap.entrySet()) {
	        		String parameterName = entry.getKey();
	            	//score-で始まるパラメータを成績として扱う
	            	if (parameterName.startsWith("score-")) {
	            		String studentId = parameterName.substring(6);
	            		String[] parameterValues = entry.getValue();
	            		//ここで空白行をはじいている
	            		if (parameterValues != null && parameterValues.length > 0 && !parameterValues[0].isEmpty()) {
	            			int score = Integer.parseInt(parameterValues[0]);

	            			//TestResultオブジェクトを作成してデータベースに保存する
	            			TestResult testResult = new TestResult(testResultId, testId, studentId, score,testRound);
	            			testResultDAO.addTestResult(testResult);
	            		}
	            	}
	        	}
	        }else {
	        	response.sendRedirect("/seisekiChecker/AllListOfTestResultsServlet?subjectId=" + URLEncoder.encode(subjectId, "UTF-8"));
	        }
	       //成績表示サーブレットにリダイレクト
			response.sendRedirect("/seisekiChecker/AllListOfTestResultsServlet?subjectId=" + URLEncoder.encode(subjectId, "UTF-8"));
	    } else {
	        response.sendRedirect("/WEB-INF/jsp/addTestResult.jsp");
	    }

	}
}

