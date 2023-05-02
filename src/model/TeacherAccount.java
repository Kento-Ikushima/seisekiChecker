package model;

//Accountは全データ
public class TeacherAccount {
	private String teacherId;
	private String teacherPassword;
	private String teacherMail;
	private String teacherName;

	public TeacherAccount(String teacherId, String teacherPassword, String teacherMail, String teacherName) {
		this.teacherId = teacherId;
		this.teacherPassword = teacherPassword;
		this.teacherMail = teacherMail;
		this.teacherName = teacherName;
	}

	public String getTeacherId() {return teacherId;}
	public String getTeacherPassword() {return teacherPassword;}
	public String getTeacherMail() {return teacherMail;}
	public String getTeacherName() {return teacherName;}



}
