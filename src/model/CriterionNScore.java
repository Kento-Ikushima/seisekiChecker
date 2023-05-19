package model;

public class CriterionNScore {
	private String subjectId;
	private String studentId;
	private int criterionId;
	private int criterionNScore;

	public CriterionNScore(String subjectId, String studentId, int criterionId, int criterionNScore) {
		this.subjectId = subjectId;
		this.studentId = studentId;
		this.criterionId = criterionId;
		this.criterionNScore = criterionNScore;
	}

	public String getSubjectId() {return subjectId;}
	public String getStudentId() {return studentId;}
	public int getCriterionId() {return criterionId;}
	public int getCriterionNScore() {return criterionNScore;}

	public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }
	public void setStudentId(String studentId) {
    	this.studentId = studentId;
    }
	public void setCriterionId(int criterionId) {
        this.criterionId = criterionId;
    }
	public void setCriterionNScore(int criterionNScore) {
        this.criterionNScore = criterionNScore;
    }
}
