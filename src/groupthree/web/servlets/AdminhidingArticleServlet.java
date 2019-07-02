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

public class AdminhidingArticleServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try (Connection conn = DBConnectionUtils.getConnectionFromSrcFolder("connection.properties")) {
            int articleId = Integer.parseInt(req.getParameter("articleId"));
            boolean value = Boolean.parseBoolean(req.getParameter("isShow"));

            ArticleDAO.changeVisibility(articleId, value, conn);
            System.out.println("This value be put in data base " + articleId + " " + !value);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("isShow", value);
            JSONResponse.send(resp, jsonObject);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
