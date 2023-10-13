package admin.tasks;


import admin.executionHistory.scene.ExecutionsHistoryController;

import java.util.TimerTask;

public class FetchSimulationTimer extends TimerTask {
    private final ExecutionsHistoryController executionsHistoryController;

    public FetchSimulationTimer(ExecutionsHistoryController executionsHistoryController) {
        this.executionsHistoryController = executionsHistoryController;
    }

    @Override
    public void run() {
        executionsHistoryController.updateSimulation();
    }
}
