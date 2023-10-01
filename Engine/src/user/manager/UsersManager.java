package user.manager;

import user.User;

import java.util.Map;

public interface UsersManager {

    void addUser(String username);

    void removeUser(String username);

    User getUser(String username);
    Map<String, User> getUsers();


}
