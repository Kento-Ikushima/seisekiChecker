package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.AverageCriterionNScore;
import model.CriterionNEvaluation;
import model.CriterionNScore;
import model.Evaluation;
import model.FinalResult;
import model.TestResult;
import model.TestResultAndTest;

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

//×【情報取得】科目IDに対応するテスト結果情報（これだと、いつの小テスト①か分からず、思った表にならない）
    public List<TestResultAndTest> findTestResultBySubjectId(String subjectId) {
    	List<TestResultAndTest> testResultList = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
            String sql = "SELECT * "
              		   + "FROM test_result "
               		   + "INNER JOIN test ON test_result.test_id = test.test_id "
               		   + "WHERE test.subject_id = ? ";
            PreparedStatement pStmt = conn.prepareStatement(sql);

            pStmt.setString(1, subjectId);
            ResultSet rs = pStmt.executeQuery();

            while(rs.next()) {
            	int testResultId = rs.getInt("test_result_id");
            	int testId = rs.getInt("test.test_id");
            	String studentId = rs.getString("student_id");
            	int score= rs.getInt("score");
                String testName = rs.getString("test_name");
                int criterionId = rs.getInt("criterion_id");
                int fullScore = rs.getInt("full_score");
                double multiplier = rs.getDouble("multiplier");

                TestResultAndTest testResultAndTest = new TestResultAndTest(testResultId, testId, studentId, score, testName, subjectId, criterionId, fullScore, multiplier);

                testResultList.add(testResultAndTest);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return testResultList;
    }

//★【データ数 受講生×3】（観点ｎ評点）生徒の観点ｎ評点を算出
    public List<CriterionNScore> calculateCriterionNScore(String subjectId) {
    	List<CriterionNScore> criterionNScoreList = new ArrayList<>();
    	try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
            String sql= "SELECT "
        			+"	student_id, criterion_id,sum(a),sum(multiplier), cast(sum(a) as float)/sum(multiplier)*100 as criterion_n_score "
        			+"FROM("
        			+"	SELECT "
        			+"		student_id, criterion_id, multiplier, (cast(score as float)/full_score)*multiplier as a "
        			+"	FROM "
        			+"		test_result "
        			+"	INNER JOIN "
        			+"		test ON test_result.test_id = test.test_id "
        			+"	WHERE "
        			+"		test.subject_id = ?) "
        			+"GROUP BY "
        			+"	student_id, criterion_id";

            PreparedStatement pStmt = conn.prepareStatement(sql);

            pStmt.setString(1, subjectId);
            ResultSet rs = pStmt.executeQuery();

            while(rs.next()) {
                String studentId = rs.getString("student_id");
                int criterionId = rs.getInt("criterion_id");
                int criterionNScore = rs.getInt("criterion_n_score");

                CriterionNScore criterionNScores = new CriterionNScore(subjectId, studentId, criterionId, criterionNScore);
                criterionNScoreList.add(criterionNScores);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return criterionNScoreList;
    }


//★【データ数 3】（各観点の受講生平均）
    public List<AverageCriterionNScore> calculateAverageCriterionNScore(String subjectId) {
    	List<AverageCriterionNScore> averageCriterionNScoreList = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
        	String sql =
        			"SELECT criterion_id, AVG(criterion_n_score) as average_criterion_n_score " +
                    "FROM ( " +
                    "    SELECT student_id, criterion_id, SUM(a), SUM(multiplier), CAST(SUM(a) AS FLOAT) / SUM(multiplier) * 100 AS criterion_n_score " +
                    "    FROM ( " +
                    "        SELECT student_id, criterion_id, multiplier, (CAST(score AS FLOAT) / full_score) * multiplier AS a " +
                    "        FROM test_result " +
                    "        INNER JOIN test ON test_result.test_id = test.test_id " +
                    "        WHERE test.subject_id = ? " +
                    "    ) " +
                    "    GROUP BY student_id, criterion_id " +
                    ") " +
                    "GROUP BY criterion_id";

            PreparedStatement pStmt = conn.prepareStatement(sql);

            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, subjectId);
            ResultSet rs = pStmt.executeQuery();

            while(rs.next()) {
                int criterionId = rs.getInt("criterion_id");
                int averageCriterionNScore = rs.getInt("average_criterion_n_score");

                AverageCriterionNScore averageCriterionNScores = new AverageCriterionNScore(subjectId,criterionId, averageCriterionNScore);
                averageCriterionNScoreList.add(averageCriterionNScores);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return averageCriterionNScoreList;
    }

//★【データ数 受講生徒数】（ある生徒の評価。観点別評価の平均）
    public List<Evaluation> calculateEvaluation(String subjectId) throws SQLException {
    	List<Evaluation> evaluationList = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
            String sql =
            		"SELECT student_id, AVG(criterion_n_score) as evaluation " +
                    "FROM ( " +
                    "    SELECT student_id, criterion_id, SUM(a), SUM(multiplier), CAST(SUM(a) AS FLOAT) / SUM(multiplier) * 100 AS criterion_n_score " +
                    "    FROM ( " +
                    "        SELECT student_id, criterion_id, multiplier, (CAST(score AS FLOAT) / full_score) * multiplier AS a " +
                    "        FROM test_result " +
                    "        INNER JOIN test ON test_result.test_id = test.test_id " +
                    "        WHERE test.subject_id = ? " +
                    "    ) " +
                    "    GROUP BY student_id, criterion_id " +
                    ") " +
                    "GROUP BY student_id";

            PreparedStatement pStmt = conn.prepareStatement(sql);

            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, subjectId);
            ResultSet rs = pStmt.executeQuery();

            while(rs.next()) {
                String studentId = rs.getString("student_id");
                int evaluation = rs.getInt("evaluation");

                Evaluation evaluations = new Evaluation(subjectId,studentId, evaluation);
                evaluationList.add(evaluations);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return evaluationList;
    }

//★【データ数 1】（受講生徒の評価平均）
    public int calculateAverageEvaluation(String subjectId) throws SQLException {
    	try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
    		String sql =
    				 "SELECT AVG(evaluation) AS average_evaluation " +
    	             "FROM ( " +
    	             "    SELECT student_id, AVG(criterion_n_score) AS evaluation " +
    	             "    FROM ( " +
    	             "        SELECT student_id, criterion_id, SUM(a), SUM(multiplier), CAST(SUM(a) AS FLOAT) / SUM(multiplier) * 100 AS criterion_n_score " +
    	             "        FROM ( " +
    	             "            SELECT student_id, criterion_id, multiplier, (CAST(score AS FLOAT) / full_score) * multiplier AS a " +
    	             "            FROM test_result " +
    	             "            INNER JOIN test ON test_result.test_id = test.test_id " +
    	             "            WHERE test.subject_id = ? " +
    	             "        ) " +
    	             "        GROUP BY student_id, criterion_id " +
    	             "    ) " +
    	             "    GROUP BY student_id " +
    	             ")";

    		PreparedStatement pStmt = conn.prepareStatement(sql);
    		pStmt.setString(1, subjectId);
    		ResultSet rs = pStmt.executeQuery();

    		if (rs.next()) {
    			double averageEvaluationDouble = rs.getDouble("average_evaluation");
    			int averageEvaluation = (int) Math.round(averageEvaluationDouble);
    			return averageEvaluation;
    		} else {
    			return 0;
    		}
    	} catch (SQLException e) {
    		e.printStackTrace();
    		return 0;
    	}
    }

//★【データ数 受講生×３】（観点n評価）観点ｎ評点を観点ｎ評価に読み替え
    public List<CriterionNEvaluation> translateToEvaluation(String subjectId) throws SQLException {
    	List<CriterionNEvaluation> criterionNEvaluationList = new ArrayList<>();
    	try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
    		String sql
    			   = "SELECT student_id, criterion_id, " +
    	             "CASE " +
    	             "    WHEN criterion_n_score >= 70 THEN 'A' " +
    	             "    WHEN criterion_n_score >= 35 THEN 'B' " +
    	             "    ELSE 'C' " +
    	             "END AS criterion_n_evaluation " +
    	             "FROM ( " +
    	             "    SELECT student_id, criterion_id, SUM(a), SUM(multiplier), CAST(SUM(a) AS FLOAT) / SUM(multiplier) * 100 AS criterion_n_score " +
    	             "    FROM ( " +
    	             "        SELECT student_id, criterion_id, multiplier, (CAST(score AS FLOAT) / full_score) * multiplier AS a " +
    	             "        FROM test_result " +
    	             "        INNER JOIN test ON test_result.test_id = test.test_id " +
    	             "        WHERE test.subject_id = ? " +
    	             "    ) " +
    	             "    GROUP BY student_id, criterion_id " +
    	             ")";

    		PreparedStatement pStmt = conn.prepareStatement(sql);
    		pStmt.setString(1, subjectId);
    		ResultSet rs = pStmt.executeQuery();

            while(rs.next()) {
                String studentId = rs.getString("student_id");
                int criterionId = rs.getInt("criterion_id");
                String criterionNEvaluation = rs.getString("criterion_n_evaluation");

                CriterionNEvaluation criterionNEvaluations = new CriterionNEvaluation(subjectId, studentId, criterionId, criterionNEvaluation);
                criterionNEvaluationList.add(criterionNEvaluations);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return criterionNEvaluationList;
    }


//★【データ数 受講生徒数】（評定）評価を評定に読み替え
    public List<FinalResult> translateToFinalResult(String subjectId) throws SQLException {
    	List<FinalResult> finalResultList = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
            String sql =
            		"SELECT student_id, " +
                    "CASE " +
                    "    WHEN evaluation >= 80 THEN '5' " +
                    "    WHEN evaluation >= 60 THEN '4' " +
                    "    WHEN evaluation >= 45 THEN '3' " +
                    "    WHEN evaluation >= 35 THEN '2' " +
                    "    ELSE '1' " +
                    "END AS final_result " +
                    "FROM ( " +
                    "    SELECT student_id, AVG(criterion_n_score) AS evaluation " +
                    "    FROM ( " +
                    "        SELECT student_id, criterion_id, SUM(a), SUM(multiplier), CAST(SUM(a) AS FLOAT) / SUM(multiplier) * 100 AS criterion_n_score " +
                    "        FROM ( " +
                    "            SELECT student_id, criterion_id, multiplier, (CAST(score AS FLOAT) / full_score) * multiplier AS a " +
                    "            FROM test_result " +
                    "            INNER JOIN test ON test_result.test_id = test.test_id " +
                    "            WHERE test.subject_id = ? " +
                    "        ) temp " +
                    "        GROUP BY student_id, criterion_id " +
                    "    ) temp2 " +
                    "    GROUP BY student_id " +
                    ") temp3";

            PreparedStatement pStmt = conn.prepareStatement(sql);

            pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, subjectId);
            ResultSet rs = pStmt.executeQuery();
            while(rs.next()) {
                String studentId = rs.getString("student_id");
                int finalResult = rs.getInt("final_result");

                FinalResult finalResults = new FinalResult(subjectId,studentId, finalResult);
                finalResultList.add(finalResults);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }

        return finalResultList;
    }

//【データ数 1】（評定平均）
    public int calculateAverageFinalResult(String subjectId) throws SQLException {
    	try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
    		String sql =
    		     "SELECT AVG(final_result) AS average_final_result " +
                 "FROM ( " +
                 "    SELECT student_id, " +
                 "           CASE " +
                 "               WHEN evaluation >= 80 THEN 5 " +
                 "               WHEN evaluation >= 60 THEN 4 " +
                 "               WHEN evaluation >= 45 THEN 3 " +
                 "               WHEN evaluation >= 35 THEN 2 " +
                 "               ELSE 1 " +
                 "           END AS final_result " +
                 "    FROM ( " +
                 "        SELECT student_id, AVG(criterion_n_score) AS evaluation " +
                 "        FROM ( " +
                 "            SELECT student_id, criterion_id, SUM(a), SUM(multiplier), CAST(SUM(a) AS FLOAT) / SUM(multiplier) * 100 AS criterion_n_score " +
                 "            FROM ( " +
                 "                SELECT student_id, criterion_id, multiplier, (CAST(score AS FLOAT) / full_score) * multiplier AS a " +
                 "                FROM test_result " +
                 "                INNER JOIN test ON test_result.test_id = test.test_id " +
                 "                WHERE test.subject_id = ? " +
                 "            ) temp " +
                 "            GROUP BY student_id, criterion_id " +
                 "        ) temp2 " +
                 "        GROUP BY student_id " +
                 "    ) temp3 " +
                 ") temp4";

    		PreparedStatement pStmt = conn.prepareStatement(sql);
    		pStmt.setString(1, subjectId);
    		ResultSet rs = pStmt.executeQuery();

    		if (rs.next()) {
    			double averageFinalResultDouble = rs.getDouble("average_final_result");
    			int averageFinalResult = (int) Math.round(averageFinalResultDouble);
    			return averageFinalResult;
    		} else {
    			return 0;
    		}
    	} catch (SQLException e) {
    		e.printStackTrace();
    		return 0;
    	}
    }
}