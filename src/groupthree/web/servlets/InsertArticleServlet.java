package groupthree.web.servlets;

import util.DBConnectionUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class InsertArticleServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection conn = DBConnectionUtils.getConnectionFromSrcFolder("connection.properties")) {
            String articleTitle = req.getParameter("articleTitle");
            String articleContent = req.getParameter("articleContent");
            String articleId = req.getParameter("articleId");
            String userId = req.getParameter("userId");
            String userArticleDate = req.getParameter("userArticleDate");
            System.out.println("articleTitle:" + articleTitle);
            System.out.println("articleId:" + articleId);
            System.out.println("userId:" + userId);
            System.out.println("userArticleDate" + userArticleDate);


            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(" HH:mm:ss");
            LocalDateTime localDateTime = LocalDateTime.now();
            String date = userArticleDate + localDateTime.format(dateTimeFormatter);

            Article article = new Article(Integer.parseInt(articleId), articleTitle, date, articleContent, true, Integer.parseInt(userId));
            ArticleDAO.updateArticle(article,conn);
            //TODO getRequestDispather to user-page if refresh the page, will add one more article.
            //req.getRequestDispatcher("./user-page.jsp").forward(req, resp);
            resp.sendRedirect("./user-page.jsp");
        } catch (
                SQLException e) {

            resp.setStatus(500);
            e.printStackTrace();
            throw new ServletException("Database access error!", e);

        }


    }
}
