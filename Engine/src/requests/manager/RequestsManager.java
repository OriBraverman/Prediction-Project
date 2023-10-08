package requests.manager;

import http.url.Client;
import requests.RequestStatus;
import requests.UserRequest;
import world.factors.termination.Termination;

import java.util.List;
import java.util.Map;

public interface RequestsManager {
    int addRequest(String username, String simulationName, int executionsCount, Termination termination);

    void setRequestStatus(int id, RequestStatus status);

    UserRequest getRequest(int id);

    List<UserRequest> getRequests(String usernameFromSession, Client typeOfClient);
}
