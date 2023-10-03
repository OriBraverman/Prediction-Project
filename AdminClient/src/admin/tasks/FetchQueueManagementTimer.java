package admin.tasks;

import admin.management.ManagementController;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dto.QueueManagementDTO;
import dto.StatusDTO;
import dto.world.WorldsDTO;
import dto.world.action.AbstructActionDTO;
import dto.world.action.AbstructActionDTODeserializer;
import javafx.application.Platform;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.TimerTask;

import static http.url.Constants.CONTENT_TYPE;
import static http.url.URLconst.FETCH_QUEUE_MANAGEMENT_URL;
import static http.url.URLconst.FETCH_WORLDS_DETAILS_URL;

public class FetchQueueManagementTimer extends TimerTask {
    private final OkHttpClient client;
    private final ManagementController managementController;

    public FetchQueueManagementTimer(OkHttpClient client, ManagementController managementController) {
        this.client = client;
        this.managementController = managementController;
    }

    @Override
    public void run() {
        Request request = new Request.Builder()
                .url(FETCH_QUEUE_MANAGEMENT_URL)
                .addHeader(CONTENT_TYPE, "text/plain")
                .get()
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                String responseBody = response.body().string();
                Gson gson = new GsonBuilder()
                        .create();
                if (response.code() == 200) {
                    QueueManagementDTO queueManagementDTO = gson.fromJson(responseBody, QueueManagementDTO.class);
                    Platform.runLater(() -> managementController.updateQueueManagement(queueManagementDTO));
                } else {
                    StatusDTO statusDTO = gson.fromJson(responseBody, StatusDTO.class);
                    Platform.runLater(() -> {});
                }
            }

            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                //System.out.println("FetchWorldsDetailsTimer: onFailure");
                Platform.runLater(() -> managementController.showAlert(new StatusDTO(false, e.getMessage())));
            }
        });
    }
}
