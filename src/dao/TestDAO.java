package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Test;

public class TestDAO {
	private final String JDBC_URL = "jdbc:h2:tcp://localhost/~/seisekiChecker";
    private final String DB_USER = "sa";
    private final String DB_PASS = "";


//テストを新規で作成
    public int addTest(Test test) {
    	int result = 0;

    	try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
            String sql = "INSERT INTO test (test_name, subject_id, criterion_id, full_score, multiplier) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pStmt = conn.prepareStatement(sql);

            pStmt.setString(1, test.getTestName());
            pStmt.setString(2, test.getSubjectId());
            pStmt.setInt(3, test.getCriterionId());
            pStmt.setInt(4, test.getFullScore());
            pStmt.setDouble(5, test.getMultiplier());

            result = pStmt.executeUpdate();

    	}catch (SQLException e) {
            e.printStackTrace();
            return 0;
    	}
        return result;
    }


  //テストに担当科目管理をleft join。teacherIdが一致するレコードを取る
    public List<Test> findTestsByTeacherId(String teacherId) throws SQLException {
        List<Test> testList = new ArrayList<>();

        try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
            String sql = "SELECT t.test_name, t.subject_id, t.criterion_id, t.full_score, t.multiplier " +
                    "FROM test t " +
                    "LEFT JOIN subjects_in_charge sic ON t.subject_id = sic.subject_id " +
                    "WHERE sic.teacher_id = ?";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, teacherId);

            ResultSet rs = pStmt.executeQuery();

            while(rs.next()) {
                String testName = rs.getString("test_name");
                String subjectId = rs.getString("subject_id");
                int criterionId = rs.getInt("criterion_id");
                int fullScore = rs.getInt("full_score");
                double multiplier = rs.getDouble("multiplier");

                Test test = new Test(testName, subjectId, criterionId, fullScore, multiplier);

                testList.add(test);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return testList;
    }
}
