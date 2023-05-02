package test;

import dao.SubjectDAO;
import model.Subject;

public class SubjectDAOTest {
	public static void main(String[] args) {
		testAddSubject();//科目を作成
	}

//科目登録テスト
	public static void testAddSubject() {
		Subject subject = new Subject("0001", "数学");
		SubjectDAO dao = new SubjectDAO();
		int result = dao.addSubject(subject);

		if(result == 1) {
			System.out.println("testAddSubject:成功したぜ");
		} else {
		System.out.println("testAddSubject:失敗したぜ");
		}
	}
}
