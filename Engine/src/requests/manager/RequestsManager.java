package requests.manager;

import requests.RequestStatus;
import requests.UserRequest;
import world.factors.termination.Termination;

import java.util.Map;

public interface RequestsManager {
    int addRequest(String simulationName, int executionsCount, Termination termination);

    void setRequestStatus(int id, RequestStatus status);

    UserRequest getRequest(int id);
}
