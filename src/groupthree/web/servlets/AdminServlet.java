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
import java.util.List;

public class AdminServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection conn = DBConnectionUtils.getConnectionFromSrcFolder("connection.properties")) {
            List<User> user = UserDAO.getAllUserAdminControl(conn);//this is for all the user

            JSONArray myJSON = new JSONArray();
            for (int i = 0; i<user.size(); i++) {
                myJSON.add(User.toJson(user.get(i)));
            }
            JSONResponse.send(resp,myJSON);

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
