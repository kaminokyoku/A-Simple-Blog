package groupthree.web.servlets;

import util.DBConnectionUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class MyArticleServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        /*try (Connection conn = DBConnectionUtils.getConnectionFromSrcFolder("connection.properties")) {

            String id = req.getParameter("id");
            int idNum = Integer.parseInt(id);
            Article article = ArticleDAO.getArticleById(idNum, conn);
            req.setAttribute("article", article);
            req.getRequestDispatcher("./myarticle.jsp").forward(req, resp);
        } catch (SQLException e) {
            throw new ServletException(e);
        }*/
    }
}
