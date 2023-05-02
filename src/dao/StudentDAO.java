package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Student;


public class StudentDAO {
//datebase接続情報
	private final String JDBC_URL = "jdbc:h2:tcp://localhost/~/seisekiChecker";
	private final String DB_USER = "sa";
	private final String DB_PASS = "";



// 全生徒を取得
    public List<Student> findAllStudents() {
        List<Student> studentList = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
            String sql = "SELECT * FROM STUDENT";
            PreparedStatement pStmt = conn.prepareStatement(sql);

            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                String studentId = rs.getString("STUDENT_ID");
                String studentName = rs.getString("STUDENT_NAME");

                Student student = new Student(studentId, studentName);

                studentList.add(student);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return null;
        }
        return studentList;
    }

// 生徒IDから生徒を取得
    public Student findStudentById(String studentId) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
            String sql = "SELECT * FROM STUDENT WHERE STUDENT_ID = ?";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, studentId);

            ResultSet rs = pStmt.executeQuery();

            if (rs.next()) {
                String studentName = rs.getString("STUDENT_NAME");
                Student student = new Student(studentId, studentName);

                return student;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
