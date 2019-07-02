package groupthree.web.servlets;

import util.DBConnectionUtils;
import util.VerifyUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class EditUserProfileServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection conn = DBConnectionUtils.getConnectionFromSrcFolder("connection.properties")) {

            String username = req.getParameter("username");
            int userId = Integer.parseInt(req.getParameter("userId"));
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
            System.out.println("avatarName should be changed to: " + avatarName);
            String password = req.getParameter("password");

            User user = AuthenticatorUtils.createAccount(username, birth_year, birth_month, birth_date, country, description, firstName, lastName, email, avatarName, password);
            UserDAO.updateUser(user,conn, userId);
            HttpSession sess = req.getSession(true);

            User userUpdated = UserDAO.getUserByuserId(conn, userId);
            sess.setAttribute("testSuccess", "Update Success!");
            sess.setAttribute("currentUser", userUpdated);
            resp.sendRedirect("./settings_profile.jsp");


        } catch (SQLException e) {
            resp.setStatus(500);
            e.printStackTrace();
            throw new ServletException("Database access error!", e);

        }

    }
}
