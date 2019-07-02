package groupthree.web.servlets;

import org.json.simple.JSONArray;
import util.DBConnectionUtils;
import util.JSONResponse;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class SearchServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection conn = DBConnectionUtils.getConnectionFromSrcFolder("connection.properties")) {
            List<Article> articles;
            String searchType = req.getParameter("searchType");
            String keyword = req.getParameter("keyword");
            String sortType = req.getParameter("sortType");
            System.out.println("searchType=" + searchType);
            System.out.println("keyword=" + keyword);
            System.out.println("sortType=" + sortType);
            articles = ArticleDAO.getArticlesByParameter(searchType, keyword, sortType, conn);

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
