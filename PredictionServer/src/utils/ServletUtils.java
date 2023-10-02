package utils;

import engine.Engine;
import engine.EngineImpl;
import http.url.Constants;
import jakarta.servlet.ServletContext;

public class ServletUtils {
    private static final Object engineLock = new Object();

    public static Engine getEngine(ServletContext servletContext) {
        synchronized (engineLock) {
            if (servletContext.getAttribute(Constants.ENGINE) == null) {
                servletContext.setAttribute(Constants.ENGINE, new EngineImpl());
            }
        }
        return (Engine) servletContext.getAttribute(Constants.ENGINE);
    }
}
