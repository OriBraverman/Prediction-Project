package requests.manager;

import requests.UserRequest;

import java.util.Map;

public interface RequestsManager {
    int addRequest(String simulationName, int executionsCount);

    void setRequestStatus(int id, boolean approved);

    UserRequest getRequest(int id);
}
