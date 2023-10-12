package servlets.common;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.SimulationIDListDTO;
import dto.StatusDTO;
import dto.result.PropertyStatisticsDTO;
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

import static http.url.URLconst.FETCH_PROPERTY_STATISTICS_SRC;
import static utils.ServletUtils.getEngine;

@WebServlet(name="fetchPropertyStatisticsServlet", urlPatterns = FETCH_PROPERTY_STATISTICS_SRC)
public class FetchPropertyStatisticsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new GsonBuilder().create();
        String usernameFromSession = SessionUtils.getUsername(request);
        int simulationID = Integer.parseInt(request.getParameter("simulationID"));
        String entityName = request.getParameter("entityName");
        String propertyName = request.getParameter("propertyName");
        ServletContext servletContext = getServletContext();
        if (SessionUtils.getTypeOfClient(request).equals(Client.UNAUTHORIZED)) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().println(gson.toJson(new StatusDTO(false, "unauthorized client type")));
            return;
        }
        Engine engine = getEngine(servletContext);
        try {
            PropertyStatisticsDTO propertyStatisticsDTO = engine.getPropertyStatisticsDTO(simulationID, entityName, propertyName);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println(gson.toJson(propertyStatisticsDTO));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println(gson.toJson(new StatusDTO(false, e.getMessage())));
        }
    }
}
