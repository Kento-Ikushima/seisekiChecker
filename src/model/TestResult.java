package model;

public class TestResult {

	private int testResultId;
	private int testId;
	private String studentId;
	private int score;
	private int testRound;

	public TestResult(int testResultId, int testId, String studentId, int score, int testRound) {
		this.testResultId = testResultId;
		this.testId = testId;
		this.studentId = studentId;
		this.score = score;
		this.testRound = testRound;
	}


	public int getTestResultId() {return testResultId;}
	public int getTestId() {return testId;}
	public String getStudentId() {return studentId;}
	public int getScore() {return score;}
	public int getTestRound() {return testRound;}

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
}
