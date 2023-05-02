package test;

import java.util.List;

import dao.CourseStudentDAO;
import model.Student;


public class CourseStudentDAOTest {
	public static void main(String[] args) {

		testFindStudentsBySubjectId();//指定した科目IDの生徒IDとNameを取得するテスト
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
	}
