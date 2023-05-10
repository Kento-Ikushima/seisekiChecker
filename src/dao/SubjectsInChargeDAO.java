package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Subject;
import model.SubjectsInCharge;
import model.Teacher;

public class SubjectsInChargeDAO {
	private final String JDBC_URL = "jdbc:h2:tcp://localhost/~/seisekiChecker";
    private final String DB_USER = "sa";
    private final String DB_PASS = "";

//teacherIDから科目のIDとNameを取得
    public List<Subject> findSubjectsByTeacherId(String teacherId) {
        List<Subject> subjectList = new ArrayList<>();

        try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
            String sql = "SELECT SUBJECT.SUBJECT_ID, SUBJECT_NAME"
            		+ " FROM SUBJECTS_IN_CHARGE "
            		+ " LEFT JOIN SUBJECT ON SUBJECTS_IN_CHARGE.SUBJECT_ID = SUBJECT.SUBJECT_ID "
            		+ " WHERE TEACHER_ID = ?";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, teacherId);

            ResultSet rs = pStmt.executeQuery();

            while(rs.next()) {
                String subjectId = rs.getString("SUBJECT.SUBJECT_ID");
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


//担当科目管理テーブルへの登録
    public boolean addSubjectsInCharge(SubjectsInCharge subjectsInCharge) {
        try(Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
            String sql = "INSERT INTO SUBJECTS_IN_CHARGE (TEACHER_ID, SUBJECT_ID) VALUES (?, ?)";
            PreparedStatement pStmt = conn.prepareStatement(sql);

            pStmt.setString(1, subjectsInCharge.getTeacherId());
            pStmt.setString(2, subjectsInCharge.getSubjectId());

            int result = pStmt.executeUpdate();

            if(result != 1) {
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

  //科目IDに対して、教員IDとNameを取得
    public List<Teacher> findTeachersBySubjectId(String subjectId) {
        List<Teacher> teacherList = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
            String sql = "SELECT TEACHER.TEACHER_ID, TEACHER.TEACHER_NAME "
                    + " FROM TEACHER "
            		+ " LEFT JOIN SUBJECTS_IN_CHARGE ON TEACHER.TEACHER_ID = SUBJECTS_IN_CHARGE.TEACHER_ID "
                    + " WHERE SUBJECTS_IN_CHARGE.SUBJECT_ID = ?";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, subjectId);

            ResultSet rs = pStmt.executeQuery();

            while (rs.next()) {
                String teacherId = rs.getString("TEACHER_ID");
                String teacherName = rs.getString("TEACHER_NAME");

                Teacher teacher = new Teacher(teacherId, teacherName);
                teacherList.add(teacher);
            }
        } catch (SQLException e) {
           e.printStackTrace();
           return null;
        }

        return teacherList;
    }


//一括削除
    public boolean deleteSubjects(List<Subject> subjectIdList) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {

            // Course Student Tableから該当するデータを削除する
            String sql = "DELETE FROM course_student WHERE subject_id = ?";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            for (Subject subjectId : subjectIdList) {
                pStmt.setString(1, subjectId.getSubjectId());
                pStmt.executeUpdate();
            }

            // Subjects in charge Tableから該当するデータを削除する
            sql = "DELETE FROM subjects_in_charge WHERE subject_id = ?";
            pStmt = conn.prepareStatement(sql);
            for (Subject subjectId : subjectIdList) {
                pStmt.setString(1, subjectId.getSubjectId());
                pStmt.executeUpdate();
            }

            // Subject Tableから該当するデータを削除する
            sql = "DELETE FROM subject WHERE subject_id = ?";
            pStmt = conn.prepareStatement(sql);
            for (Subject subjectId : subjectIdList) {
                pStmt.setString(1, subjectId.getSubjectId());
                pStmt.executeUpdate();
            }
            return true;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
