package model;

//何もなしはIDとName
public class Student {
	private String studentId;
	private String studentName;

public Student(String studentId, String studentName) {
		this.studentId = studentId;
		this.studentName = studentName;
	}

public String getStudentId() {return studentId;}
public String getStudentName() {return studentName;}
public void setStudentId(String studentId) {this.studentId = studentId;}
public void setStudentName(String studentName) {this.studentName = studentName;}
}
