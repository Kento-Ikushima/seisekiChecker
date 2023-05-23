package model;

public class TestResultAndTest {
	private int testResultId;
	private int testId;
	private String studentId;
	private int score;
	private int testRound;

	private String testName;
	private String subjectId;
	private int criterionId;
	private int fullScore;
	private double multiplier;

	public TestResultAndTest(int testResultId, int testId, String studentId, int score, int testRound
			, String testName, String subjectId, int criterionId, int fullScore, double multiplier) {
		this.testResultId = testResultId;
		this.testId = testId;
		this.studentId = studentId;
		this.score = score;
		this.testRound = testRound;
		this.testName = testName;
		this.subjectId = subjectId;
		this.criterionId = criterionId;
		this.fullScore = fullScore;
		this.multiplier = multiplier;
	}


	public int getTestResultId() {return testResultId;}
	public int getTestId() {return testId;}
	public String getStudentId() {return studentId;}
	public int getScore() {return score;}
	public int getTestRound() {return testRound;}
	public String getTestName() {return testName;}
	public String getSubjectId() {return subjectId;}
	public int getCriterionId() {return criterionId;}
	public int getFullScore() {return fullScore;}
	public double getMultiplier() {return multiplier;}

    public void setTestResultId(int testResultId) {
    	this.testResultId = testResultId;
    }
    public void setTestId(int testId) {
    	this.testId = testId;
    }
    public void setStudentId(String studentId) {
    	this.studentId = studentId;
    }
    public void setScore(int score) {
    	this.score = score;
    }
    public void setTestRound(int testRound) {
    	this.testRound = testRound;
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

