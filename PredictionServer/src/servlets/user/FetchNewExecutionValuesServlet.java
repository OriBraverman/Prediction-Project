package servlets.user;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.NewExecutionInputDTO;
import dto.NewExecutionValuesDTO;
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
import utils.SessionUtils;

import java.io.IOException;

import static http.url.URLconst.FETCH_NEW_EXECUTION_VALUES_SRC;
import static utils.ServletUtils.getEngine;

@WebServlet(name = "FetchNewExecutionValuesServlet", value = FETCH_NEW_EXECUTION_VALUES_SRC)
public class FetchNewExecutionValuesServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new GsonBuilder().create();
        int simulationID = Integer.parseInt(request.getParameter(Constants.SIMULATION_ID));
        ServletContext servletContext = getServletContext();
        if (SessionUtils.getTypeOfClient(request) != Client.USER) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().println(gson.toJson(new StatusDTO(false, "unauthorized client type")));
            return;
        }
        Engine engine = getEngine(servletContext);
        try {
            NewExecutionValuesDTO newExecutionValues = engine.getNewExecutionValuesDTO(simulationID);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println(gson.toJson(newExecutionValues));
        } catch (IllegalArgumentException e) {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println(gson.toJson(new StatusDTO(false, e.getMessage())));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println(gson.toJson(new StatusDTO(false, e.getMessage())));
        }
    }
}
