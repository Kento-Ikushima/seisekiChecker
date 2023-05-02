package model;

import dao.TeacherDAO;

public class TeacherLoginBO {
  public boolean execute(TeacherLogin teacherLogin) {
    TeacherDAO dao = new TeacherDAO();
    TeacherAccount teacherAccount = dao.findByTeacherLogin(teacherLogin);
    return teacherAccount != null;    //null->false, else->trueって意味
  }
}