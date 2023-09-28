package user;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.Window;

public class UserApplication extends Application {
    private static Stage primaryStage;
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Admin Application");
        Parent load = FXMLLoader.load(getClass().getResource("app/Application.fxml"));
        primaryStage.getIcons().add(
                new Image(
                        UserApplication.class.getResourceAsStream( "app/prediction-icon.png")));
        Scene scene = new Scene(load);
        primaryStage.setScene(scene);
        primaryStage.show();
        this.primaryStage = primaryStage;
    }
    public static Window getStage() {
        return primaryStage;
    }
}