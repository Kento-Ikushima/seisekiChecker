package model;

public class Criterion {
	private int criterionId;
	private String criterionName;

	public Criterion (int criterionId, String criterionName) {
		this.criterionId = criterionId;
		this.criterionName = criterionName;
	}

	public int getCriterionId() {return criterionId;}
	public String getCriterionName() {return criterionName;}

    public void setCriterionId(int criterionId) {
        this.criterionId = criterionId;
    }
    public void setCriterionName(String criterionName) {
        this.criterionName = criterionName;
    }
}
