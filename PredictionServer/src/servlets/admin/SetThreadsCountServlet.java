package servlets.admin;

import com.google.gson.Gson;
import dto.StatusDTO;
import engine.Engine;
import jakarta.servlet.ServletContext;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletResponse;
import utils.SessionUtils;

import static http.url.URLconst.SET_THREADS_COUNT_SRC;
import static utils.ServletUtils.getEngine;

@WebServlet(name="setThreadsCountServlet", urlPatterns = SET_THREADS_COUNT_SRC)
public class SetThreadsCountServlet extends HttpServlet {
    @Override
    protected void doPost(jakarta.servlet.http.HttpServletRequest request, jakarta.servlet.http.HttpServletResponse response) throws jakarta.servlet.ServletException, java.io.IOException {
        Gson gson = new Gson();
        String usernameFromSession = SessionUtils.getUsername(request);
        ServletContext servletContext = getServletContext();
        if (SessionUtils.getTypeOfClient(request) != http.url.Client.ADMIN) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().println(gson.toJson(new StatusDTO(false, "unauthorized client type")));
            return;
        }
        Engine engine = getEngine(servletContext);
        String threadsCount = gson.fromJson(request.getReader(), String.class);
        try {
            engine.setThreadsCount(threadsCount);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println(gson.toJson(new StatusDTO(true, "Threads count set successfully.")));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println(gson.toJson(new StatusDTO(false, e.getMessage())));
        }
    }
}
