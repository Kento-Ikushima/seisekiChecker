package servlet;


import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.SubjectDAO;
import dao.SubjectsInChargeDAO;
import model.Subject;
import model.SubjectsInCharge;

@WebServlet("/AddSubjectServlet")
public class AddSubjectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		// フォワード
		RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/addSubject.jsp");
		dispatcher.forward(request, response);
		}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			Class.forName("org.h2.Driver");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}

		request.setCharacterEncoding("UTF-8");
		String subjectId = request.getParameter("subjectId");
		String subjectName = request.getParameter("subjectName");
		SubjectDAO subjectDao = new SubjectDAO();
		SubjectsInChargeDAO subjectsInChargeDao = new SubjectsInChargeDAO();

		if(subjectId != null && subjectName != null) {
			Subject subject = new Subject(subjectId, subjectName);
			subjectDao.addSubject(subject);


			String teacherId = (String) request.getSession().getAttribute("teacherId");
			SubjectsInCharge subjectsInCharge = new SubjectsInCharge(teacherId, subjectId);
			subjectsInChargeDao.addSubjectsInCharge(subjectsInCharge);

			//ここからもう一つのサーブレットに飛ぶ
			response.sendRedirect("/seisekiChecker/ListOfSubjectsServlet?teacherId=" + URLEncoder.encode(teacherId, "UTF-8"));

		} else {
			response.sendRedirect("/WEB-INF/jsp/addSubject.jsp");
		}
	}
}