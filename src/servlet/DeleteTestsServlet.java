package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.TestDAO;


@WebServlet("/DeleteTestsServlet")
public class DeleteTestsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//subjectのdeleteServletの方にあったよくわからんデータ接続はいったん省いた
		//セッションスコープ受け取る。ログイン一応確認
		HttpSession session = request.getSession();
        String teacherId = (String) session.getAttribute("teacherId");
        if (teacherId == null) {
            response.sendRedirect("/seisekiChecker/TeacherLoginServlet");
            return;
        }

        // 選択された削除対象のテストIDを取得
        String[] deleteTestsString = request.getParameterValues("deleteTestsString");
        int[] deleteTests = new int[deleteTestsString.length];
        for (int i = 0; i < deleteTestsString.length; i++) {
        	deleteTests[i] = Integer.parseInt(deleteTestsString[i]);
        }

        // 削除対象が未選択の場合は、科目詳細画面に戻る
        if (deleteTests == null || deleteTests.length == 0) {
            response.sendRedirect("/seisekiChecker/SubjectDetailServlet");
            return;
        }

        // DAOを使い削除対象のテストを削除
        TestDAO testDAO = new TestDAO();
        boolean isSuccess = testDAO.deleteTests(deleteTests);

        if (isSuccess) {
            // 削除が成功した場合は、科目詳細画面にリダイレクトする
            response.sendRedirect("/seisekiChecker/SubjectDetailServlet");
        } else {
            // 削除に失敗した場合は、エラー画面にリダイレクトする
            response.sendRedirect("/WEB-INF/jsp/error.jsp");
        }
	}

}
