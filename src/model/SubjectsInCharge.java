package model;

public class SubjectsInCharge {
    private int subjectsInChargeId;
    private String teacherId;
    private String subjectId;

    public SubjectsInCharge(String teacherId, String subjectId) {
        this.teacherId = teacherId;
        this.subjectId = subjectId;
    }

    public int getSubjectsInChargeId() {
        return subjectsInChargeId;
    }

    public void setSubjectsInChargeId(int subjectsInChargeId) {
        this.subjectsInChargeId = subjectsInChargeId;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

}
