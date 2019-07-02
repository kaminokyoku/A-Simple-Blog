package groupthree.web.servlets;

import org.json.simple.JSONArray;
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
import java.util.List;

public class AdminDeletingUserServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (Connection conn = DBConnectionUtils.getConnectionFromSrcFolder("connection.properties")) {

            int userId = Integer.parseInt(req.getParameter("userId"));

            UserDAO.deleteUserAdminControl(userId, conn);


            JSONObject jsonObject = new JSONObject();
            jsonObject.put("deleteMsg", "the user has been deleted");
            JSONResponse.send(resp, jsonObject);

        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
