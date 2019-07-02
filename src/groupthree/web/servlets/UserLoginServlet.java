package groupthree.web.servlets;

import util.DBConnectionUtils;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class UserLoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().invalidate();
        resp.sendRedirect("./index.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection conn = DBConnectionUtils.getConnectionFromSrcFolder("connection.properties")) {
            String username = req.getParameter("logininfo");
            String password = req.getParameter("password");
            User currentUser = UserDAO.getUserByUsername(conn, username);

            if (currentUser != null && (AuthenticatorUtils.authenticate(currentUser, password))) {
                HttpSession sess = req.getSession(true);

                sess.setAttribute("currentUser", currentUser);
                resp.sendRedirect("./index.jsp");
            } else {
                resp.sendRedirect("./login.jsp");
            }

        } catch (
                SQLException e) {

            resp.setStatus(500);
            e.printStackTrace();
            throw new ServletException("Database access error!", e);

        }
    }
}
