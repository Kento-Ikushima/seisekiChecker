package model;

public class Evaluation {
	private String subjectId;
	private String studentId;
	private int evaluation;

	public Evaluation(String subjectId, String studentId, int evaluation) {
		this.subjectId = subjectId;
		this.studentId = studentId;
		this.evaluation = evaluation;
	}

	public String getSubjectId() {return subjectId;}
	public String getStudentId() {return studentId;}
	public int getEvaluation() {return evaluation;}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}
	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}
	public void setEvaluation(int evaluation) {
		this.evaluation = evaluation;
	}
}
