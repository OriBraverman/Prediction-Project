package utils;

import com.google.gson.Gson;
import dto.StatusDTO;
import engine.Engine;
import engine.EngineImpl;
import http.url.Client;
import http.url.Constants;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletResponse;
import user.manager.UsersManager;
import user.manager.UsersManagerImpl;

import java.io.IOException;

public class ServletUtils {
    private static final String USER_MANAGER_ATTRIBUTE_NAME = "userManager";
    private static final String CHAT_MANAGER_ATTRIBUTE_NAME = "chatManager";
    /*
    Note how the synchronization is done only on the question and\or creation of the relevant managers and once they exists -
    the actual fetch of them is remained un-synchronized for performance POV
     */
    private static final Object userManagerLock = new Object();
    private static final Object adminManagerLock = new Object();
    private static final Object engineLock = new Object();


    public static UsersManager getUsersManager(ServletContext servletContext) {
        synchronized (userManagerLock) {
            if (servletContext.getAttribute(USER_MANAGER_ATTRIBUTE_NAME) == null) {
                servletContext.setAttribute(USER_MANAGER_ATTRIBUTE_NAME, new UsersManagerImpl());
            }
        }
        return (UsersManager) servletContext.getAttribute(USER_MANAGER_ATTRIBUTE_NAME);
    }

    public static Engine getEngine(ServletContext servletContext) {
        synchronized (engineLock) {
            if (servletContext.getAttribute(Constants.ENGINE) == null) {
                servletContext.setAttribute(Constants.ENGINE, new EngineImpl());
            }
        }
        return (Engine) servletContext.getAttribute(Constants.ENGINE);
    }
}
