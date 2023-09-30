package admin.app;

import http.url.URLconst;
import javafx.beans.property.SimpleBooleanProperty;
import okhttp3.*;

import java.nio.file.Path;

public class Connection {
    private OkHttpClient client;

    public Connection() {
        this.client = new OkHttpClient();
    }
    public void loadXML(Path xmlPath) throws Exception {
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("xmlFile", xmlPath.toString())
                .build();
        Request request = new Request.Builder()
                .url(URLconst.LOAD_XML_URL)
                .post(requestBody)
                .build();
        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new Exception("Error loading XML file");
        }
    }
}
