package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.SubjectsInChargeDAO;
import model.Subject;

@WebServlet("/ListOfSubjectsServlet")
public class ListOfSubjectsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	try {
    		Class.forName("org.h2.Driver");
    	} catch (ClassNotFoundException e) {
    		e.printStackTrace();
    		response.sendRedirect("error.jsp");
            return;
    	}


    	HttpSession session = request.getSession();
    	String teacherId = (String) session.getAttribute("teacherId");

    	if(teacherId == null) {
    		response.sendRedirect("/seisekiChecker/TeacherLoginServlet");
    	}

// 科目一覧を取得する

    	SubjectsInChargeDAO subjectsInChargeDAO = new SubjectsInChargeDAO();
        List<Subject> subjectList = subjectsInChargeDAO.findSubjectsByTeacherId(teacherId);

        if (subjectList == null) {
            response.sendRedirect("error.jsp");
            return;
        }

        // JSPに渡すデータをセットする
        request.setAttribute("subjectList", subjectList);

        // JSPにフォワードする
        request.getRequestDispatcher("/WEB-INF/jsp/subjectList.jsp").forward(request, response);
    }
}
