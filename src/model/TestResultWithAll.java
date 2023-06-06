package model;

public class TestResultWithAll {
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
	private int deleted;

	private String studentPassword;
	private String studentMail;
	private String studentName;

	public TestResultWithAll(int testResultId, int testId, String studentId, int score, int testRound
			, String testName, String subjectId, int criterionId, int fullScore, double multiplier, int deleted
			, String studentPassword, String studentMail, String studentName) {
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
		this.deleted = deleted;
		this.studentPassword = studentPassword;
		this.studentMail = studentMail;
		this.studentName = studentName;
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
	public int getDeleted() {return deleted;}
	public String getStudentPassword() {return studentPassword;}
	public String getStudentMail() {return studentMail;}
	public String getStudentName() {return studentName;}

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
    public void setDeleted(int deleted) {
        this.deleted = deleted;
    }
    public void setStudentPassword(String studentPassword) {
    	this.studentPassword = studentPassword;
    }
    public void setStudentMail(String studentMail) {
    	this.studentMail = studentMail;
    }
    public void setStudentName(String studentName) {
    	this.studentName = studentName;
    }
}

