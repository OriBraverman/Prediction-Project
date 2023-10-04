package requests.manager;

import requests.RequestStatus;
import requests.UserRequest;
import world.factors.termination.Termination;

import java.util.HashMap;
import java.util.Map;

public class RequestsMangaerImpl implements RequestsManager {
    private int nextId = 0;
    private Map<Integer, UserRequest> requests;

    public RequestsMangaerImpl() {
        this.requests = new HashMap<>();
    }

    @Override
    public int addRequest(String simulationName, int executionsCount, Termination termination) {
        nextId++;
        requests.put(nextId, new UserRequest(nextId, simulationName, termination, executionsCount));
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

}
