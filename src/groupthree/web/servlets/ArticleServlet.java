package groupthree.web.servlets;

import org.json.simple.JSONArray;
import util.DBConnectionUtils;
import util.JSONResponse;

import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ArticleServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try (Connection conn = DBConnectionUtils.getConnectionFromSrcFolder("connection.properties")) {

            List<Article> articles;
            String userId = req.getParameter("userId");
            if (userId != null) {
                articles = ArticleDAO.getArticlesById(Integer.parseInt(userId), conn);
            } else {
                articles = ArticleDAO.getAllArticles(conn);
            }
            JSONArray myJSON = new JSONArray();
            for (int i = 0; i<articles.size(); i++) {
                myJSON.add(Article.toJson(articles.get(i)));
            }
            JSONResponse.send(resp,myJSON);

        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }


}
