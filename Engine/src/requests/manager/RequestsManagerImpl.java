package requests.manager;

import http.url.Client;
import requests.RequestStatus;
import requests.UserRequest;
import world.factors.termination.Termination;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class RequestsManagerImpl implements RequestsManager {
    private int nextId = 0;
    private Map<Integer, UserRequest> requests;

    public RequestsManagerImpl() {
        this.requests = new HashMap<>();
    }

    @Override
    public int addRequest(String username, String simulationName, int executionsCount, Termination termination) {
        nextId++;
        requests.put(nextId, new UserRequest(nextId, username, simulationName, termination, executionsCount));
        return nextId;
    }

    @Override
    public void setRequestStatus(int id, RequestStatus status) {
        requests.get(id).setRequestStatus(status);
    }

    @Override
    public UserRequest getRequest(int id) {
        return requests.get(id);
    }

    @Override
    public List<UserRequest> getRequests(String usernameFromSession, Client typeOfClient) {
        if (typeOfClient == Client.ADMIN) {
            return requests.values().stream().collect(Collectors.toList());
        } else {
            return requests.values().stream()
                    .filter(request -> request.getUsername().equals(usernameFromSession))
                    .collect(Collectors.toList());
        }
    }
}
