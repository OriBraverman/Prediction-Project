package servlets.admin;

import com.google.gson.Gson;
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

import static http.url.URLconst.UPDATE_REQUEST_STATUS_SRC;
import static utils.ServletUtils.getEngine;

@WebServlet(name="updateRequestStatusServlet", urlPatterns = UPDATE_REQUEST_STATUS_SRC)
public class UpdateRequestStatusServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        String requestID = request.getParameter(Constants.REQUEST_ID);
        int requestIDInt = Integer.parseInt(requestID);
        String status = request.getParameter(Constants.STATUS);
        ServletContext servletContext = getServletContext();
        if (SessionUtils.getTypeOfClient(request) != Client.ADMIN) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().println(gson.toJson(new StatusDTO(false, "unauthorized client type")));
            return;
        }
        Engine engine = getEngine(servletContext);
        try {
            engine.updateRequestStatus(requestIDInt, status);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println(gson.toJson(new StatusDTO(true, "XML file loaded successfully.")));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println(gson.toJson(new StatusDTO(false, e.getMessage())));
        }
    }
}
