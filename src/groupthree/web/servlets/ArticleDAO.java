package groupthree.web.servlets;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ArticleDAO {


    public static boolean insertArticle(Article article, Connection conn) throws SQLException {

        try (PreparedStatement stmt = conn.prepareStatement(
                "INSERT INTO article (title , date ,  content, isShown, userId) VALUES (?, ?, ? , ?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, article.getTitle());
           stmt.setString(2, article.getDate());
            stmt.setString(3, article.getContent());
            stmt.setBoolean(4, article.isShown());
            stmt.setInt(5, article.getUserId());
            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected == 0) {
                return false;
            }
            try (ResultSet keys = stmt.getGeneratedKeys()) {
                keys.next();
                int id = keys.getInt(1);
                article.setArticleId(id);
                return true;
            }
        }
    }

    /**
     * If a new article is created, insert it into the database. Otherwise, update the existed article.
     * @param article article to be inserted or updated.
     * @param conn
     * @return
     * @throws SQLException
     */
    public static boolean updateArticle(Article article, Connection conn) throws SQLException {

        System.out.println("update start");
        Article resultArticle = ArticleDAO.getArticleByArticleId(article.getArticleId(), conn);

        if (resultArticle != null) {
            System.out.println("article exists");
            try (PreparedStatement stmt = conn.prepareStatement(
                    "UPDATE article SET title = ?, date = ?, content = ? WHERE articleId = ?")){
                stmt.setString(1, article.getTitle());
                stmt.setString(2, article.getDate());
                stmt.setString(3, article.getContent());
                stmt.setInt(4, article.getArticleId());
                int rowsAffected = stmt.executeUpdate();
                return (rowsAffected == 1);
            }
        } else {
            System.out.println("article does not exist.");
            return ArticleDAO.insertArticle(article, conn);
        }
    }

    public static List<Article> getAllArticles(Connection conn) throws SQLException {
        List<Article> articles = new ArrayList<>();
        try (Statement stmt = conn.createStatement()) {
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM article ORDER BY date DESC")) {
                while (rs.next()) {
                    Article article = getArticleFromResultSet(rs);
                    articles.add(article);
                }
            }
        }
        return articles;
    }

    /**
     * Search articles by search type and sort type
     * @param searchType 1 means by title, 2 means by user name, 3 means by date
     * @param keyword search keyword
     * @param sortType 1 means by title, 2 means by user name, 3means by date
     * @param conn
     * @return
     * @throws SQLException
     */
    public static List<Article> getArticlesByParameter(String searchType, String keyword, String sortType, Connection conn) throws SQLException {

        List<Article> articles = new ArrayList<>();

        String sql = "";
        if (searchType.equals("1") && sortType.equals("1")) {
            sql = "SELECT * FROM article WHERE title LIKE ? ORDER BY title";
        }

        //TODO search by inner join. Next time may consider to keep some redundancy of data in order to increase efficiency.
        if (searchType.equals("1") && sortType.equals("2")) {
            sql = "SELECT * FROM article, user WHERE article.title LIKE ? AND article.userId = user.userId ORDER BY user.userName";
        }

        if (searchType.equals("1") && sortType.equals("3")) {
            sql = "SELECT * FROM article WHERE article.title LIKE ? ORDER BY date";
        }

        if (searchType.equals("2") && sortType.equals("1")) {
            sql = "SELECT * FROM article, user WHERE article.userId = user.userId AND user.userName LIKE ? ORDER BY article.title";
        }

        if (searchType.equals("2") && sortType.equals("2")) {
            sql = "SELECT * FROM article, user WHERE article.userId = user.userId AND user.userName LIKE ? ORDER BY user.userName";
        }

        if (searchType.equals("2") && sortType.equals("3")) {
            sql = "SELECT * FROM article, user WHERE article.userId = user.userId AND user.userName LIKE ? ORDER BY article.date";
        }

        if (searchType.equals("3") && sortType.equals("1")) {
            sql = "SELECT * FROM article WHERE date LIKE ? ORDER BY title";
        }

        if (searchType.equals("3") && sortType.equals("2")) {
            sql = "SELECT * FROM article, user WHERE article.date LIKE ? AND article.userId = user.userId ORDER BY user.userName";
        }

        if (searchType.equals("3") && sortType.equals("3")) {
            sql = "SELECT * FROM article WHERE date LIKE ? ORDER BY date";
        }

        try (PreparedStatement stmt = conn.prepareStatement(
                sql)) {
            stmt.setString(1, "%" + keyword + "%");
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    articles.add(getArticleFromResultSet(rs));
                }
            }
        }

        return articles;
    }

    /**
     * Search articles using pagination
     * @param offset the pointer
     * @param rowCount the records count
     * @param conn
     * @param showFutureArticle  show articles published in the future or not
     * @return
     * @throws SQLException
     */
    public static List<Article> getPaginationArticles(int offset, int rowCount, Connection conn, boolean showFutureArticle) throws SQLException {

        String sql;
        if (showFutureArticle) {
            sql =  "SELECT * FROM article ORDER BY date DESC LIMIT ?,?;";
        } else {
            sql = "SELECT * FROM article WHERE date < ? ORDER BY date DESC LIMIT ?,?;";
        }

            List<Article> articles = new ArrayList<>();
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {


                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                LocalDateTime localDateTime = LocalDateTime.now();
                String date = localDateTime.format(dateTimeFormatter);
                System.out.println("local date: " + date);
                if (showFutureArticle) {
                    stmt.setInt(1, offset);
                    stmt.setInt(2, rowCount);
                } else {
                    stmt.setString(1, date);
                    stmt.setInt(2, offset);
                    stmt.setInt(3, rowCount);
                }

                try (ResultSet rs = stmt.executeQuery()) {
                    while (rs.next()) {
                        Article article = getArticleFromResultSet(rs);
                        articles.add(article);
                    }
                }
            }

            return articles;
        }


    public static List<Article> getArticlesById(int id, Connection conn) throws SQLException{
        List<Article> articles = new ArrayList<>();

        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM article WHERE userId= ? ORDER BY date DESC")) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    articles.add(getArticleFromResultSet(rs));
                }
            }
        }
        return articles;
    }

    public static Article getArticleByArticleId(int id, Connection conn) throws SQLException{

        Article article = null;
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM article WHERE articleId= ?")) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    article = getArticleFromResultSet(rs);
                }
            }
        }
        return article;
    }

    public static boolean deleteArticle(int id, Connection conn) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "DELETE FROM article WHERE articleId = ?")) {
            stmt.setInt(1, id);
            int rowsAffected = stmt.executeUpdate();
            return (rowsAffected == 1);
        }
    }

    public static boolean editArticle(int id, String title, String content, Connection conn) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement(
                "UPDATE article SET title = ? AND content = ? WHERE articleId = ?")) {
            stmt.setString(1, title);
            stmt.setString(2, content);
            stmt.setInt(3, id);
            int rowsAffected = stmt.executeUpdate();
            return (rowsAffected == 1);
        }
    }

    private static Article getArticleFromResultSet(ResultSet rs) throws SQLException {
        return new Article(
                rs.getInt(1),
                rs.getString(2),
                rs.getString(3),
                rs.getString(4),
                rs.getBoolean(5),
                rs.getInt(6)
        );
    }


    public static boolean changeVisibility(int articleId, boolean isShown, Connection conn) throws SQLException {

        Article article = null;
        try (PreparedStatement stmt = conn.prepareStatement(
                "SELECT * FROM article WHERE articleId = ?"
        )){
            stmt.setInt(1, articleId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    article = getArticleFromResultSet(rs);
                }
            }
        }

        if (article.isShown()) {
            isShown = false;
        }else  {
            isShown = true;
        }


        try (PreparedStatement stmt = conn.prepareStatement(
                "UPDATE article SET isShown = ? WHERE articleId = ?")) {
            stmt.setBoolean(1, isShown);
            stmt.setInt(2, articleId);
            int rowsAffected = stmt.executeUpdate();
            return (rowsAffected == 1);
        }
    }
}
