package groupthree.web.servlets;

import org.json.simple.JSONObject;
import util.JSONResponse;

import javax.json.JsonObject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class AdminsendingMailByclicking extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = req.getParameter("email");
        int id = Integer.parseInt(req.getParameter("userId"));
        System.out.println(email);
        SendEmail.sendingUsertoPasswordRecoveryPage(email,id);

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("sendMail", "success");
        JSONResponse.send(resp,jsonObject);
    }
}
