package model;

public class AverageCriterionNScore {
	private String subjectId;
	private int criterionId;
	private int averageCriterionNScore;

	public AverageCriterionNScore(String subjectId, int criterionId, int averageCriterionNScore) {
		this.subjectId = subjectId;
		this.criterionId = criterionId;
		this.averageCriterionNScore = averageCriterionNScore;
	}

	public String getSubjectId() {return subjectId;}
	public int getCriterionId() {return criterionId;}
	public int getAverageCriterionNScore() {return averageCriterionNScore;}

	public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }
	public void setCriterionId(int criterionId) {
        this.criterionId = criterionId;
    }
	public void setAverageCriterionNScore(int averageCriterionNScore) {
        this.averageCriterionNScore = averageCriterionNScore;
    }
}
