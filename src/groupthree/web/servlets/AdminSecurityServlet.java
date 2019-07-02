package groupthree.web.servlets;

import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminSecurityServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));

        switch (id) {
            case 1:
                req.getRequestDispatcher("/WEB-INF/admin-dashboard.jsp").forward(req, resp);
                break;
            case 2:
                req.getRequestDispatcher("/WEB-INF/admin-user.jsp").forward(req, resp);
                break;
            case 3:
                req.getRequestDispatcher("/WEB-INF/admin-Article.jsp").forward(req, resp);
                break;
            case 4:
                req.getRequestDispatcher("/WEB-INF/admin-comment.jsp").forward(req, resp);
                break;

        }

    }
}
