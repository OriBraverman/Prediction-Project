package user.app;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.StatusDTO;
import http.url.Constants;
import javafx.beans.property.StringProperty;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import static http.url.Client.USER;
import static http.url.Constants.CONTENT_TYPE;
import static http.url.URLconst.LOGIN_URL;

public class Connection {
    private OkHttpClient client;
    private StringProperty usernameProperty;
    private Gson gson;
    private AppController appController;

    Connection(AppController appController) {
        gson = new GsonBuilder().create();
        this.appController = appController;
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
    public void sendLogOut() {
        String body = "";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(LOGIN_URL).newBuilder();
        urlBuilder.addQueryParameter(Constants.USER_NAME, usernameProperty.getValue());
        urlBuilder.addQueryParameter(Constants.CLIENT_TYPE, USER.getClientType());
        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .addHeader(CONTENT_TYPE, "text/plain")
                .post(RequestBody.create(body.getBytes()))
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                System.out.println("Oops... something went wrong..." + e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String dtoAsStr = response.body().string();
                System.out.println("logout response Code: " + response.code() + " " + dtoAsStr);

                if (response.code() != 200) {
                    Gson gson = new Gson();
                    StatusDTO loginStatus = gson.fromJson(dtoAsStr, StatusDTO.class);

                    appController.showAlert(loginStatus);
                }
            }
        });
    }
}
