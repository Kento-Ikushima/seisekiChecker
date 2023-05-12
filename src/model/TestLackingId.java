package model;

public class TestLackingId {
	private int testId;
	private String testName;
	private String subjectId;
	private int criterionId;
	private int fullScore;
	private double multiplier;

	public TestLackingId(String testName, String subjectId, int criterionId, int fullScore, double multiplier) {
		this.testName = testName;
		this.subjectId = subjectId;
		this.criterionId = criterionId;
		this.fullScore = fullScore;
		this.multiplier = multiplier;
	}


	public int getTestId() {return testId;}
	public String getTestName() {return testName;}
	public String getSubjectId() {return subjectId;}
	public int getCriterionId() {return criterionId;}
	public int getFullScore() {return fullScore;}
	public double getMultiplier() {return multiplier;}

    public void setTestId(int testId) {
        this.testId = testId;
    }
    public void setTestName(String testName) {
        this.testName = testName;
    }
    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }
    public void setCriterionId(int criterionId) {
        this.criterionId = criterionId;
    }
    public void setFullScore(int fullScore) {
        this.fullScore = fullScore;
    }
    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
    }
}
