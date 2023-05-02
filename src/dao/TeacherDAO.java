package dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Teacher;
import model.TeacherAccount;
import model.TeacherLogin;


public class TeacherDAO {
//datebase接続情報
	private final String JDBC_URL = "jdbc:h2:tcp://localhost/~/seisekiChecker";
	private final String DB_USER = "sa";
	private final String DB_PASS = "";


//ログイン
	public TeacherAccount findByTeacherLogin(TeacherLogin teacherLogin) {
		TeacherAccount teacherAccount = null;
		//detabase接続
		try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
		      // SELECT文を準備
		      String sql = "SELECT TEACHER_ID, TEACHER_PASSWORD, TEACHER_MAIL, TEACHER_NAME FROM TEACHER WHERE TEACHER_ID = ? AND TEACHER_PASSWORD = ?";
		      PreparedStatement pStmt = conn.prepareStatement(sql);
		      pStmt.setString(1, teacherLogin.getTeacherId()); //後だしじゃんけんで？に込めてる
		      pStmt.setString(2, teacherLogin.getTeacherPassword());

		      // SELECTを実行し、結果表を取得
		      ResultSet rs = pStmt.executeQuery();

		      // 一致したユーザーが存在した場合
		      // そのユーザーを表すTeacherインスタンスを生成
		      if (rs.next()) {
		        // 結果表からデータを取得
		        String teacherId = rs.getString("TEACHER_ID");
		        String teacherPassword = rs.getString("TEACHER_PASSWORD");
		        String teacherMail = rs.getString("TEACHER_MAIL");
		        String teacherName = rs.getString("TEACHER_NAME");
		        //箱詰め
		        teacherAccount = new TeacherAccount(teacherId, teacherPassword, teacherMail, teacherName);
		      }
		    } catch (SQLException e) {
		      e.printStackTrace();
		      return null;
		    }
		    // 出荷
		    return teacherAccount;
	}


//ユーザー登録
	public int regTeacher(TeacherAccount teacherAccount) {
        int result = 0;

        try  (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)){
        	String sql = "INSERT INTO TEACHER (TEACHER_ID, TEACHER_PASSWORD, TEACHER_MAIL, TEACHER_NAME) VALUES (?, ?, ?, ?)";
        	PreparedStatement pStmt = conn.prepareStatement(sql);

        	pStmt.setString(1, teacherAccount.getTeacherId());
        	pStmt.setString(2, teacherAccount.getTeacherPassword());
        	pStmt.setString(3, teacherAccount.getTeacherMail());
        	pStmt.setString(4, teacherAccount.getTeacherName());

        	result = pStmt.executeUpdate();


        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
        return result;

    }

// 全教員を取得
    public List<Teacher> findAllTeachers() {
        List<Teacher> teacherList = new ArrayList<>();

        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
            String sql = "SELECT * FROM TEACHER";
            PreparedStatement pStmt = conn.prepareStatement(sql);

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

// 教員IDから教員を取得
    public Teacher findTeacherById(String teacherId) {
        try (Connection conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS)) {
            String sql = "SELECT * FROM TEACHER WHERE TEACHER_ID = ?";
            PreparedStatement pStmt = conn.prepareStatement(sql);
            pStmt.setString(1, teacherId);

            ResultSet rs = pStmt.executeQuery();

            if (rs.next()) {
                String teacherName = rs.getString("TEACHER_NAME");
                Teacher teacher = new Teacher(teacherId, teacherName);

                return teacher;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

}
