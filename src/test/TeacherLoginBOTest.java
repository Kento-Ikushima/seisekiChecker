package test;

import model.TeacherLogin;
import model.TeacherLoginBO;

public class TeacherLoginBOTest {
  public static void main(String[] args) {
    testExecute1(); // ログイン成功のテスト
    testExecute2(); // ログイン失敗のテスト
  }

  public static void testExecute1() {
    TeacherLogin teacherLogin = new TeacherLogin("ikushima", "1111");
    TeacherLoginBO bo = new TeacherLoginBO();
    boolean result = bo.execute(teacherLogin);
    if (result) {
      System.out.println("testExcecute1:成功しました");
    } else {
      System.out.println("testExcecute1:失敗しました");
    }
  }

  public static void testExecute2() {
    TeacherLogin teacherLogin = new TeacherLogin("ikushima", "12345");
    TeacherLoginBO bo = new TeacherLoginBO();
    boolean result = bo.execute(teacherLogin);
    if (!result) {
      System.out.println("testExcecute2:成功しました");
    } else {
      System.out.println("testExcecute2:失敗しました");
    }
  }
}