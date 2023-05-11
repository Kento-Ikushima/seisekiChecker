package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CourseStudentDAO;
import dao.CriterionDAO;
import dao.SubjectDAO;
import dao.SubjectsInChargeDAO;
import dao.TestDAO;
import model.Criterion;
import model.Student;
import model.Subject;
import model.Teacher;
import model.Test;

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

        //科目IDに紐づくテスト情報を取得
        TestDAO testDAO = new TestDAO();
        List<Test> testList = null;
        try {
			testList = testDAO.findTestsBySubjectId(subjectId);
		} catch (SQLException e) {
			e.printStackTrace();
		}

        //観点IDに対応する観点名を取得
        CriterionDAO criterionDAO = new CriterionDAO();
        List<Criterion> criterionList = criterionDAO.findAllCriterions();


        // JSPに渡すデータを設定
        request.setAttribute("subject", subject);
        request.setAttribute("teacherList", teacherList);
        request.setAttribute("studentList", studentList);
        request.setAttribute("testList", testList);
        request.setAttribute("criterionList", criterionList);
        // 詳細画面を表示
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/subjectDetail.jsp");
        dispatcher.forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}