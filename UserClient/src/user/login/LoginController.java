package user.login;

import javafx.scene.Parent;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import user.UserApplication;
import user.app.AppController;
import com.google.gson.Gson;
import dto.StatusDTO;
import http.cookie.SimpleCookieManager;
import http.url.Constants;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import okhttp3.*;

import java.io.IOException;
import java.net.URL;

import static http.url.Client.USER;
import static http.url.URLconst.BASE_URL;
import static http.url.Constants.CONTENT_TYPE;
import static http.url.URLconst.LOGIN_URL;

public class LoginController {
    @FXML private TextField userNameTextField;

    @FXML private Button logInButton;

    @FXML private Label errorLabel;

    Parent app;

    private AppController appController;

    @FXML
    public void initialize() {
        errorLabel.setVisible(false); // hide error label
    }


    @FXML
    void sendLogIn(MouseEvent event) {
        String body = "";
        OkHttpClient client = new OkHttpClient().newBuilder().cookieJar(new SimpleCookieManager()).build();
        HttpUrl.Builder urlBuilder = HttpUrl.parse(LOGIN_URL).newBuilder();
        urlBuilder.addQueryParameter(Constants.USER_NAME, userNameTextField.getText());
        urlBuilder.addQueryParameter(Constants.CLIENT_TYPE, USER.getClientType());
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

                    Platform.runLater(() -> {
                        errorLabel.setVisible(true);
                        errorLabel.setText(loginStatus.getMessage());
                    });
                    return;
                }


                Platform.runLater(() -> {
                    FXMLLoader loader = null;
                    try {
                        loader = new FXMLLoader();
                        URL appFxml = getClass().getResource("../app/Application.fxml");
                        loader.setLocation(appFxml);
                        app = loader.load();
                        appController = loader.getController();
                        appController.setOkHttpClient(client);
                        appController.setUserName(userNameTextField.getText());

                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    Scene appScene = new Scene(app);
                    Stage primaryStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                    primaryStage.setScene(appScene);
                    primaryStage.show();
                });
            }

            public void onFailure(Call call, IOException e) {
                System.out.println("Oops... something went wrong..." + e.getMessage());
            }
        });

    }
}
