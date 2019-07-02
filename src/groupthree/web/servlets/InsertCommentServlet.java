package groupthree.web.servlets;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import util.DBConnectionUtils;
import util.JSONResponse;

import javax.json.JsonObject;
import javax.persistence.criteria.CriteriaBuilder;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class InsertCommentServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection conn = DBConnectionUtils.getConnectionFromSrcFolder("connection.properties")) {
            int parentId = 0;
            String content = req.getParameter("newComment");
            int articleID = Integer.parseInt(req.getParameter("articleId"));
            int userId = Integer.parseInt(req.getParameter("userId"));
            if (req.getParameter("parentId").length() != 0){
                parentId = Integer.parseInt(req.getParameter("parentId"));
            }
            Calendar cal = Calendar.getInstance();
            Timestamp timestamp = new Timestamp(cal.getTimeInMillis());

            String userName = req.getParameter("userName");
            String userAvatar = req.getParameter("userAvatar");
            Comment comment = new Comment(parentId, content, timestamp, articleID, userId, true, userName, userAvatar);
            CommentDAO.insertComment(comment, conn);
            //resp.sendRedirect("./articleDetail?articleId=" + articleID);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("comment", Comment.toJson(comment));
            JSONResponse.send(resp, jsonObject);
        } catch (
                SQLException e) {
            resp.setStatus(500);
            e.printStackTrace();
            throw new ServletException("Database access error!", e);

        }
    }
}
