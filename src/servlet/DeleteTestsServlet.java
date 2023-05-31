package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CriterionDAO;
import dao.TestDAO;
import model.Criterion;
import model.Test;


@WebServlet("/DeleteTestsServlet")
public class DeleteTestsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//リクエストパラメータ受け取る
		String subjectId = request.getParameter("subjectId");

		//科目IDに紐づいたテスト一覧を取得
        TestDAO testDAO = new TestDAO();
        List<Test> testList = null;
        try {
			testList = testDAO.findTestsBySubjectId(subjectId);
		} catch (SQLException e) {
			e.printStackTrace();
		}

		//観点IDに紐づいた観点リストを取得
		CriterionDAO criterionDAO = new CriterionDAO();
        List<Criterion> criterionList = criterionDAO.findAllCriterions();

		//リクエストスコープにセット
		request.setAttribute("testList", testList);
		request.setAttribute("criterionList", criterionList);
		request.setAttribute("subjectId", subjectId);

		//フォワード
		request.getRequestDispatcher("/WEB-INF/jsp/deleteTests.jsp").forward(request, response);
	}




	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //リクエストパラメータ受け取る。セットもしとく
      	String subjectId = request.getParameter("subjectId");
      	request.setAttribute("subjectId", subjectId);

		//セッションスコープ受け取る。ログイン一応確認
		HttpSession session = request.getSession();
        String teacherId = (String) session.getAttribute("teacherId");
        if (teacherId == null) {
            response.sendRedirect("/seisekiChecker/TeacherLoginServlet");
            return;
        }

        // 選択された削除対象のテストIDを取得
        String[] deleteTestsString = request.getParameterValues("deleteTestsString");


        // 削除対象が未選択の場合は、科目詳細画面に戻る
        if (deleteTestsString == null || deleteTestsString.length == 0) {
            response.sendRedirect("/seisekiChecker/SubjectDetailServlet");
            return;
        }

        int[] deleteTests = new int[deleteTestsString.length];
        for (int i = 0; i < deleteTestsString.length; i++) {
        	deleteTests[i] = Integer.parseInt(deleteTestsString[i]);
        }

        // DAOを使い削除対象のテストを論理削除
        TestDAO testDAO = new TestDAO();
        boolean isSuccess = testDAO.deleteTests(deleteTests);

        if (isSuccess) {
            // 削除が成功した場合は、科目詳細画面に遷移する
        	response.sendRedirect(request.getContextPath() + "/SubjectDetailServlet?subjectId=" + subjectId);
        } else {
            // 削除に失敗した場合は、エラー画面にリダイレクトする
            response.sendRedirect("/WEB-INF/jsp/error.jsp");
        }
	}

}