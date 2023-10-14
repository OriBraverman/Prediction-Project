package admin.app;

import admin.AdminApplication;
import admin.allocations.AllocationsController;
import admin.executionHistory.scene.ExecutionsHistoryController;
import admin.management.scene.ManagementController;
import dto.StatusDTO;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import okhttp3.OkHttpClient;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AppController {
    // FXML design components
    @FXML private Label HeaderLabel;
    @FXML private VBox MainVBox;
    @FXML private GridPane UpperGridPane;
    @FXML private AnchorPane TitleRow;
    @FXML private MenuButton SkinsMenuButton;

    // FXML components
    @FXML private ScrollPane applicationScrollPane;
    @FXML private TabPane tabPane;
    @FXML private AnchorPane managementComponent;
    @FXML private ManagementController managementComponentController;
    @FXML private AnchorPane allocationsComponent;
    @FXML private AllocationsController allocationsComponentController;
    @FXML private AnchorPane executionsHistoryComponent;
    @FXML private ExecutionsHistoryController executionsHistoryComponentController;


    private final Connection connection = new Connection(this);
    private final SimpleBooleanProperty isXMLLoaded;
    private final SimpleBooleanProperty isSimulationExecuted;
    private static final List<String> cssList =
            new ArrayList<>(Arrays.asList(
                    "Application.css", "DarkMode-theme.css",
                    "HappyMode-theme.css", "OrangeApplication.css",
                    "GreenApplication.css"));

    public enum Tab {
        MANAGEMENT, ALLOCATION, EXECUTIONS_HISTORY
    };

    public AppController() {
        this.isXMLLoaded = new SimpleBooleanProperty(false);
        this.isSimulationExecuted = new SimpleBooleanProperty(false);
    }

    @FXML public void initialize(){
        boolean loginSucceeded = connection.sendLogIn();
        setColorThemeComponents();
        if (managementComponentController != null && allocationsComponentController != null
                && executionsHistoryComponentController != null) {
            managementComponentController.setAppController(this);
            allocationsComponentController.setAppController(this);
            executionsHistoryComponentController.setAppController(this);
        }
        Platform.runLater(() -> {
            // Set applicationScrollPane to be the same size as the window
            applicationScrollPane.prefWidthProperty().bind(AdminApplication.getStage().widthProperty());
            applicationScrollPane.prefHeightProperty().bind(AdminApplication.getStage().heightProperty());
            applicationScrollPane.setFitToWidth(true);
            applicationScrollPane.setFitToHeight(true);

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                if (loginSucceeded) {
                    connection.sendLogOut();
                }
                // close javaFX application
                Platform.exit();
            }));
        });
    }

    private void setColorThemeComponents() {
        // set skins menu button
        applyDesign("Application.css");
        SkinsMenuButton.getItems().clear();
        List<MenuItem> menuItems = new ArrayList<>();
        MenuItem defaultSkin = new MenuItem("Default");
        defaultSkin.setOnAction(event -> {
            switchColorMode("Application.css");
        });
        MenuItem darkModeSkin = new MenuItem("Dark Mode");
        darkModeSkin.setOnAction(event -> {
            switchColorMode("DarkMode-theme.css");
        });
        MenuItem happyModeSkin = new MenuItem("Happy Mode");
        happyModeSkin.setOnAction(event -> {
            switchColorMode("HappyMode-theme.css");
        });
        MenuItem orangeSkin = new MenuItem("Orange Mode");
        orangeSkin.setOnAction(event -> {
            switchColorMode("OrangeApplication.css");
        });
        MenuItem greenSkin = new MenuItem("Green Mode");
        greenSkin.setOnAction(event -> {
            switchColorMode("GreenApplication.css");
        });
        menuItems.add(defaultSkin);
        menuItems.add(darkModeSkin);
        menuItems.add(happyModeSkin);
        menuItems.add(orangeSkin);
        menuItems.add(greenSkin);
        SkinsMenuButton.getItems().addAll(menuItems);
    }

    public void selectTab(Tab tab) {
        switch (tab) {
            case MANAGEMENT:
                tabPane.getSelectionModel().select(0);
                break;
            case ALLOCATION:
                tabPane.getSelectionModel().select(1);
                break;
            case EXECUTIONS_HISTORY:
                tabPane.getSelectionModel().select(2);
                break;
        }
    }

    public TabPane getTabPane(){ return tabPane; }

    private void applyDesign(String cssPath){
        if (getClass().getResource(cssPath) != null) {
            MainVBox.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());
            UpperGridPane.getStylesheets().add(getClass().getResource(cssPath).toExternalForm());
        }
    }

    private void removeDesign(String cssPath){
        if (getClass().getResource(cssPath) != null) {
            MainVBox.getStylesheets().remove(getClass().getResource(cssPath).toExternalForm());
            UpperGridPane.getStylesheets().remove(getClass().getResource(cssPath).toExternalForm());
        }
    }

    private void switchColorMode(String cssPath){
        for (String css : cssList) {
            if (!css.equals(cssPath)) {
                removeDesign(css);
            }
        }
        applyDesign(cssPath);
    }

    public void setThreadsCount(String threadsCount) throws NumberFormatException {
        connection.setThreadsCount(threadsCount);
    }

    public void loadXML(String xmlPath) throws Exception {
        connection.loadXML(xmlPath);
    }

    public OkHttpClient getClient() {
        return connection.getClient();
    }

    public void showAlert(StatusDTO statusDTO) {
        if (statusDTO.isSuccessful()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Success");
            alert.setHeaderText("Success");
            alert.setContentText(statusDTO.getMessage());
            alert.showAndWait();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Error");
            alert.setContentText(statusDTO.getMessage());
            alert.showAndWait();
        }
    }

    public void updateRequestStatus(int requestID, String status) {
        connection.updateRequestStatus(requestID, status);
    }

    public Connection getConnection() {
        return connection;
    }

    public ExecutionsHistoryController getExecutionsHistoryController() {
        return executionsHistoryComponentController;
    }
}