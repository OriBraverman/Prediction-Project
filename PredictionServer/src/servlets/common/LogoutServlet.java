package servlets.common;

import com.google.gson.Gson;
import dto.StatusDTO;
import engine.Engine;
import http.url.Client;
import http.url.Constants;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import user.manager.UsersManager;
import utils.ServletUtils;
import utils.SessionUtils;

import java.io.IOException;

import static http.url.URLconst.LOGOUT_SRC;

@WebServlet(name = "LogoutServlet", urlPatterns = LOGOUT_SRC)
public class LogoutServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        response.setContentType("application/json");
        String usernameFromSession = SessionUtils.getUsername(request);
        Client typeOfClient = SessionUtils.getTypeOfClient(request);
        UsersManager userManager = ServletUtils.getUsersManager(getServletContext());

        if (SessionUtils.getTypeOfClient(request).equals(Client.UNAUTHORIZED)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().println(gson.toJson(new StatusDTO(false, "unauthorized client type")));
            return;
        }

        if (usernameFromSession != null) {
            System.out.println("Clearing session for " + usernameFromSession);
            switch (typeOfClient) {
                case ADMIN:
                    userManager.setAdminLoggedIn(false);
                    break;
                case USER:
                    userManager.removeUser(usernameFromSession);
                    break;
            }
            SessionUtils.clearSession(request);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println(gson.toJson(new StatusDTO(true, "logout succeeded")));
        } else {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().println(gson.toJson(new StatusDTO(false, "user is not logged in")));
        }
    }
}
