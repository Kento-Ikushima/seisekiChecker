package model;

public class FinalResult {
		private String subjectId;
		private String studentId;
		private int finalResult;

		public FinalResult(String subjectId, String studentId, int finalResult) {
			this.subjectId = subjectId;
			this.studentId = studentId;
			this.finalResult = finalResult;
		}

		public String getSubjectId() {return subjectId;}
		public String getStudentId() {return studentId;}
		public int getFinalResult() {return finalResult;}

		public void setSubjectId(String subjectId) {
			this.subjectId = subjectId;
		}
		public void setStudentId(String studentId) {
			this.studentId = studentId;
		}
		public void setFinalResult(int finalResult) {
			this.finalResult = finalResult;
		}
	}
