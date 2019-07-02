package groupthree.web.servlets;

import util.DBConnectionUtils;
import util.Passwords;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;

public class ChangePasswordServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("Change pass here!!!!!");
        try (Connection conn = DBConnectionUtils.getConnectionFromSrcFolder("connection.properties")) {

            String confirmPassword = req.getParameter("confirmpassword");
            String token = req.getParameter("token");
            User user = UserDAO.getUserByToken(token, conn);
            byte[] passwordHashed = Passwords.hash(confirmPassword.toCharArray(), user.getSalt(), user.getIteration());
            UserDAO.insertPassword(Passwords.base64Encode(passwordHashed), token, conn);


            resp.sendRedirect("./index.jsp");


        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
