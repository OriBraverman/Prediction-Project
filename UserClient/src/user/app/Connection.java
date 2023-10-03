package user.app;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import javafx.beans.property.StringProperty;
import okhttp3.OkHttpClient;

public class Connection {
    private OkHttpClient client;
    private StringProperty usernameProperty;
    private Gson gson;
    Connection() {
        gson = new GsonBuilder().create();
    }

    public OkHttpClient getClient() {
        return client;
    }

    public void setClient(OkHttpClient client) {
        this.client = client;
    }

    public StringProperty getUsernameProperty() {
        return usernameProperty;
    }
}
