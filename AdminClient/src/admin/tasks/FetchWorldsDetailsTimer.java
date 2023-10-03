package user.tasks;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.StatusDTO;
import dto.world.WorldsDTO;
import dto.world.action.AbstructActionDTODeserializer;
import dto.world.action.AbstructActionDTO;
import javafx.application.Platform;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import user.app.AppController;

import java.io.IOException;
import java.util.TimerTask;

import static http.url.URLconst.FETCH_WORLDS_DETAILS_URL;
import static http.url.Constants.CONTENT_TYPE;

public class FetchWorldsDetailsTimer extends TimerTask {
    private OkHttpClient client;
    private final AppController appController;

    public FetchWorldsDetailsTimer(AppController appController, OkHttpClient client) {
        this.appController = appController;
        this.client = client;
    }

    @Override
    public void run() {
        Request request = new Request.Builder()
                .url(FETCH_WORLDS_DETAILS_URL)
                .addHeader(CONTENT_TYPE, "text/plain")
                .get()
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                //System.out.println("FetchWorldsDetailsTimer: onResponse" + " Code: " + response.code());
                String responseBody = response.body().string();
                Gson gson = new GsonBuilder()
                        .registerTypeAdapter(AbstructActionDTO.class, new AbstructActionDTODeserializer())
                        .create();
                if (response.code() == 200) {
                    WorldsDTO worldsDTO = gson.fromJson(responseBody, WorldsDTO.class);
                    Platform.runLater(() -> appController.setWorldsDetails(worldsDTO));
                } else {
                    StatusDTO statusDTO = gson.fromJson(responseBody, StatusDTO.class);
                    Platform.runLater(() -> {});
                }
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                //System.out.println("FetchWorldsDetailsTimer: onFailure");
                Platform.runLater(() -> appController.showAlert(new StatusDTO(false, e.getMessage())));
            }
        });
    }
}
