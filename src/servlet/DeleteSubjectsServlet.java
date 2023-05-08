package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.SubjectsInChargeDAO;
import model.Subject;

@WebServlet("/DeleteSubjectsServlet")
public class DeleteSubjectsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)throws ServletException, IOException {
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
        request.getRequestDispatcher("/WEB-INF/jsp/deleteSubjects.jsp").forward(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            response.sendRedirect("error.jsp");
            return;
        }

        HttpSession session = request.getSession();
        String teacherId = (String) session.getAttribute("teacherId");

        if (teacherId == null) {
            response.sendRedirect("/seisekiChecker/TeacherLoginServlet");
            return;
        }

        // 選択された削除対象の科目IDを取得する
        String[] deleteSubjects = request.getParameterValues("deleteSubjects");

        if (deleteSubjects == null || deleteSubjects.length == 0) {
            // 削除対象が未選択の場合は、科目一覧画面に戻る
            response.sendRedirect("/seisekiChecker/ListOfSubjectsServlet");
            return;
        }

        // Subjectオブジェクトのリストを作成
        List<Subject> subjectList = new ArrayList<>();
        for (String subjectId : deleteSubjects) {
        	// 削除対象の科目の名前を取得する
        	 String subjectName = request.getParameter("subjectName_" + subjectId);
        	Subject subject = new Subject(subjectId, subjectName);
        	subjectList.add(subject);

        }

        // SubjectsInChargeDAOを使って、削除対象の科目を削除する
        SubjectsInChargeDAO subjectsInChargeDAO = new SubjectsInChargeDAO();
        boolean isSuccess = subjectsInChargeDAO.deleteSubjects(subjectList);

        if (isSuccess) {
            // 削除が成功した場合は、科目一覧画面にリダイレクトする
            response.sendRedirect("/seisekiChecker/ListOfSubjectsServlet");
        } else {
            // 削除に失敗した場合は、エラー画面にリダイレクトする
            response.sendRedirect("error.jsp");
        }
    }
}
