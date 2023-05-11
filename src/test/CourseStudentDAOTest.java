package test;

import java.util.List;

import dao.CourseStudentDAO;
import model.Student;


public class CourseStudentDAOTest {
	public static void main(String[] args) {

		testFindStudentsBySubjectId();//指定した科目IDの生徒IDとNameを取得するテスト
		testAddCourseStudent();
		testDeleteCourseStudent();
	}

//指定した科目IDの生徒IDとNameを取得するテスト
	public static void testFindStudentsBySubjectId() {
		String subjectId = "10001";
		CourseStudentDAO dao = new CourseStudentDAO();
		List<Student> result = dao.findStudentsBySubjectId(subjectId);

		if(result == null) {
			System.out.println("testFindStudentsBySubjectId:失敗したぜ");
		}else {
			for (Student student : result) {
			    System.out.println("studentId: " + student.getStudentId() + ", studentName: " + student.getStudentName());
		}
		System.out.println("testFindStudentsById:成功したぜ");			}
	}


	// 受講生徒の追加テスト
	public static void testAddCourseStudent() {
	String subjectId = "10001";
	String[] allStudentIds = {"001", "002", "003"};
	CourseStudentDAO dao = new CourseStudentDAO();
	dao.addCourseStudent(subjectId, allStudentIds);
	List<Student> result = dao.findStudentsBySubjectId(subjectId);
	if (result == null || result.isEmpty()) {
	    System.out.println("testAddCourseStudent: Failed!");
	} else {
	    for (Student student : result) {
	        System.out.println("studentId: " + student.getStudentId() + ", studentName: " + student.getStudentName());
	    }
	    System.out.println("testAddCourseStudent: Passed!");
	}}

	// 受講生徒の削除テスト
	public static void testDeleteCourseStudent() {
	String subjectId = "10001";
	String[] studentsToDeleteArray = {"001", "002"};
	CourseStudentDAO dao = new CourseStudentDAO();
	dao.deleteCourseStudent(subjectId, studentsToDeleteArray);
	List<Student> result = dao.findStudentsBySubjectId(subjectId);
	if (result == null || result.isEmpty()) {
	    System.out.println("testDeleteCourseStudent: Failed!");
	} else {
	    for (Student student : result) {
	        System.out.println("studentId: " + student.getStudentId() + ", studentName: " + student.getStudentName());
	    }
	    System.out.println("testDeleteCourseStudent: Passed!");
	}
	}
}