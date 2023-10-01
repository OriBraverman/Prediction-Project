package user;

import requests.UserRequest;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class User {
    private String username;
    private Map<Integer, UserRequest> requests;

    public User(String username) {
        this.username = username;
        this.requests = new HashMap<>();
    }

    public String getUsername() {
        return username;
    }

    public Map<Integer, UserRequest> getRequests() {
        return requests;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setRequests(Map<Integer, UserRequest> requests) {
        this.requests = requests;
    }

    public void addRequest(UserRequest request) {
        requests.put(request.getId(), request);
    }

    public void removeRequest(int id) {
        requests.remove(id);
    }

    public List<UserRequest> getAllApprovedRequests() {
        return this.requests.values()
                .stream()
                .filter(UserRequest::isApproved)
                .collect(Collectors.toList());
    }

    public List<UserRequest> getAllRejectedRequests() {
        return this.requests.values()
                .stream()
                .filter(request -> !request.isApproved())
                .collect(Collectors.toList());
    }
}
