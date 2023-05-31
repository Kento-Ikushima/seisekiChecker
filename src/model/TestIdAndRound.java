package model;

public class TestIdAndRound {
	private int testId;
	private int testRound;

	public TestIdAndRound(int testId, int testRound) {
		this.testId = testId;
		this.testRound = testRound;
	}

	public int getTestId() {return testId;}
	public int getTestRound() {return testRound;}

    public void setTestId(int testId) {
    	this.testId = testId;
    }
    public void setTestRound(int testRound) {
    	this.testRound = testRound;
    }
}
