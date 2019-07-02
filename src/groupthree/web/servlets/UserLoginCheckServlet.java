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
import java.util.ArrayList;
import java.util.List;

public class UserLoginCheckServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String userName = req.getParameter("userName");
        String password = req.getParameter("password");

        List<String> msg = new ArrayList<>();

        String success = "no";

        if (userName.isEmpty()) {
            msg.add("Username is empty.");
        }

        if (password.isEmpty()) {
            msg.add("Password is empty.");
        }

        if (!userName.isEmpty() && !password.isEmpty()) {
            //user the password check
            try (Connection conn = DBConnectionUtils.getConnectionFromSrcFolder("connection.properties")) {

                User currentUser = UserDAO.getUserByUsername(conn, userName);

                if (currentUser != null && (AuthenticatorUtils.authenticate(currentUser, password))) {
                    success = "yes";
                } else {
                    msg.add("Invalid username/password combination.");
                }

            } catch (
                    SQLException e) {

                resp.setStatus(500);
                e.printStackTrace();
                throw new ServletException("Database access error!", e);

            }


        }

        if ("no".equals(success)) {
            JSONObject error = new JSONObject();
            error.put("success", success);
            error.put("msg", msg);
            JSONResponse.send(resp, error);
        } else {
            JSONObject login = new JSONObject();
            login.put("success", success);
            JSONResponse.send(resp, login);
        }
    }
}
