package user.tasks;


import user.results.scene.ResultsController;

import java.util.TimerTask;

public class FetchSimulationTimer extends TimerTask {
    private final ResultsController resultsController;

    public FetchSimulationTimer(ResultsController resultsController) {
        this.resultsController = resultsController;
    }

    @Override
    public void run() {
        resultsController.updateSimulation();
    }
}
