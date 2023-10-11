package user.app;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.*;
import dto.world.TerminationDTO;
import http.url.Constants;
import http.url.URLconst;
import javafx.application.Platform;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import static http.url.Client.USER;
import static http.url.Constants.CONTENT_TYPE;
import static http.url.URLconst.LOGIN_URL;

public class Connection {
    private OkHttpClient client;
    private final Gson gson;
    private final AppController appController;

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
    public void sendLogOut() {
        String body = "";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(LOGIN_URL).newBuilder();
        urlBuilder.addQueryParameter(Constants.USER_NAME, appController.getUsername());
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

    public void submitRequest(String simulationName, int amount, int terminationByTicks, int terminationBySeconds, boolean isTerminationByUser) {
        TerminationDTO terminationDTO = new TerminationDTO(isTerminationByUser, terminationBySeconds, terminationByTicks);
        String username = appController.getUsername();
        RequestDTO requestDTO = new RequestDTO(username, simulationName, amount, terminationDTO);
        String jsonRequest = gson.toJson(requestDTO);
        Request request = new Request.Builder()
                .url(URLconst.SUBMIT_REQUEST_URL)
                .addHeader(CONTENT_TYPE, "application/json")
                .post(RequestBody.create(jsonRequest, MediaType.parse("application/json")))
                .build();
        try (Response response = client.newCall(request).execute()) {
            String dtoAsStr = response.body().string();
            System.out.println("submitRequest response Code: " + response.code() + " " + dtoAsStr);

            if (response.code() != 200) {
                Gson gson = new Gson();
                StatusDTO loginStatus = gson.fromJson(dtoAsStr, StatusDTO.class);

                appController.showAlert(loginStatus);
            }
        } catch (IOException e) {
            System.out.println("Oops... something went wrong..." + e.getMessage());
        }
    }

    public void executeRequest(int requestID) {
        // a get request with the requestID as a parameter
        HttpUrl.Builder urlBuilder = HttpUrl.parse(URLconst.EXECUTE_REQUEST_URL).newBuilder();
        urlBuilder.addQueryParameter(Constants.REQUEST_ID, String.valueOf(requestID));
        Request request = new Request.Builder()
                .url(urlBuilder.build())
                .addHeader(CONTENT_TYPE, "text/plain")
                .get()
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                System.out.println("Oops... something went wrong..." + e.getMessage());
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String dtoAsStr = response.body().string();
                System.out.println("executeRequest response Code: " + response.code() + " " + dtoAsStr);
                NewExecutionInputDTO newExecutionInputDTO = gson.fromJson(dtoAsStr, NewExecutionInputDTO.class);
                Platform.runLater(() -> appController.getExecutionController().updateExecution(newExecutionInputDTO));

                if (response.code() != 200) {
                    Gson gson = new Gson();
                    StatusDTO loginStatus = gson.fromJson(dtoAsStr, StatusDTO.class);

                    appController.showAlert(loginStatus);
                }
            }
        });
    }

    public boolean activateSimulation(ActivateSimulationDTO activateSimulationDTO) {
        // pass the requestID, envVariablesValuesDTO, entityPopulationDTO to the server
        String jsonRequest = gson.toJson(activateSimulationDTO);
        Request request = new Request.Builder()
                .url(URLconst.ACTIVATE_SIMULATION_URL)
                .addHeader(CONTENT_TYPE, "application/json")
                .post(RequestBody.create(jsonRequest, MediaType.parse("application/json")))
                .build();

        try (Response response = client.newCall(request).execute()) {
            String dtoAsStr = response.body().string();
            System.out.println("activateSimulation response Code: " + response.code() + " " + dtoAsStr);

            if (response.code() != 200) {
                Gson gson = new Gson();
                StatusDTO loginStatus = gson.fromJson(dtoAsStr, StatusDTO.class);
                appController.showAlert(loginStatus);
                return false;
            }
        } catch (IOException e) {
            System.out.println("Oops... something went wrong..." + e.getMessage());
            return false;
        }
        return true;
    }
}
