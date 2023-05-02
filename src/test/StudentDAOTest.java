package test;



import java.util.List;

import dao.StudentDAO;
import model.Student;



public class StudentDAOTest {
	public static void main(String[] args) {

		testFindAllStudents();//全生徒のデータ取得
		testFindStudentById();//特定の生徒IDから生徒Nameを取得
	}


//全生徒取得のテスト
	public static void testFindAllStudents() {
		StudentDAO dao = new StudentDAO();
	    List<Student> studentList = dao.findAllStudents();

	    if(studentList == null) {
	    	System.out.println("testFindAllStudents:失敗したぜ");
	    } else {
	    	for (Student student : studentList) {
	    	    System.out.println("studentId: " + student.getStudentId() + ", studentName: " + student.getStudentName());
	    	}

	    }
	}

//指定したIDの生徒を取得するテスト
	public static void testFindStudentById() {
		String student = "000000001";
		StudentDAO dao = new StudentDAO();
		Student result = dao.findStudentById(student);

		if(result == null) {
			System.out.println("testFindStudentById:失敗したぜ");
		}else {
			System.out.println("studentId: " + result.getStudentId() + ", studentName: " + result.getStudentName());
			System.out.println("testFindStudentById:成功したぜ");
		}
	}



}
