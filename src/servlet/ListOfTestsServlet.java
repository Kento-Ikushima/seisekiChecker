package servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import dao.CriterionDAO;
import dao.SubjectDAO;
import dao.TestDAO;
import model.Criterion;
import model.Subject;
import model.Test;


@WebServlet("/ListOfTestsServlet")
public class ListOfTestsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;



    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	//これがrequest.getParameterが妥当かわかってない
   		HttpSession session = request.getSession();
        String teacherId = (String) session.getAttribute("teacherId");

        //DAOの情報を受け取る
        TestDAO testDAO = new TestDAO();
        List<Test> testList = null;
		try {
			testList = testDAO.findTestsByTeacherId(teacherId);
		} catch (SQLException e) {
			e.printStackTrace();
		}

        //科目IDに対応する科目情報を取得(subjectIdをどうやって召喚するか不明 chatGPT参照）
        SubjectDAO subjectDAO = new SubjectDAO();
        List<Subject> subjectList = new ArrayList<>();
		for (Test test : testList) {
        	String subjectId = test.getSubjectId();
        	Subject subject = subjectDAO.findSubjectById(subjectId);
        	subjectList .add(subject);
        }


        //観点iDに対応する観点名を取得
        CriterionDAO criterionDAO = new CriterionDAO();
        List<Criterion> criterionList = criterionDAO.findAllCriterions();

        //リクエストパラメータを設定
        request.setAttribute("testList", testList);
        request.setAttribute("subjectList", subjectList);
        request.setAttribute("criterionList", criterionList);

        //詳細画面へフォワード
        RequestDispatcher dispatcher = request.getRequestDispatcher("/WEB-INF/jsp/testList.jsp");
        dispatcher.forward(request, response);
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	// TODO Auto-generated method stub
		doGet(request, response);
    }
}

