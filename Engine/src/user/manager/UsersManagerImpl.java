package user.manager;

import user.User;

import java.util.HashMap;
import java.util.Map;

public class UsersManagerImpl implements UsersManager{
    private Map<String, User> users;

    public UsersManagerImpl() {
        this.users = new HashMap<>();
    }

    @Override
    public void addUser(String username, boolean isAdmin) {
        if (users.containsKey(username)){
            throw new IllegalArgumentException("Username already exists, please choose another one");
        }
        users.put(username, new User(username, isAdmin));
    }

    @Override
    public void removeUser(String username) {
        if (!users.containsKey(username)){
            throw new IllegalArgumentException("Username does not exist");
        }
        users.remove(username);
    }

    @Override
    public User getUser(String username) {
        if (!users.containsKey(username)){
            throw new IllegalArgumentException("Username does not exist");
        }
        return users.get(username);
    }

    @Override
    public Map<String, User> getUsers() {
        return users;
    }

    @Override
    public boolean isUserExists(String username) {
        return users.containsKey(username);
    }

    @Override
    public boolean isUserAdmin(String username) {
        if (!users.containsKey(username)){
            throw new IllegalArgumentException("Username does not exist");
        }
        return users.get(username).isAdmin();
    }

}
