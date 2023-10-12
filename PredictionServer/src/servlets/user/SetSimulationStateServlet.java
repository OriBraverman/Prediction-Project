package servlets.user;

import com.google.gson.Gson;
import dto.ActivateSimulationDTO;
import dto.StatusDTO;
import engine.Engine;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.SessionUtils;

import static http.url.URLconst.SET_SIMULATION_STATE_SRC;
import static utils.ServletUtils.getEngine;

@WebServlet(name = "SetSimulationStateServlet", urlPatterns = SET_SIMULATION_STATE_SRC)
public class SetSimulationStateServlet extends HttpServlet {
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, java.io.IOException {
        Gson gson = new Gson();
        ServletContext servletContext = getServletContext();
        if (SessionUtils.getTypeOfClient(request) != http.url.Client.USER) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().println(gson.toJson(new StatusDTO(false, "unauthorized client type")));
            return;
        }
        Engine engine = getEngine(servletContext);
        ActivateSimulationDTO activateSimulationDTO = gson.fromJson(request.getReader(), ActivateSimulationDTO.class);
        try {
            engine.activateSimulation(activateSimulationDTO);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println(gson.toJson(new StatusDTO(true, "Request submitted successfully.")));
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println(gson.toJson(new StatusDTO(false, e.getMessage())));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println(gson.toJson(new StatusDTO(false, e.getMessage())));
        }
    }
}
