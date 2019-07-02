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
import java.util.List;

public class DeleteCommentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection conn = DBConnectionUtils.getConnectionFromSrcFolder("connection.properties")) {
            int commentId = Integer.parseInt(req.getParameter("commentId"));

            List<Comment> commentsNested = CommentDAO.getCommentsByParentID(commentId, conn);

            for (Comment c : commentsNested) {
               CommentDAO.deleteComment(c.getId(), conn);
            }

            CommentDAO.deleteComment(commentId, conn);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("deleted", "yes");
            JSONResponse.send(resp, jsonObject);
            //resp.sendRedirect("./index.jsp");
        } catch (
                SQLException e) {
            resp.setStatus(500);
            e.printStackTrace();
            throw new ServletException("Database access error!", e);

        }

    }
}
