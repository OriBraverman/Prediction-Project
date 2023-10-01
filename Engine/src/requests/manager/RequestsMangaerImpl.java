package requests.manager;

import requests.UserRequest;

import java.util.HashMap;
import java.util.Map;

public class RequestsMangaerImpl implements RequestsManager {
    private int nextId = 0;
    private Map<Integer, UserRequest> requests;

    public RequestsMangaerImpl() {
        this.requests = new HashMap<>();
    }

    @Override
    public int addRequest(String simulationName, int executionsCount) {
        nextId++;
        requests.put(nextId, new UserRequest(nextId, simulationName, executionsCount, false));
        return nextId;
    }

    @Override
    public void setRequestStatus(int id, boolean approved) {
        requests.get(id).setApproved(approved);
    }

    @Override
    public UserRequest getRequest(int id) {
        return requests.get(id);
    }

}
