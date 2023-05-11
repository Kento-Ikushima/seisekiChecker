package test;

import java.sql.SQLException;
import java.util.List;

import dao.TestDAO;
import model.Test;

public class TestDAOTest {
	public static void main(String[] args) throws SQLException {
		testAddTest();//testを作成
		testFindTestsByTeacherId();
	}

//test作成テスト
	public static void testAddTest() {
		Test test = new Test("小テスト", "55444", 1, 10, 3);
		TestDAO dao = new TestDAO();
		int result = dao.addTest(test);

		if(result == 1) {
			System.out.println("testAddTest:成功したぜ");
		} else {
		System.out.println("testAddTest:失敗したぜ");
		}
	}


//teacherIdを参照し、自分のテスト一覧を取得するテスト
	public static void testFindTestsByTeacherId() throws SQLException {
		String teacherId = "ikushima";
		TestDAO dao = new TestDAO();
		List<Test> result = dao.findTestsByTeacherId(teacherId);

		if(result == null) {
			System.out.println("testFindTestsByTeacherId:失敗したぜ");
		}else {
			System.out.println("testFindTestsByTeacherId:成功したぜ");
		}
	}
}
