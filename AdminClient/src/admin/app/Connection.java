package admin.app;

import http.url.URLconst;
import javafx.beans.property.SimpleBooleanProperty;
import okhttp3.*;

import java.nio.file.Path;

public class Connection {
    private SimpleBooleanProperty isEngineLoaded;
    public void loadXML(Path xmlPath) throws Exception {
        if (isEngineLoaded.get()) {
            throw new Exception("Engine is already loaded");
        } else {


            //HttpURL.Builder urlBuilder = HttpURL.parse(URLconst.LOAD_XML_URL);
        }
    }
}
