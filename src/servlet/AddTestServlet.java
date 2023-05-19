package servlet;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.SubjectsInChargeDAO;
import dao.TestDAO;
import model.Subject;
import model.TestLackingId;


@WebServlet("/AddTestServlet")
public class AddTestServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
		HttpSession session = request.getSession();
    	String teacherId = (String) session.getAttribute("teacherId");
    	String subjectId = request.getParameter("subjectId");

        // teacherIdから科目一覧を取得する
    	SubjectsInChargeDAO subjectsInChargeDAO = new SubjectsInChargeDAO();
        List<Subject> subjectList = subjectsInChargeDAO.findSubjectsByTeacherId(teacherId);

        // 科目の選択肢のHTMLを生成する
        StringBuilder subjectOptions = new StringBuilder();
        for (Subject subject : subjectList) {
            subjectOptions.append("<option value=\"").append(subject.getSubjectId()).append("\">")
                   .append(subject.getSubjectName()).append("</option>");
        }


        // リクエストスコープに選択肢を設定する
        request.setAttribute("subjectOptions", subjectOptions.toString());
        request.setAttribute("subjectId", subjectId);

        // フォワード
     	RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/addTest.jsp");
     	dispatcher.forward(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		String testName = request.getParameter("testName");
		String subjectId = request.getParameter("subjectId");
		String criterionIdString = request.getParameter("criterionId");
		String fullScoreString = request.getParameter("fullScore");
		String multiplierString = request.getParameter("multiplier");

		TestDAO testDao = new TestDAO();

		if(testName != null && subjectId != null && criterionIdString != null && fullScoreString != null && multiplierString != null) {
			int criterionId = Integer.parseInt(criterionIdString);
			int fullScore = Integer.parseInt(fullScoreString);
			double multiplier = Double.parseDouble(multiplierString);

			TestLackingId testLackingId = new TestLackingId(testName, subjectId, criterionId, fullScore, multiplier);
			testDao.addTest(testLackingId);

			//ここからもう一つのサーブレットに飛ぶ
			String teacherId = (String) request.getSession().getAttribute("teacherId");
			response.sendRedirect("/seisekiChecker/ListOfTestsServlet?teacherId=" + URLEncoder.encode(teacherId, "UTF-8"));

		} else {
			response.sendRedirect("/WEB-INF/jsp/addTest.jsp");
		}
	}
}