package groupthree.web.servlets;

import util.DBConnectionUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class EditArticleServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        /*try (Connection conn = DBConnectionUtils.getConnectionFromSrcFolder("connection.properties")) {
            String articleId = req.getParameter("articleId");
            Article article = ArticleDAO.getArticleByArticleId(Integer.parseInt(articleId), conn);

            req.setAttribute("article", Article.toJson(article));
            req.getRequestDispatcher("./rich-editor.jsp").forward(req, resp);
        } catch (
                SQLException e) {

            resp.setStatus(500);
            e.printStackTrace();
            throw new ServletException("Database access error!", e);

        }*/

        String articleId = req.getParameter("articleId");
        req.setAttribute("articleId", articleId);
        req.getRequestDispatcher("./rich-editor.jsp").forward(req, resp);
    }
}
