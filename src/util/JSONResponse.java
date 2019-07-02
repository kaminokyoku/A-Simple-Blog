package util;

import org.json.simple.JSONAware;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class JSONResponse {
    public static void send(HttpServletResponse response, JSONAware j) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        try {
            response.getWriter().print(j.toJSONString());
        } catch (IOException e) {
            // Error communicating with client, can't really recover from this gracefully
            e.printStackTrace();
        }
    }
}
