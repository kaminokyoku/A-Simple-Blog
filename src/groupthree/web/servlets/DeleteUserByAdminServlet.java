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
import java.util.ArrayList;
import java.util.List;

public class DeleteUserByAdminServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection conn = DBConnectionUtils.getConnectionFromSrcFolder("connection.properties")) {
            int userId = Integer.parseInt(req.getParameter("userId"));

            List<Comment> commentsByCurrentUser = CommentDAO.getCommentsByUserID(userId, conn);

            for (Comment c : commentsByCurrentUser) {
                List<Comment> commentsNested = CommentDAO.getCommentsByParentID(c.getId(), conn);
                for (Comment nestedComment : commentsNested) {
                    CommentDAO.deleteComment(nestedComment.getId(), conn);
                }
                CommentDAO.deleteComment(c.getId(), conn);
            }

            List<Article> articlesByCurrentUser = ArticleDAO.getArticlesById(userId, conn);
            for (Article article : articlesByCurrentUser) {
                List<Comment> commentsUnderArticleByCurrentuser = CommentDAO.getCommentsByArticleID(article.getArticleId(), conn);
                for (Comment comment : commentsUnderArticleByCurrentuser) {
                    CommentDAO.deleteComment(comment.getId(), conn);
                }
                ArticleDAO.deleteArticle(article.getArticleId(), conn);
            }

            UserDAO.deleteUser(userId, conn);
            req.getSession().invalidate();
            resp.sendRedirect("./index.jsp");
        } catch (
                SQLException e) {
            resp.setStatus(500);
            e.printStackTrace();
            throw new ServletException("Database access error!", e);

        }

    }
}
