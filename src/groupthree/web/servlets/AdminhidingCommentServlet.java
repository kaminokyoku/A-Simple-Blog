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

public class AdminhidingCommentServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        try (Connection conn = DBConnectionUtils.getConnectionFromSrcFolder("connection.properties")) {
            int id = Integer.parseInt(req.getParameter("commentId"));


            boolean value = Boolean.parseBoolean(req.getParameter("isShow"));


            CommentDAO.changeVisibility(id, value, conn);

            JSONObject jsonObject = new JSONObject();
            jsonObject.put("isShow", value);
            JSONResponse.send(resp, jsonObject);
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }
}
