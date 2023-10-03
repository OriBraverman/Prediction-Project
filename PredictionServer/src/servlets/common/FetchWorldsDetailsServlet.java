package servlets.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.StatusDTO;
import dto.world.WorldsDTO;
import dto.world.action.AbstructActionDTO;
import dto.world.action.AbstructActionDTOSerializer;
import engine.Engine;
import http.url.Client;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.SessionUtils;

import java.io.IOException;

import static http.url.URLconst.FETCH_WORLDS_DETAILS_SRC;
import static utils.ServletUtils.getEngine;

@WebServlet(name="fetchWorldsDetailsServlet", urlPatterns = FETCH_WORLDS_DETAILS_SRC)
public class FetchWorldsDetailsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(AbstructActionDTO.class, new AbstructActionDTOSerializer())
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
            WorldsDTO worldsDTO = engine.getWorldsDTO();
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println(gson.toJson(worldsDTO));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println(gson.toJson(new StatusDTO(false, e.getMessage())));
        }
    }
}
