package servlets.user;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.ActivateSimulationDTO;
import dto.ExecutionSummaryDTO;
import dto.SimulationIDDTO;
import dto.StatusDTO;
import dto.result.EntityPopulationByTicksDTO;
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

import static http.url.URLconst.FETCH_EXECUTION_SUMMARY_SRC;
import static utils.ServletUtils.getEngine;

@WebServlet(name = "FetchExecutionSummaryServlet", value = FETCH_EXECUTION_SUMMARY_SRC)
public class FetchExecutionSummaryServlet extends HttpServlet {
    @Override
    protected void doPost(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response) throws jakarta.servlet.ServletException, java.io.IOException {
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
            //ExecutionSummaryDTO simulationIDDTO = engine.getExecutionSummaryDTO(activateSimulationDTO);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println(gson.toJson(simulationIDDTO));
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println(gson.toJson(new StatusDTO(false, e.getMessage())));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println(gson.toJson(new StatusDTO(false, e.getMessage())));
        }
    }
}
