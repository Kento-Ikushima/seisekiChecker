package model;

public class CriterionNEvaluation {
	private String subjectId;
	private String studentId;
	private int criterionId;
	private String criterionNEvaluation;

	public CriterionNEvaluation(String subjectId, String studentId, int criterionId, String criterionNEvaluation) {
		this.subjectId = subjectId;
		this.studentId = studentId;
		this.criterionId = criterionId;
		this.criterionNEvaluation = criterionNEvaluation;
	}

	public String getSubjectId() {return subjectId;}
	public String getStudentId() {return studentId;}
	public int getCriterionId() {return criterionId;}
	public String getCriterionNEvaluation() {return criterionNEvaluation;}

	public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }
	public void setStudentId(String studentId) {
    	this.studentId = studentId;
    }
	public void setCriterionId(int criterionId) {
        this.criterionId = criterionId;
    }
	public void setCriterionNEvaluation(String criterionNEvaluation) {
        this.criterionNEvaluation = criterionNEvaluation;
    }
}
