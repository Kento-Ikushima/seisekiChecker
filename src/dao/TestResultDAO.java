package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import model.TestResult;

public class TestResultDAO {
	private final String JDBC_URL = "jdbc:h2:tcp://localhost/~/seisekiChecker";
    private final String DB_USER = "sa";
    private final String DB_PASS = "";


//テスト結果を新規で作成
    public int addTestResult(TestResult testResult) {
    	int result = 0;

    	//test_result_idは自動生成されるので送らない
    	try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
            String sql = "INSERT INTO test_result (test_id, student_id, score) VALUES (?, ?, ?)";
            PreparedStatement pStmt = conn.prepareStatement(sql);

            pStmt.setInt(1, testResult.getTestId());
            pStmt.setString(2, testResult.getStudentId());
            pStmt.setInt(3, testResult.getScore());

            result = pStmt.executeUpdate();

    	}catch (SQLException e) {
            e.printStackTrace();
            return 0;
    	}
        return result;
    }
}
