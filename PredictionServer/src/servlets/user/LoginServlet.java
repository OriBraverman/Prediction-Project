package servlets.common;

import com.google.gson.Gson;
import http.url.Client;
import http.url.Constants;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.SessionUtils;

import java.io.IOException;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        Gson gson = new Gson();
        String usernameFromSession = SessionUtils.getUsername(request);

        Client clientType = Client.fromString(request.getParameter(Constants.CLIENT_TYPE));

        if (clientType.equals(Client.UNAUTHORIZED)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            //response.getWriter().println(gson.toJson(new statusDTO(false, Problem.UNAUTHORIZED_CLIENT_ACCESS)));
            return;
        }

    }
}
