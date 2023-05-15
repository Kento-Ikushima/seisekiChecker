package model;

public class TestResult {

	private int testResultId;
	private int testId;
	private String studentId;
	private int score;

	public TestResult(int testResultId, int testId, String studentId, int score) {
		this.testResultId = testResultId;
		this.testId = testId;
		this.studentId = studentId;
		this.score = score;
	}


	public int getTestResultId() {return testResultId;}
	public int getTestId() {return testId;}
	public String getStudentId() {return studentId;}
	public int getScore() {return score;}

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

}
