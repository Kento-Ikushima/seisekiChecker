package test;



import java.util.List;

import dao.TeacherDAO;
import model.Teacher;
import model.TeacherAccount;
import model.TeacherLogin;



public class TeacherDAOTest {
	public static void main(String[] args) {
		testFindByTeacherLogin1();//ユーザーが見つかる場合
		testFindByTeacherLogin2();//ユーザーが見つからない場合

		testRegTeacher1(); //ユーザー登録成功
		testRegTeacher2(); //ユーザー登録成功

		testFindAllTeachers();//全教員のデータ取得
		testFindTeacherById();//特定の教員IDから教員Nameを取得
	}

//ログインテスト
	public static void testFindByTeacherLogin1() {
		TeacherLogin teacherLogin = new TeacherLogin("ikushima", "1111");
		TeacherDAO dao = new TeacherDAO();
		TeacherAccount result = dao.findByTeacherLogin(teacherLogin);

		if(result != null &&
			result.getTeacherId().equals("ikushima") &&
			result.getTeacherPassword().equals("1111") &&
			result.getTeacherMail().equals("ikushima@gmial.com") &&
			result.getTeacherName().equals("生島健斗") ) {
			System.out.println("testFindByTeacherLogin1:成功したぜ");
		} else {
			System.out.println("testFindByTeacherLogin1:失敗したぜ");
			System.out.println(result.getTeacherId());
			System.out.println(result.getTeacherPassword());
			System.out.println(result.getTeacherMail());
			System.out.println(result.getTeacherName());
		}
	}
	public static void testFindByTeacherLogin2() {
		TeacherLogin teacherLogin = new TeacherLogin("ikushima", "11112");
		TeacherDAO dao = new TeacherDAO();
		TeacherAccount result = dao.findByTeacherLogin(teacherLogin);

		if(result == null) {
			System.out.println("testFindByTeacherLogin2:成功したぜ");
		} else {
			System.out.println("testFindByTeacherLogin2:失敗したぜ");
		}
	}





//ユーザー登録テスト
	public static void testRegTeacher1() {
		TeacherAccount teacherAccount = new TeacherAccount("inoue", "9999", "inoue@gmail.com", "井上");
		TeacherDAO dao = new TeacherDAO();
		int result = dao.regTeacher(teacherAccount);

		if(result == 1) {
			System.out.println("testRegTeacher1:成功したぜ");
		} else {
		System.out.println("testRegTeacher1:失敗したぜ");
		System.out.println(result);
		}
	}

	public static void testRegTeacher2() {
		TeacherAccount teacherAccount = new TeacherAccount("kitamura", "1111", "ikushima@gmial.com", "生島健斗");
		TeacherDAO dao = new TeacherDAO();
		int result = dao.regTeacher(teacherAccount);

		if(result == 0) {
			System.out.println("testRegTeacher2:成功したぜ");
		} else {
		System.out.println("testRegTeacher2:失敗したぜ");
		System.out.println(result);
		}
	}

//全教員取得のテスト
	public static void testFindAllTeachers() {
		TeacherDAO dao = new TeacherDAO();
	    List<Teacher> teacherList = dao.findAllTeachers();

	    if(teacherList == null) {
	    	System.out.println("testFindAllTeachers:失敗したぜ");
	    } else {
	    	for (Teacher teacher : teacherList) {
	    	    System.out.println("teacherId: " + teacher.getTeacherId() + ", teacherName: " + teacher.getTeacherName());
	    	}

	    }
	}

//指定したIDの教員を取得するテスト
	public static void testFindTeacherById() {
		String teacher = "ikushima";
		TeacherDAO dao = new TeacherDAO();
		Teacher result = dao.findTeacherById(teacher);

		if(result == null) {
			System.out.println("testFindTeacherById:失敗したぜ");
		}else {
			System.out.println("teacherId: " + result.getTeacherId() + ", teacherName: " + result.getTeacherName());
			System.out.println("testFindTeacherById:成功したぜ");
		}
	}



}
