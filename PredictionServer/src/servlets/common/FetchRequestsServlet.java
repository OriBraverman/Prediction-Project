package servlets.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.RequestDTO;
import dto.RequestsDTO;
import dto.SimulationExecutionDetailsDTO;
import dto.StatusDTO;
import engine.Engine;
import http.url.Client;
import http.url.Constants;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import requests.UserRequest;
import user.manager.UsersManager;
import utils.ServletUtils;
import utils.SessionUtils;

import java.io.IOException;

import static http.url.URLconst.FETCH_REQUESTS_SRC;
import static utils.ServletUtils.getEngine;

@WebServlet(name="fetchRequestsServlet", urlPatterns = FETCH_REQUESTS_SRC)
public class FetchRequestsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();
        String usernameFromSession = SessionUtils.getUsername(request);
        ServletContext servletContext = getServletContext();
        if (SessionUtils.getTypeOfClient(request).equals(Client.UNAUTHORIZED)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().println(gson.toJson(new StatusDTO(false, "unauthorized client type")));
            return;
        }
        Engine engine = getEngine(servletContext);
        try {
            RequestsDTO requests = engine.getRequestsDTO(usernameFromSession, SessionUtils.getTypeOfClient(request));
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println(gson.toJson(requests));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println(gson.toJson(new StatusDTO(false, e.getMessage())));
        }
    }
}
