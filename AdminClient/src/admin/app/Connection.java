package admin.app;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import http.url.URLconst;
import okhttp3.*;

import java.nio.file.Path;

public class Connection {
    private OkHttpClient client;
    private Gson gson;
    Connection() {
        client = new OkHttpClient();
        gson = new GsonBuilder().create();
    }
    public void loadXML(String xmlPath) throws Exception {
        // Create a JSON request body containing the Path object
        String jsonRequest = gson.toJson(xmlPath);
        RequestBody body = RequestBody.create(jsonRequest,
                MediaType.parse("application/json"));

        // Create the request
        Request request = new Request.Builder()
                .url(URLconst.LOAD_XML_URL)
                .post(body)
                .build();

        // Send the request
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new Exception("Error loading XML file");
            }

            // Handle the response as needed
            String responseBody = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public OkHttpClient getClient() {
        return client;
    }

    public void setThreadsCount(String threadsCount) {
        // Create a JSON request body containing the Path object
        String jsonRequest = gson.toJson(threadsCount);
        RequestBody body = RequestBody.create(jsonRequest,
                MediaType.parse("application/json"));

        // Create the request
        Request request = new Request.Builder()
                .url(URLconst.SET_THREADS_COUNT_URL)
                .post(body)
                .build();

        // Send the request
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new Exception("Error setting threads count");
            }

            // Handle the response as needed
            String responseBody = response.body().string();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
