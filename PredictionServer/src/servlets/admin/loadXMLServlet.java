package servlets.admin;

import com.google.gson.Gson;
import engine.Engine;
import http.url.Constants;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import utils.SessionUtils;

import java.io.IOException;
import java.nio.file.Path;

@WebServlet(name="loadXMLServlet", urlPatterns = "/loadXML")
public class loadXMLServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        String usernameFromSession = SessionUtils.getUsername(request);
        //todo: check permissions
        ServletContext servletContext = getServletContext();
        Engine engine = (Engine) servletContext.getAttribute(Constants.ENGINE);
        Path xmlPath = (Path) request.getAttribute(???)
        try {
            engine.loadXML(xmlPath);
            response.setStatus(HttpServletResponse.SC_OK);
            //response.getWriter().println(gson.toJson(new statusDTO(true, Problem.SUCCESS)));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            //response.getWriter().println(gson.toJson(new statusDTO(false, Problem.INTERNAL_SERVER_ERROR)));
        }
    }
}
