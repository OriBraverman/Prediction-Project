package servlets.user;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.NewExecutionInputDTO;
import dto.StatusDTO;
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

import static http.url.URLconst.EXECUTE_REQUEST_SRC;
import static utils.ServletUtils.getEngine;

@WebServlet(name = "FetchExecuteRequestServlet", value = EXECUTE_REQUEST_SRC)
public class FetchExecuteRequestServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new GsonBuilder().create();
        int requestID = Integer.parseInt(request.getParameter("requestID"));
        ServletContext servletContext = getServletContext();
        if (SessionUtils.getTypeOfClient(request) != Client.USER) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().println(gson.toJson(new StatusDTO(false, "unauthorized client type")));
            return;
        }
        Engine engine = getEngine(servletContext);
        try {
            NewExecutionInputDTO newExecutionInputDTO = engine.getNewExecutionInputDTO(requestID);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println(gson.toJson(newExecutionInputDTO));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println(gson.toJson(new StatusDTO(false, e.getMessage())));
        }
    }
}
