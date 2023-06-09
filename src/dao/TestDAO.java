package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Test;
import model.TestLackingId;

public class TestDAO {
	private final String JDBC_URL = "jdbc:h2:tcp://localhost/~/seisekiChecker";
    private final String DB_USER = "sa";
    private final String DB_PASS = "";


//テストを新規で作成
    public int addTest(TestLackingId testLackingId) {
    	int result = 0;

    	try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
            String sql = "INSERT INTO test (test_name, subject_id, criterion_id, full_score, multiplier) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement pStmt = conn.prepareStatement(sql);

            pStmt.setString(1, testLackingId.getTestName());
            pStmt.setString(2, testLackingId.getSubjectId());
            pStmt.setInt(3, testLackingId.getCriterionId());
            pStmt.setInt(4, testLackingId.getFullScore());
            pStmt.setDouble(5, testLackingId.getMultiplier());

            result = pStmt.executeUpdate();

    	}catch (SQLException e) {
            e.printStackTrace();
            return 0;
    	}
        return result;
    }


  //テストに担当科目管理をleft join。teacherIdが一致するレコードを取る
  //つまり、teacherIDに紐づくテストを探す
    public List<Test> findTestsByTeacherId(String teacherId) throws SQLException {
        List<Test> testList = new ArrayList<>();

        try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
            String sql = "SELECT t.test_id, t.test_name, t.subject_id, t.criterion_id, t.full_score, t.multiplier " +
                    "FROM test t " +
                    "LEFT JOIN subjects_in_charge sic ON t.subject_id = sic.subject_id " +
                    "WHERE sic.teacher_id = ? and t.deleted = 0";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, teacherId);

            ResultSet rs = pStmt.executeQuery();

            while(rs.next()) {
            	int testId = rs.getInt("test_id");
                String testName = rs.getString("test_name");
                String subjectId = rs.getString("subject_id");
                int criterionId = rs.getInt("criterion_id");
                int fullScore = rs.getInt("full_score");
                double multiplier = rs.getDouble("multiplier");
                int deleted = rs.getInt("deleted");

                Test test = new Test(testId, testName, subjectId, criterionId, fullScore, multiplier, deleted);

                testList.add(test);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return testList;
    }

  //subjectIDに紐づくテスト取得（科目詳細画面用、テスト削除画面用）
    public List<Test> findTestsBySubjectId(String subjectId) throws SQLException {
        List<Test> testList = new ArrayList<>();

        try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
            String sql = "SELECT * FROM test WHERE subject_id = ? and deleted = 0";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, subjectId);

            ResultSet rs = pStmt.executeQuery();

            while(rs.next()) {
            	int testId = rs.getInt("test_id");
                String testName = rs.getString("test_name");
                int criterionId = rs.getInt("criterion_id");
                int fullScore = rs.getInt("full_score");
                double multiplier = rs.getDouble("multiplier");
                int deleted = rs.getInt("deleted");

                Test test = new Test(testId, testName, subjectId, criterionId, fullScore, multiplier, deleted);
                testList.add(test);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return testList;
    }

  //選択したtestIDに対応するレコードを一括で論理削除
    public boolean deleteTests(int[] deleteTests) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {

            String sql = "UPDATE test SET deleted = 1 WHERE test_id = ?";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            for (int testId : deleteTests) {
                pStmt.setInt(1, testId);
                pStmt.executeUpdate();
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

 // （１件取得）testIDに対するテスト情報
    public Test findTestById(int testId) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
            String sql = "SELECT * FROM TEST WHERE TEST_ID = ? deleted = 0";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setInt(1, testId);

            ResultSet rs = pStmt.executeQuery();

            if (rs.next()) {
                String testName = rs.getString("test_name");
                String subjectId = rs.getString("subject_id");
                int criterionId = rs.getInt("criterion_id");
                int fullScore = rs.getInt("full_score");
                double multiplier = rs.getDouble("multiplier");
                int deleted = rs.getInt("deleted");


                return new Test(testId, testName, subjectId, criterionId, fullScore, multiplier, deleted);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
