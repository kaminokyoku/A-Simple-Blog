package groupthree.web.servlets;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

public class CommentDAO {

    public static List<Comment> getAllComments(Connection conn) throws SQLException {
        List<Comment> comments = new ArrayList<>();
        try (Statement stmt = conn.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM comment")) {
                while (rs.next()) {
                    Comment comment = getCommentFromResultSet(rs);
                    comments.add(comment);
                }
            }
        }
        return comments;
    }


    public static List<Comment> getCommentsByArticleID(int articleID, Connection conn) {
        List<Comment> comments = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM comment WHERE articleId = ?")) {
            stmt.setInt(1, articleID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Comment comment = getCommentFromResultSet(rs);
                    comments.add(comment);

                    List<Comment> commentsNested = getCommentsByParentID(comment.getId(), conn);
                    if (!commentsNested.isEmpty()) {
                        comments.addAll(commentsNested);
                    }
                }
            }
        } catch (SQLException e) {
            return null;
        }

        for (int i = 0; i < comments.size(); i++) {
            for (int j = i + 1; j < comments.size(); j++) {
                if (comments.get(i).getId() == comments.get(j).getId()) {
                    comments.remove(comments.get(j));
                }
            }
        }
        return comments;
    }

    public static List<Comment> getCommentsByParentID(int parentID, Connection conn) {
        List<Comment> comments = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM comment WHERE parentId = ?")) {
            stmt.setInt(1, parentID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Comment comment = getCommentFromResultSet(rs);
                    comments.add(comment);
                }
            }
        } catch (SQLException e) {
            return null;
        }
        return comments;
    }

    public static List<Comment> getCommentsByUserID(int userID, Connection conn) {
        List<Comment> comments = new ArrayList<>();
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM comment WHERE userID= ?")) {
            stmt.setInt(1, userID);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Comment comment = getCommentFromResultSet(rs);
                    comments.add(comment);
                }
            }
        } catch (SQLException e) {
            return null;
        }
        return comments;
    }

    public static boolean deleteComment(int id, Connection conn) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "DELETE FROM comment WHERE commentId = ?")) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return (rowsAffected == 1);
        }
    }

    public static boolean insertComment(Comment comment, Connection conn) throws SQLException {

        try (PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO comment (parentId, content, date, articleId, userId, isShown, userName, userAvatar) VALUES (?, ?, ? , ?, ?, ?, ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            stmt.setInt(1, comment.getParentID());
            stmt.setString(2, comment.getContent());
            stmt.setTimestamp(3, comment.getDate());
            stmt.setInt(4, comment.getArticleId());
            stmt.setInt(5, comment.getUserId());
            stmt.setBoolean(6, comment.isShown());
            stmt.setString(7, comment.getUserName());
            stmt.setString(8, comment.getUserAvatar());
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                return false;
            }

            try (ResultSet keys = stmt.getGeneratedKeys()) {
                keys.next();
                int id = keys.getInt(1);
                comment.setId(id);
                return true;
            }
        }
    }

    private static Comment getCommentFromResultSet(ResultSet rs) throws SQLException {
        return new Comment(
                rs.getInt(1),
                rs.getInt(2),
                rs.getString(3),
                rs.getTimestamp(4),
                rs.getInt(5),
                rs.getInt(6),
                rs.getBoolean(7),
                rs.getString(8),
                rs.getString(9)
        );
    }


    public static boolean changeVisibility(int id, boolean isShown, Connection conn) throws SQLException {

        Comment comment = null;
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM comment WHERE commentId = ?"
        )) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    comment = getCommentFromResultSet(rs);
                }
            }
        }

        if (comment.isShown()) {
            isShown = false;
        } else {
            isShown = true;
        }


        try (PreparedStatement stmt = conn.prepareStatement(
                "UPDATE comment SET isShown = ? WHERE commentId = ?")) {
            stmt.setBoolean(1, isShown);
            stmt.setInt(2, id);
            int rowsAffected = stmt.executeUpdate();
            return (rowsAffected == 1);
        }
    }
}
