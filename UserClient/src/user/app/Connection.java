package user.app;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import okhttp3.OkHttpClient;

public class Connection {
    private OkHttpClient client;
    private Gson gson;
    Connection() {
        client = new OkHttpClient();
        gson = new GsonBuilder().create();
    }

    public OkHttpClient getClient() {
        return client;
    }
}
