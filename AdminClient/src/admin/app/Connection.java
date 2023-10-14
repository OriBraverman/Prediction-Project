package admin.app;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.RequestDTO;
import dto.SimulationExecutionDetailsDTO;
import dto.StatusDTO;
import dto.result.EntityPopulationByTicksDTO;
import dto.result.PropertyStatisticsDTO;
import dto.world.WorldDTO;
import dto.world.action.AbstructActionDTO;
import dto.world.action.AbstructActionDTODeserializer;
import http.cookie.SimpleCookieManager;
import http.url.Constants;
import http.url.URLconst;
import javafx.application.Platform;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import static http.url.Client.ADMIN;
import static http.url.Constants.CONTENT_TYPE;
import static http.url.URLconst.*;

public class Connection {
    private final OkHttpClient client;
    private final Gson gson;
    private AppController appController;


    Connection(AppController appController) {
        this.client = new OkHttpClient().newBuilder().cookieJar(new SimpleCookieManager()).build();
        this.gson = new GsonBuilder()
                .registerTypeAdapter(AbstructActionDTO.class, new AbstructActionDTODeserializer())
                .create();
        this.appController = appController;
    }

    public boolean sendLogIn() {
        String body = "";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(LOGIN_URL).newBuilder();
        urlBuilder.addQueryParameter(Constants.USER_NAME, Constants.ADMIN);
        urlBuilder.addQueryParameter(Constants.CLIENT_TYPE, ADMIN.getClientType());
        String url = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .url(url)
                .addHeader(CONTENT_TYPE, "text/plain")
                .post(RequestBody.create(body.getBytes()))
                .build();

        try (Response response = client.newCall(request).execute()) {
            String dtoAsStr = response.body().string();
            System.out.println("login response Code: " + response.code() + " " + dtoAsStr);

            if (response.code() != 200) {
                Gson gson = new Gson();
                StatusDTO loginStatus = gson.fromJson(dtoAsStr, StatusDTO.class);
                Platform.runLater(() -> {
                    appController.showAlert(loginStatus);
                    Platform.exit();
                });
                return false;
            }
        } catch (Exception e) {
            System.out.println("Oops... something went wrong..." + e.getMessage());
            return false;
        }
        return true;
    }

    public void loadXML(String xmlPath) throws Exception {
        // Create a JSON request body containing the Path object
        String jsonRequest = gson.toJson(xmlPath);
        RequestBody body = RequestBody.create(jsonRequest,
                MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url(URLconst.LOAD_XML_URL)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String dtoAsStr = response.body().string();
            System.out.println("loadXML response Code: " + response.code() + " " + dtoAsStr);

            if (response.code() != 200) {
                Gson gson = new Gson();
                StatusDTO loginStatus = gson.fromJson(dtoAsStr, StatusDTO.class);

                Platform.runLater(() -> appController.showAlert(loginStatus));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public OkHttpClient getClient() {
        return client;
    }

    public void setThreadsCount(String threadsCount) {
        String jsonRequest = gson.toJson(threadsCount);
        RequestBody body = RequestBody.create(jsonRequest,
                MediaType.parse("application/json"));

        Request request = new Request.Builder()
                .url(URLconst.SET_THREADS_COUNT_URL)
                .post(body)
                .build();

        try (Response response = client.newCall(request).execute()) {
            String dtoAsStr = response.body().string();
            System.out.println("setThreadsCount response Code: " + response.code() + " " + dtoAsStr);

            if (response.code() != 200) {
                Gson gson = new Gson();
                StatusDTO loginStatus = gson.fromJson(dtoAsStr, StatusDTO.class);

                Platform.runLater(() -> appController.showAlert(loginStatus));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void sendLogOut() {
        String body = "";
        HttpUrl.Builder urlBuilder = HttpUrl.parse(LOGOUT_URL).newBuilder();
        urlBuilder.addQueryParameter(Constants.USER_NAME, Constants.ADMIN);
        urlBuilder.addQueryParameter(Constants.CLIENT_TYPE, ADMIN.getClientType());
        String url = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .url(url)
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

                    Platform.runLater(() -> appController.showAlert(loginStatus));
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
        String url = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .url(url)
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

                    Platform.runLater(() -> appController.showAlert(loginStatus));
                }
            }
        });
    }

    public boolean isSimulationCompleted(int id) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(URLconst.IS_SIMULATION_COMPLETED_URL).newBuilder();
        urlBuilder.addQueryParameter(Constants.SIMULATION_ID, String.valueOf(id));
        String url = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .url(url)
                .addHeader(CONTENT_TYPE, "text/plain")
                .get()
                .build();
        try (Response response = client.newCall(request).execute()) {
            String dtoAsStr = response.body().string();
            System.out.println("isSimulationCompleted response Code: " + response.code() + " " + dtoAsStr);

            if (response.code() != 200) {
                Gson gson = new Gson();
                StatusDTO statusDTO = gson.fromJson(dtoAsStr, StatusDTO.class);

                Platform.runLater(() -> appController.showAlert(statusDTO));
                return false;
            } else {
                Boolean isSimulationCompleted = gson.fromJson(dtoAsStr, Boolean.class);
                return isSimulationCompleted;
            }
        } catch (IOException e) {
            System.out.println("Oops... something went wrong..." + e.getMessage());
            return false;
        }
    }

    public EntityPopulationByTicksDTO getEntityPopulationByTicksDTO(int simulationID) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(URLconst.FETCH_ENTITY_POPULATION_BY_TICKS_URL).newBuilder();
        urlBuilder.addQueryParameter(Constants.SIMULATION_ID, String.valueOf(simulationID));
        String url = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .url(url)
                .addHeader(CONTENT_TYPE, "text/plain")
                .get()
                .build();
        try (Response response = client.newCall(request).execute()) {
            String dtoAsStr = response.body().string();
            System.out.println("getEntityPopulationByTicksDTO response Code: " + response.code() + " " + dtoAsStr);

            if (response.code() != 200) {
                Gson gson = new Gson();
                StatusDTO statusDTO = gson.fromJson(dtoAsStr, StatusDTO.class);

                Platform.runLater(() -> appController.showAlert(statusDTO));
                return null;
            } else {
                EntityPopulationByTicksDTO entityPopulationByTicksDTO = gson.fromJson(dtoAsStr, EntityPopulationByTicksDTO.class);
                return entityPopulationByTicksDTO;
            }
        } catch (IOException e) {
            System.out.println("Oops... something went wrong..." + e.getMessage());
            return null;
        }
    }

