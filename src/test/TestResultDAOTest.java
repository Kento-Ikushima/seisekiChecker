package test;

import java.sql.SQLException;

import dao.TestResultDAO;
import model.TestResult;

public class TestResultDAOTest {
	public static void main(String[] args) throws SQLException {
		testAddTestResult();
	}

//test作成テスト
	public static void testAddTestResult() {
		TestResult testResult = new TestResult(0, 70, "000000003", 4, 1);
		TestResultDAO dao = new TestResultDAO();
		int result = dao.addTestResult(testResult);

		if(result == 1) {
			System.out.println("よっしゃ！テスト結果新規作成に成功したぜ");
		} else {
		System.out.println("残念ｗｗテスト結果新規作成に失敗したぜ");
		}
	}
}
