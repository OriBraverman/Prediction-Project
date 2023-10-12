package user.app;

import dto.*;
import dto.gridView.GridViewDTO;
import dto.result.EntityPopulationByTicksDTO;
import dto.result.HistogramDTO;
import dto.result.PropertyAvaregeValueDTO;
import dto.result.PropertyConstistencyDTO;
import dto.world.WorldDTO;
import dto.world.WorldsDTO;
import engine.Engine;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import okhttp3.OkHttpClient;
import requests.UserRequest;
import user.UserApplication;
import user.details.scene.DetailsController;
import user.execution.scene.NewExecutionController;
import user.requests.RequestsController;
import engine.EngineImpl;
import javafx.application.Platform;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import user.results.scene.ResultsController;
import user.tasks.FetchWorldsDetailsTimer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Timer;

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
    @FXML private AnchorPane simulationDetailsComponent;
    @FXML private DetailsController simulationDetailsComponentController;
    @FXML private AnchorPane requestsComponent;
    @FXML private RequestsController requestsComponentController;
    @FXML private AnchorPane executionComponent;
    @FXML private NewExecutionController executionComponentController;
    @FXML private AnchorPane resultsComponent;
    @FXML private ResultsController resultsComponentController;
    @FXML private Label usernameLabel;

    public final static int REFRESH_RATE = 1000;
    private Timer fetchWorldsDetailsTimer;
    private FetchWorldsDetailsTimer fetchWorldsDetailsTimerTask;

    private final EngineImpl engineImpl = new EngineImpl();
    private final Connection connection = new Connection(this);
    private final SimpleBooleanProperty isXMLLoaded;
    private final SimpleBooleanProperty isSimulationExecuted;
    private final SimpleStringProperty username;

    public enum Tab {
        SIMULATION_DETAILS, REQUESTS, EXECUTION, RESULTS
    };
    private static final List<String> cssList =
            new ArrayList<>(Arrays.asList(
                    "Application.css", "DarkMode-theme.css",
                    "HappyMode-theme.css", "OrangeApplication.css",
                    "GreenApplication.css"));


    public AppController() {
        this.isXMLLoaded = new SimpleBooleanProperty(false);
        this.isSimulationExecuted = new SimpleBooleanProperty(false);
        this.username = new SimpleStringProperty("");
    }

    @FXML public void initialize(){
        setColorThemeComponents();
        if (simulationDetailsComponentController != null && requestsComponentController != null
                && executionComponentController != null && resultsComponentController != null
                && resultsComponentController.getSimulationComponentController() != null
                && resultsComponentController.getSimulationComponentController().getInformationComponentController() != null) {
            simulationDetailsComponentController.setAppController(this);
            requestsComponentController.setAppController(this);
            executionComponentController.setAppController(this);
            resultsComponentController.setAppController(this);
            resultsComponentController.getSimulationComponentController().setAppController(this);
            resultsComponentController.getSimulationComponentController().getInformationComponentController().setAppController(this);
        }
        usernameLabel.textProperty().bind(username);
        Platform.runLater(() -> {
            // Set applicationScrollPane to be the same size as the window
            applicationScrollPane.prefWidthProperty().bind(UserApplication.getStage().widthProperty());
            applicationScrollPane.prefHeightProperty().bind(UserApplication.getStage().heightProperty());
            applicationScrollPane.setFitToWidth(true);
            applicationScrollPane.setFitToHeight(true);

            this.fetchWorldsDetailsTimer = new Timer();
            this.fetchWorldsDetailsTimerTask = new FetchWorldsDetailsTimer(this, connection.getClient());
            this.fetchWorldsDetailsTimer.scheduleAtFixedRate(fetchWorldsDetailsTimerTask, 0, REFRESH_RATE);

            Runtime.getRuntime().addShutdownHook(new Thread(() -> {
                //engineImpl.deleteInDepthMemoryFolder();
                //resultsComponentController.stopExecutorService();
                //engineImpl.stopThreadPool();
                //todo: in the logout if needed, use a function that will stop all the threads and the executor service
                connection.sendLogOut();
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
            case SIMULATION_DETAILS:
                tabPane.getSelectionModel().select(0);
                break;
            case REQUESTS:
                tabPane.getSelectionModel().select(1);
                break;
            case EXECUTION:
                tabPane.getSelectionModel().select(2);
                break;
            case RESULTS:
                tabPane.getSelectionModel().select(3);
                break;
        }
    }

    public void tabActivation(Tab tab, boolean isActivated) {
        switch (tab) {
            case SIMULATION_DETAILS:
                tabPane.getTabs().get(0).setDisable(!isActivated);
                break;
            case REQUESTS:
                tabPane.getTabs().get(1).setDisable(!isActivated);
                break;
            case EXECUTION:
                tabPane.getTabs().get(2).setDisable(!isActivated);
                break;
            case RESULTS:
                tabPane.getTabs().get(3).setDisable(!isActivated);
                break;
        }
    }

    public void updateNewExecutionByPrevSimulation(int simulationID) {
        EnvVariablesValuesDTO envVariablesValuesDTO = engineImpl.getEnvVariablesValuesDTO(simulationID);
        EntitiesPopulationDTO entityPopulationDTO = engineImpl.getEntityPopulationDTO(simulationID);
        //newExecutionComponentController.fillEnvVariablesInputVBox(envVariablesValuesDTO);
        //newExecutionComponentController.fillEntityPopulationInputVBox(entityPopulationDTO);
    }

    public TabPane getTabPane(){ return tabPane; }

    public SimulationResultByAmountDTO getSimulationResultByAmountDTO(int simulationID) {
        return engineImpl.getSimulationResultByAmountDTO(simulationID);
    }

    public WorldDTO getWorldDTO(int simulationID) {
        return engineImpl.getWorldDTO(simulationID);
    }

    public HistogramDTO getHistogramDTO(int simulationID, String entityName, String propertyName) {
        return engineImpl.getHistogramDTO(simulationID, entityName, propertyName);
    }

    public GridViewDTO getGridViewDTO(int simulationID) {
        return engineImpl.getGridViewDTO(simulationID);
    }

    public boolean isSimulationCompleted(int simulationID) {
        return engineImpl.isSimulationCompleted(simulationID);
    }

    public PropertyConstistencyDTO getPropertyConsistencyDTO(int currentSimulationID, String entityName, String propertyName) {
        return engineImpl.getPropertyConsistencyDTO(currentSimulationID, entityName, propertyName);
    }

    public PropertyAvaregeValueDTO getPropertyAvaregeValueDTO(int currentSimulationID, String entityName, String propertyName) {
        return engineImpl.getPropertyAvaregeValueDTO(currentSimulationID, entityName, propertyName);
    }

    public EntityPopulationByTicksDTO getEntityPopulationByTicksDTO(int simulationID) {
        return engineImpl.getEntityPopulationByTicksDTO(simulationID);
    }

    public void setPreviousTick(int simulationID) {
        engineImpl.setPreviousTick(simulationID);
    }

    public void getToNextTick(int simulationID) {
        engineImpl.getToNextTick(simulationID);
    }

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

    public void setWorldsDetails(WorldsDTO worldsDTO) {
        simulationDetailsComponentController.updateDetailsTreeView(worldsDTO);
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

    public void setOkHttpClient(OkHttpClient client) {
        connection.setClient(client);
    }

    public void setUserName(String text) {
        this.username.set(text);
    }

    public Connection getConnection() {
        return connection;
    }

    public String getUsername() {
        return username.get();
    }

    public NewExecutionController getExecutionController() {
        return executionComponentController;
    }

    public ResultsController getResultsController() {
        return resultsComponentController;
    }
}