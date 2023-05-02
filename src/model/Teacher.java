package model;

//何もなしはIDとName
public class Teacher {
	private String teacherId;
	private String teacherName;

public Teacher(String teacherId, String teacherName) {
		this.teacherId = teacherId;
		this.teacherName = teacherName;
	}

public String getTeacherId() {return teacherId;}
public String getTeacherName() {return teacherName;}
public void setTeacherId(String teacherId) {this.teacherId = teacherId;}
public void setTeacherName(String teacherName) {this.teacherName = teacherName;}
}
