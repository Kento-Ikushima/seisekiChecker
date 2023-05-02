package servlet;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.TeacherDAO;
import model.TeacherAccount;
import model.TeacherLogin;
import model.TeacherLoginBO;

@WebServlet("/TeacherRegServlet")
public class TeacherRegServlet extends HttpServlet {
  private static final long serialVersionUID = 1L;

  protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
    // フォワード
    RequestDispatcher dispatcher = request.getRequestDispatcher(
        "/WEB-INF/jsp/teacherReg.jsp");
    dispatcher.forward(request, response);
  }

  protected void doPost(HttpServletRequest request,HttpServletResponse response)throws ServletException, IOException {
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
    String teacherMail = request.getParameter("teacherMail");
    String teacherName = request.getParameter("teacherName");

    // ログイン処理を使って重複を確認
    TeacherLogin teacherLogin = new TeacherLogin(teacherId, teacherPassword);
    TeacherLoginBO bo = new TeacherLoginBO();
    boolean reg = bo.execute(teacherLogin);

    // 成否によって処理を分岐
    if (reg) { // 登録失敗時リダイレクト
        response.sendRedirect("/seisekiChecker/TeacherRegServlet");
    } else { // 登録成功時
      // データを登録。セッションスコープにもなんか入れるのか
      TeacherAccount teacherAccount = new TeacherAccount(teacherId, teacherPassword, teacherMail, teacherName);
      TeacherDAO dao = new TeacherDAO();
      int result = dao.regTeacher(teacherAccount);
      HttpSession session = request.getSession();
      session.setAttribute("teacherId", teacherId);

      // フォワード
      RequestDispatcher dispatcher =
          request.getRequestDispatcher("/WEB-INF/jsp/teacherRegOK.jsp");
      dispatcher.forward(request, response);
    }
  }
}
