package servlets.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.StatusDTO;
import dto.world.WorldDTO;
import dto.world.WorldsDTO;
import dto.world.action.AbstructActionDTO;
import dto.world.action.AbstructActionDTOSerializer;
import engine.Engine;
import http.url.Client;
import http.url.Constants;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.SessionUtils;

import java.io.IOException;

import static http.url.URLconst.FETCH_WORLD_DETAILS_SRC;
import static utils.ServletUtils.getEngine;

@WebServlet(name="fetchWorldDetailsServlet", urlPatterns = FETCH_WORLD_DETAILS_SRC)
public class FetchWorldDetailsServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(AbstructActionDTO.class, new AbstructActionDTOSerializer())
                .create();
        int simulationID = Integer.parseInt(request.getParameter(Constants.SIMULATION_ID));
        String usernameFromSession = SessionUtils.getUsername(request);
        ServletContext servletContext = getServletContext();
        if (SessionUtils.getTypeOfClient(request).equals(Client.UNAUTHORIZED)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().println(gson.toJson(new StatusDTO(false, "unauthorized client type")));
            return;
        }
        Engine engine = getEngine(servletContext);
        try {
            WorldDTO worldDTO = engine.getWorldDTO(simulationID);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println(gson.toJson(worldDTO));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println(gson.toJson(new StatusDTO(false, e.getMessage())));
        }
    }
}
