package servlets.admin;

import com.google.gson.Gson;
import dto.StatusDTO;
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
import java.lang.reflect.Type;
import java.nio.file.Path;

import static utils.ServletUtils.getEngine;

@WebServlet(name="loadXMLServlet", urlPatterns = "/loadXML")
public class loadXMLServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Gson gson = new Gson();
        String usernameFromSession = SessionUtils.getUsername(request);
        //todo: check permissions
        ServletContext servletContext = getServletContext();
        Engine engine = getEngine(servletContext);
        String xmlPath = gson.fromJson(request.getReader(), String.class);
        try {
            engine.loadXML(xmlPath);
            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().println(gson.toJson(new StatusDTO(true, "XML file loaded successfully.")));
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().println(gson.toJson(new StatusDTO(false, e.getMessage())));
        }
    }
}
