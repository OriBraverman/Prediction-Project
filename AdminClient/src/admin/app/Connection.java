package admin.app;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.RequestDTO;
import dto.StatusDTO;
import http.cookie.SimpleCookieManager;
import http.url.Constants;
import http.url.URLconst;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import static http.url.Client.ADMIN;
import static http.url.Client.USER;
import static http.url.Constants.CONTENT_TYPE;
import static http.url.URLconst.*;

public class Connection {
    private OkHttpClient client;
    private Gson gson;
    private AppController appController;


    Connection(AppController appController) {
        this.client = new OkHttpClient().newBuilder().cookieJar(new SimpleCookieManager()).build();
        this.gson = new GsonBuilder().create();
        this.appController = appController;
    }

    public void sendLogIn() {
        String body = "";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(LOGIN_URL).newBuilder();
        urlBuilder.addQueryParameter(Constants.USER_NAME, Constants.ADMIN);
        urlBuilder.addQueryParameter(Constants.CLIENT_TYPE, ADMIN.getClientType());
        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .addHeader(CONTENT_TYPE, "text/plain")
                .post(RequestBody.create(body.getBytes()))
                .build();
        client.newCall(request).enqueue(new Callback() {
            public void onResponse(Call call, Response response) throws IOException {
                String dtoAsStr = response.body().string();
                System.out.println("login response Code: " + response.code() + " " + dtoAsStr);

                if (response.code() != 200) {
                    Gson gson = new Gson();
                    StatusDTO loginStatus = gson.fromJson(dtoAsStr, StatusDTO.class);
                    appController.showAlert(loginStatus);
                }
            }

            public void onFailure(Call call, IOException e) {
                System.out.println("Oops... something went wrong..." + e.getMessage());
            }
        });

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

    public void sendLogOut() {
        String body = "";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(LOGOUT_URL).newBuilder();
        urlBuilder.addQueryParameter(Constants.USER_NAME, Constants.ADMIN);
        urlBuilder.addQueryParameter(Constants.CLIENT_TYPE, ADMIN.getClientType());
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

    public void approveRequest(RequestDTO requestDTO) {
    }

    public void updateRequestStatus(int requestID, String status) {
        String body = "";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(UPDATE_REQUEST_STATUS_URL).newBuilder();
        urlBuilder.addQueryParameter(Constants.REQUEST_ID, String.valueOf(requestID));
        urlBuilder.addQueryParameter(Constants.STATUS, status);
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
                System.out.println("updateRequestStatus response Code: " + response.code() + " " + dtoAsStr);

                if (response.code() != 200) {
                    Gson gson = new Gson();
                    StatusDTO loginStatus = gson.fromJson(dtoAsStr, StatusDTO.class);

                    appController.showAlert(loginStatus);
                }
            }
        });
    }
}
