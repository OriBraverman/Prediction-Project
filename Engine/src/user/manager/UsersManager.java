package user.manager;

import http.url.Client;
import requests.UserRequest;
import user.User;

import java.util.List;
import java.util.Map;

public interface UsersManager {

    void addUser(String username);

    void removeUser(String username);

    User getUser(String username);

    Map<String, User> getUsers();

    boolean isUserExists(String username);

    boolean isAdminLoggedIn();

    void setAdminLoggedIn(boolean adminLoggedIn);
}
