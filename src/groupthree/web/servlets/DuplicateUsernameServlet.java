package groupthree.web.servlets;

import util.DBConnectionUtils;

import org.json.simple.JSONObject;
import util.JSONResponse;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class DuplicateUsernameServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection conn = DBConnectionUtils.getConnectionFromSrcFolder("connection.properties")) {

            JSONObject usernameDuplicate = new JSONObject();

            User user = UserDAO.getUserByUsername(conn, req.getParameter("username"));

            if (user != null){
                usernameDuplicate.put("duplicate", true);
            }else {
                usernameDuplicate.put("duplicate", false);
            }

            JSONResponse.send(resp, usernameDuplicate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
