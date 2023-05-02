package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import model.TeacherLogin;
import model.TeacherLoginBO;

@WebServlet("/TeacherLoginServlet")
public class TeacherLoginServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  protected void doGet(HttpServletRequest request,
      HttpServletResponse response)

      throws ServletException, IOException {

    // フォワード
    RequestDispatcher dispatcher = request.getRequestDispatcher(
        "/WEB-INF/jsp/teacherLogin.jsp");
    dispatcher.forward(request, response);
  }

  protected void doPost(HttpServletRequest request,
      HttpServletResponse response)
      throws ServletException, IOException {

	// JDBCドライバのロード
	try {
	      Class.forName("org.h2.Driver");
	} catch (ClassNotFoundException e) {
	      e.printStackTrace();
	}


    // リクエストパラメータの取得
    request.setCharacterEncoding("UTF-8");
    String teacherId = request.getParameter("teacherId");
    String teacherPassword = request.getParameter("teacherPassword");

    // ログイン処理の実行
    TeacherLogin teacherLogin = new TeacherLogin(teacherId, teacherPassword);
    TeacherLoginBO bo = new TeacherLoginBO();
    boolean result = bo.execute(teacherLogin);

    // ログイン処理の成否によって処理を分岐
    if (result) { // ログイン成功時

      // セッションスコープにユーザーIDを保存
      HttpSession session = request.getSession();
      session.setAttribute("teacherId", teacherId);

      // フォワード
      RequestDispatcher dispatcher =
          request.getRequestDispatcher("/WEB-INF/jsp/teacherLoginOK.jsp");
      dispatcher.forward(request, response);
    } else { // ログイン失敗時
    // リダイレクト
      response.sendRedirect("/seisekiChecker/TeacherLoginServlet");
    }
  }
}
