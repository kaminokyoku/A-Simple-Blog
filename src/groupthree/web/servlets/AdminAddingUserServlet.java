package groupthree.web.servlets;

import util.DBConnectionUtils;
import util.VerifyUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class AdminAddingUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection conn = DBConnectionUtils.getConnectionFromSrcFolder("connection.properties")) {

            String username = req.getParameter("username");
            String date = req.getParameter("date");
            int birth_year = Integer.parseInt(date.substring(0, 4));
            int birth_month = Integer.parseInt(date.substring(5, 7));
            int birth_date = Integer.parseInt(date.substring(8));
            String country = req.getParameter("country");
            String description = req.getParameter("description");
            String firstName = req.getParameter("firstname");
            String lastName = req.getParameter("lastname");
            String email = req.getParameter("email");
            String avatarName = req.getParameter("avatarPath");
            String password = req.getParameter("password");


            String errorString = null;

            boolean valid = true;


            if (valid) {
                String gRecaptchaResponse = req.getParameter("g-recaptcha-response");
                //System.out.println("gRecaptchaResponse=" + gRecaptchaResponse);
                valid = VerifyUtils.verify(gRecaptchaResponse);
                if (!valid) {
                    errorString = "Captcha invalid!";
                }
            }
            if (!valid) {
                req.setAttribute("errorString", errorString);
                RequestDispatcher dispatcher = //
                        req.getServletContext().getRequestDispatcher("/signup-admin.jsp");

                dispatcher.forward(req, resp);
                return;
            } else {
                User user = AuthenticatorUtils.createAccount(username, birth_year, birth_month, birth_date, country, description, firstName, lastName, email, avatarName, password);
                UserDAO.insertUser(user, conn);
                //send the user an email
                SendEmail.sendingNewUserEmailAndPassword(email, username, password);

                req.getRequestDispatcher("/WEB-INF/admin-user.jsp").forward(req, resp);
            }

        } catch (SQLException e) {
            resp.setStatus(500);
            e.printStackTrace();
            throw new ServletException("Database access error!", e);

        }

    }
}
