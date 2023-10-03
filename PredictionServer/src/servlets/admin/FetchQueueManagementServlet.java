package servlets.admin;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.QueueManagementDTO;
import dto.StatusDTO;
import engine.Engine;
import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import utils.SessionUtils;

import static http.url.URLconst.FETCH_QUEUE_MANAGEMENT_SRC;
import static utils.ServletUtils.getEngine;

@WebServlet(name="fetchQueueManagementServlet", urlPatterns = FETCH_QUEUE_MANAGEMENT_SRC)
public class FetchQueueManagementServlet extends HttpServlet {
    @Override
    protected void doGet(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response) throws jakarta.servlet.ServletException, java.io.IOException {
        Gson gson = new GsonBuilder().create();
        String usernameFromSession = SessionUtils.getUsername(request);
        //todo: check permissions
        ServletContext servletContext = getServletContext();
        /*if (ServletUtils.getEngine(servletContext).isUserAdmin(usernameFromSession)) {
            throw new ServletException("User is not admin");
        }*/
        Engine engine = getEngine(servletContext);
        try {
            QueueManagementDTO queueManagementDTO = engine.getQueueManagementDTO();
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println(gson.toJson(queueManagementDTO));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println(gson.toJson(new StatusDTO(false, e.getMessage())));
        }
    }
}
