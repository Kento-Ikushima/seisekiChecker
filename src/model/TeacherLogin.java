package model;

//LoginはIDとPass
public class TeacherLogin {
	private String teacherId;
	private String teacherPassword;
	public TeacherLogin(String teacherId, String teacherPassword) {
		this.teacherId = teacherId;
		this.teacherPassword = teacherPassword;
	}
	public String getTeacherId() {return teacherId;}
	public String getTeacherPassword() {return teacherPassword;}
}
