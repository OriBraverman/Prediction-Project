package servlets.common;

import com.google.gson.Gson;
import dto.StatusDTO;
import http.url.Client;
import http.url.Constants;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import user.manager.UsersManager;
import utils.ServletUtils;
import utils.SessionUtils;

import java.io.IOException;
import java.util.Set;

import static http.url.URLconst.LOGIN_SRC;

@WebServlet(name = "LoginServlet", urlPatterns = LOGIN_SRC)
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        Gson gson = new Gson();
        String usernameFromSession = SessionUtils.getUsername(request);
        Client typeOfClient = Client.getClientTypeFromString(request.getParameter(Constants.CLIENT_TYPE));
        UsersManager userManager = ServletUtils.getUsersManager(getServletContext());

        if (typeOfClient.equals(Client.UNAUTHORIZED)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().println(gson.toJson(new StatusDTO(false, "unauthorized client type")));
            return;
        }

        if (usernameFromSession == null) { //user is not logged in yet
            String usernameFromParameter = request.getParameter(Constants.USER_NAME);
            if (usernameFromParameter == null || usernameFromParameter.isEmpty()) {
                // create response for bad request
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().println(gson.toJson(new StatusDTO(false, "missing query username")));
            } else {
                synchronized (this) {
                    switch (typeOfClient) {
                        case ADMIN:
                            if (userManager.isAdminLoggedIn()) {
                                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                                response.getWriter().println(gson.toJson(new StatusDTO(false, "admin is already logged in")));
                                return;
                            } else {
                                userManager.setAdminLoggedIn(true);
                            }
                            break;
                        case USER:
                            if (userManager.isUserExists(usernameFromParameter)) {
                                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                                response.getWriter().println(gson.toJson(new StatusDTO(false, "user already logged in")));
                                return;
                            } else {
                                userManager.addUser(usernameFromParameter);
                            }
                            break;
                    }
                    // assuming we got here then all ok
                    request.getSession(true).setAttribute(Constants.USER_NAME, usernameFromParameter);
                    request.getSession(true).setAttribute(Constants.CLIENT_TYPE, typeOfClient);
                    response.setStatus(HttpServletResponse.SC_OK);
                }
            }
        } else {
            //user is already logged in
            response.setStatus(HttpServletResponse.SC_OK);
        }
    }
}
