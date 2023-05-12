package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.CourseStudentDAO;
import dao.StudentDAO;
import model.Student;


@WebServlet("/EditStudentServlet")
public class EditStudentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String subjectId = request.getParameter("subjectId");

	    // 科目IDに対応する受講生情報を取得
	    CourseStudentDAO courseStudentDAO = new CourseStudentDAO();
	    List<Student> selectedStudentList = courseStudentDAO.findStudentsBySubjectId(subjectId);

	    // 全生徒を取得
	    StudentDAO studentDAO = new StudentDAO();
	    List<Student> allStudentList = studentDAO.findAllStudents();

	    // JSPに渡すデータを設定
	    request.setAttribute("selectedStudentList", selectedStudentList);
	    request.setAttribute("allStudentList", allStudentList);
	    request.setAttribute("subjectId", subjectId);

	    // 編集画面を表示
	    RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/editStudent.jsp");
	    dispatcher.forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String subjectId = request.getParameter("subjectId");
	    String[] selectedStudentIds = request.getParameterValues("selectedStudentIds");
	    String[] allStudentIds = request.getParameterValues("allStudentIds");


	//まず受講生の削除。selectedリストとdeleteCouiseメソッドを使い、チェックがついているものを検索かけて、チェックが外れたものをあぶりだして削除
	    CourseStudentDAO courseStudentDAO = new CourseStudentDAO();
	    List<Student> nowSelectedStudentList = courseStudentDAO.findStudentsBySubjectId(subjectId); //変更前の情報
	    List<String> nowSelectedStudentIdList = new ArrayList<>();
	    for (Student student : nowSelectedStudentList) {
	    	nowSelectedStudentIdList.add(student.getStudentId());
	    }
	    List<String> selectedStudentIdList = Arrays.asList(selectedStudentIds); //selectedをListに変換
	    List<String> studentsToDelete = new ArrayList<>();

	    for (String studentId : nowSelectedStudentIdList) {
	        if (!selectedStudentIdList.contains(studentId)) {
	            studentsToDelete.add(studentId);
	        }
	    }
	    if (!studentsToDelete.isEmpty()) {
	    	String[] studentsToDeleteArray = studentsToDelete.toArray(new String[0]);
	        courseStudentDAO.deleteCourseStudent(subjectId, studentsToDeleteArray);
	    }

	//次に未受講生の追加。allリストをaddCourseメソッドを使い、チェックついてるものをそのまま追加
	if (allStudentIds != null && allStudentIds.length > 0) {
	    courseStudentDAO.addCourseStudent(subjectId, allStudentIds);
	}


	// 科目詳細画面に遷移
	response.sendRedirect(request.getContextPath() + "/SubjectDetailServlet?subjectId=" + subjectId);
	}
}
