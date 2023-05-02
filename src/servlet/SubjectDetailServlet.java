package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CourseStudentDAO;
import dao.SubjectDAO;
import dao.SubjectsInChargeDAO;
import model.Student;
import model.Subject;
import model.Teacher;

@WebServlet("/SubjectDetailServlet")
public class SubjectDetailServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String subjectId = request.getParameter("subjectId");

        // 科目IDに対応する科目情報を取得
        SubjectDAO subjectDAO = new SubjectDAO();
        Subject subject = subjectDAO.findSubjectById(subjectId);

        // 科目IDに対応する教員情報を取得
        SubjectsInChargeDAO subjectsInChargeDAO = new SubjectsInChargeDAO();
        List<Teacher> teacherList = subjectsInChargeDAO.findTeachersBySubjectId(subjectId);

        // 科目IDに対応する受講生情報を取得
        CourseStudentDAO courseStudentDAO = new CourseStudentDAO();
        List<Student> studentList = courseStudentDAO.findStudentsBySubjectId(subjectId);

        // JSPに渡すデータを設定
        request.setAttribute("subject", subject);
        request.setAttribute("teacherList", teacherList);
        request.setAttribute("studentList", studentList);

        // 詳細画面を表示
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/subjectDetail.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}

