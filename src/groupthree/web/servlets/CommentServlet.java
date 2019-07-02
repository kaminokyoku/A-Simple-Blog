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

public class CommentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection conn = DBConnectionUtils.getConnectionFromSrcFolder("connection.properties")) {
            int articleId = Integer.parseInt(req.getParameter("articleId"));
            List<Comment> comments = CommentDAO.getCommentsByArticleID(articleId, conn);

            JSONArray myJSON = new JSONArray();
            for (int i = 0; i<comments.size(); i++) {
                myJSON.add(Comment.toJson(comments.get(i)));
            }
            JSONResponse.send(resp,myJSON);
        } catch (
                SQLException e) {
            resp.setStatus(500);
            e.printStackTrace();
            throw new ServletException("Database access error!", e);

        }
    }

}
