package test;

import static org.junit.Assert.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import dao.SubjectsInChargeDAO;
import model.Subject;

public class SubjectsInChargeDAOTest {

    private Connection conn;
    private SubjectsInChargeDAO dao;

    @Before
    public void setUp() throws Exception {
        // テスト用データベースに接続する
    	final String JDBC_URL = "jdbc:h2:tcp://localhost/~/seisekiChecker";
        final String DB_USER = "sa";
        final String DB_PASS = "";
        conn = DriverManager.getConnection(JDBC_URL, DB_USER, DB_PASS);

        // テスト用データを準備する
        PreparedStatement ps = conn.prepareStatement("INSERT INTO subjects_in_charge (teacher_id, subject_id) VALUES (?, ?)");
        ps.setString(1, "ikushima");
        ps.setString(2, "0006");
        ps.executeUpdate();

        dao = new SubjectsInChargeDAO();
    }

    @After
    public void tearDown() throws Exception {
        // テスト用データを削除する
        PreparedStatement ps = conn.prepareStatement("DELETE FROM subjects_in_charge WHERE teacher_id = 'ikushima'");
        ps.executeUpdate();
        conn.close();
    }

    @Test
    public void testDeleteSubjects() {
        // テスト前のデータ数を確認する
        List<Subject> before = dao.findSubjectsByTeacherId("ikushima");
        int beforeSize = before.size();

        // 削除するデータを準備する
        List<Subject> deleteList = new ArrayList<>();
        deleteList.add(new Subject("0006", null));

        // テスト対象メソッドを実行する
        boolean result = dao.deleteSubjects(deleteList);

        // テスト後のデータ数を確認する
        List<Subject> after = dao.findSubjectsByTeacherId("ikushima");
        int afterSize = after.size();

        // テスト結果を検証する
        assertTrue(result);
        assertEquals(1, beforeSize);
        assertEquals(0, afterSize);
    }

}
