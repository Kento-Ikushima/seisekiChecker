package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Subject;

public class SubjectDAO {
    private final String JDBC_URL = "jdbc:h2:tcp://localhost/~/seisekiChecker";
    private final String DB_USER = "sa";
    private final String DB_PASS = "";


//科目作成
    public int addSubject(Subject subject) {
    	int result = 0;

    	try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
            String sql = "INSERT INTO subject (subject_id, subject_name) VALUES (?, ?)";
            PreparedStatement pStmt = conn.prepareStatement(sql);

            pStmt.setString(1, subject.getSubjectId());
            pStmt.setString(2, subject.getSubjectName());

            result = pStmt.executeUpdate();

    	}catch (SQLException e) {
            e.printStackTrace();
            return 0;
    	}
        return result;
    }

// 全科目を取得
    public List<Subject> findAllSubjects() {
        List<Subject> subjectList = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
            String sql = "SELECT * FROM SUBJECT";
            PreparedStatement pStmt = conn.prepareStatement(sql);

            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                String subjectId = rs.getString("SUBJECT_ID");
                String subjectName = rs.getString("SUBJECT_NAME");

                Subject subject = new Subject(subjectId, subjectName);

                subjectList.add(subject);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return subjectList;
    }


// 科目IDから科目を取得
    public Subject findSubjectById(String subjectId) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
            String sql = "SELECT * FROM SUBJECT WHERE SUBJECT_ID = ?";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, subjectId);

            ResultSet rs = pStmt.executeQuery();

            if (rs.next()) {
                String subjectName = rs.getString("SUBJECT_NAME");

                return new Subject(subjectId, subjectName);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
