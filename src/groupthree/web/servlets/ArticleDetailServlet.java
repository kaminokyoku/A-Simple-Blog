package groupthree.web.servlets;

import util.DBConnectionUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class ArticleDetailServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {


        try (Connection conn = DBConnectionUtils.getConnectionFromSrcFolder("connection.properties")) {
            int articleId = Integer.parseInt(req.getParameter("articleId"));
            Article article = ArticleDAO.getArticleByArticleId(articleId, conn);
            req.setAttribute("article", article);
            req.getRequestDispatcher("./article-detail.jsp").forward(req, resp);
        } catch (
                SQLException e) {

            resp.setStatus(500);
            e.printStackTrace();
            throw new ServletException("Database access error!", e);

        }
    }
}