    public WorldDTO getWorldDTO(int simulationID) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(URLconst.FETCH_WORLD_DETAILS_URL).newBuilder();
        urlBuilder.addQueryParameter(Constants.SIMULATION_ID, String.valueOf(simulationID));
        String url = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .url(url)
                .addHeader(CONTENT_TYPE, "text/plain")
                .get()
                .build();
        try (Response response = client.newCall(request).execute()) {
            String dtoAsStr = response.body().string();
            System.out.println("getWorldDTO response Code: " + response.code() + " " + dtoAsStr);

            if (response.code() != 200) {
                Gson gson = new Gson();
                StatusDTO statusDTO = gson.fromJson(dtoAsStr, StatusDTO.class);

                Platform.runLater(() -> appController.showAlert(statusDTO));
                return null;
            } else {
                WorldDTO worldDTO = gson.fromJson(dtoAsStr, WorldDTO.class);
                return worldDTO;
            }
        } catch (IOException e) {
            System.out.println("Oops... something went wrong..." + e.getMessage());
            return null;
        }
    }

    public PropertyStatisticsDTO getPropertyStatisticsDTO(int simulationID, String entityName, String propertyName) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(URLconst.FETCH_PROPERTY_STATISTICS_URL).newBuilder();
        urlBuilder.addQueryParameter(Constants.SIMULATION_ID, String.valueOf(simulationID));
        urlBuilder.addQueryParameter(Constants.ENTITY_NAME, entityName);
        urlBuilder.addQueryParameter(Constants.PROPERTY_NAME, propertyName);
        String url = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .url(url)
                .addHeader(CONTENT_TYPE, "text/plain")
                .get()
                .build();
        try (Response response = client.newCall(request).execute()) {
            String dtoAsStr = response.body().string();
            System.out.println("getPropertyStatisticsDTO response Code: " + response.code() + " " + dtoAsStr);

            if (response.code() != 200) {
                Gson gson = new Gson();
                StatusDTO loginStatus = gson.fromJson(dtoAsStr, StatusDTO.class);

                Platform.runLater(() -> appController.showAlert(loginStatus));
            } else {
                PropertyStatisticsDTO propertyStatisticsDTO = gson.fromJson(dtoAsStr, PropertyStatisticsDTO.class);
                return propertyStatisticsDTO;
            }
        } catch (IOException e) {
            System.out.println("Oops... something went wrong..." + e.getMessage());
        }
        return null;
    }
    public void getSimulationExecutionDetails(int simulationID) {
        HttpUrl.Builder urlBuilder = HttpUrl.parse(URLconst.FETCH_SIMULATION_EXECUTION_DETAILS_URL).newBuilder();
        urlBuilder.addQueryParameter(Constants.SIMULATION_ID, String.valueOf(simulationID));
        String url = urlBuilder.build().toString();
        Request request = new Request.Builder()
                .url(url)
                .addHeader(CONTENT_TYPE, "text/plain")
                .get()
                .build();
        try (Response response = client.newCall(request).execute()) {
            String dtoAsStr = response.body().string();
            System.out.println("getSimulationExecutionDetails response Code: " + response.code() + " " + dtoAsStr);

            if (response.code() != 200) {
                Gson gson = new Gson();
                StatusDTO loginStatus = gson.fromJson(dtoAsStr, StatusDTO.class);

                Platform.runLater(() -> appController.showAlert(loginStatus));
            } else {
                SimulationExecutionDetailsDTO simulationExecutionDetailsDTO = gson.fromJson(dtoAsStr, SimulationExecutionDetailsDTO.class);
                Platform.runLater(() -> appController.getExecutionsHistoryController().updateSimulationComponent(simulationExecutionDetailsDTO));
            }
        } catch (IOException e) {
            System.out.println("Oops... something went wrong..." + e.getMessage());
        }
    }
}
