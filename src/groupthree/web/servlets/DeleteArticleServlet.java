package groupthree.web.servlets;

import org.json.simple.JSONObject;
import util.DBConnectionUtils;
import util.JSONResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class DeleteArticleServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection conn = DBConnectionUtils.getConnectionFromSrcFolder("connection.properties")) {

            String articleId = req.getParameter("articleId");
            boolean symbol = ArticleDAO.deleteArticle(Integer.parseInt(articleId), conn);

            JSONObject jsonObject = new JSONObject();
            if (symbol) {
                jsonObject.put("deleted", "yes");
            } else {
                jsonObject.put("deleted", "no");
            }
            JSONResponse.send(resp, jsonObject);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
