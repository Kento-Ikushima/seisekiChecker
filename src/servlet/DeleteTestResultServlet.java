package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CriterionDAO;
import dao.TestResultDAO;
import model.Criterion;
import model.TestResultWithAll;

@WebServlet("/DeleteTestResultServlet")
public class DeleteTestResultServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

  	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  		//testIdとtestRoundを受け取る
  		String testIdString = request.getParameter("testId");
  		String testRoundString = request.getParameter("testRound");
		int testId = Integer.parseInt(testIdString);
		int testRound = Integer.parseInt(testRoundString);

		//testIdとtestRoundに対するテスト情報とその結果の情報を取得
		TestResultDAO testResultDAO = new TestResultDAO();
		List<TestResultWithAll> testResultWithAllList = testResultDAO.findTestResultByTestIdAndTestRound(testId, testRound);

		CriterionDAO criterionDAO = new CriterionDAO();
		List<Criterion> criterionList = criterionDAO.findAllCriterions();

		//リクエストパラメータに設置し、フォワード
		request.setAttribute("testResultWithAllList", testResultWithAllList);
        request.setAttribute("criterionList", criterionList);

        request.getRequestDispatcher("/WEB-INF/jsp/testResultDetail.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
  		//testIdとtestRoundを受け取る
  		String testIdString = request.getParameter("testId");
  		String testRoundString = request.getParameter("testRound");
		int testId = Integer.parseInt(testIdString);
		int testRound = Integer.parseInt(testRoundString);

		//deleteTestResultメソッドを呼出し物理削除（消す前に消したリストは作っておく）
		TestResultDAO testResultDAO = new TestResultDAO();
		List<TestResultWithAll> deletedTestResultList = testResultDAO.findTestResultByTestIdAndTestRound(testId, testRound);
		boolean isSuccess = testResultDAO.deleteTestResult(testId, testRound);

		//削除成功した場合は、セッションスコープにパラメータ設置後、テスト結果一覧サーブレットへリダイレクト
		if (isSuccess) {
			HttpSession session = request.getSession();
			session.setAttribute("deletedTestResultList", deletedTestResultList);
			response.sendRedirect(request.getContextPath() + "ListOfTestResultsServlet?subjectId=" + deletedTestResultList.get(1).getSubjectId());
		}else {
		    response.sendRedirect("/WEB-INF/jsp/error.jsp");
		}
	}

}
