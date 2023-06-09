package servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebServlet("/TeacherHomeServlet")
public class TeacherHomeServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session != null && session.getAttribute("teacherId") != null) {
            request.getRequestDispatcher("/WEB-INF/jsp/teacherLoginOK.jsp").forward(request, response);
        } else {
            response.sendRedirect("TeacherLoginServlet");
        }
    }
}
