package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Student;


public class CourseStudentDAO {
    private static final String JDBC_URL = "jdbc:h2:tcp://localhost/~/seisekiChecker";
    private static final String DB_USER = "sa";
    private static final String DB_PASS = "";

  //科目IDに対して、生徒IDとNameを取得
    public List<Student> findStudentsBySubjectId(String subjectId) {
        List<Student> studentList = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
            String sql = "SELECT STUDENT.STUDENT_ID, STUDENT.STUDENT_NAME "
            		+ " FROM STUDENT "
                    + " LEFT JOIN COURSE_STUDENT ON STUDENT.STUDENT_ID = COURSE_STUDENT.STUDENT_ID "
                    + " WHERE COURSE_STUDENT.SUBJECT_ID = ?";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, subjectId);

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


    //受講生徒の削除
    public void deleteCourseStudent(String subjectId, String[] studentsToDeleteArray) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
            String sql = "DELETE FROM course_student WHERE student_id = ? AND subject_id = ?";
            PreparedStatement stmt = conn.prepareStatement(sql);
            for (String studentId : studentsToDeleteArray) {
            	stmt.setString(1, studentId);
            	stmt.setString(2, subjectId);
            	stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    //受講生徒の追加
    public void addCourseStudent(String subjectId, String[] allStudentIds) {
    	System.out.println("test");
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
            String sql = "INSERT INTO course_student (student_id, subject_id) VALUES (?, ?)";
            PreparedStatement stmt = conn.prepareStatement(sql);
            for (String studentId : allStudentIds) {
            	stmt.setString(1, studentId);
            	stmt.setString(2, subjectId);
            	stmt.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }




}
