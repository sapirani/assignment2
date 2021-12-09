package main.data;

import java.util.HashMap;
import java.util.Map;

public class UserController
{
    private Map<String, User> users;

    /**
     * Implementation of singleton
     */
    private static class UserControllerHolder
    {
        private static UserController INSTANCE = new UserController();
    }

    public static UserController getInstance()
    {
        return UserControllerHolder.INSTANCE;
    }

    public UserController()
    {
        this.users = new HashMap<>();
    }

    public boolean signInAsAdministrativeUser(String new_username, String password, int memberId)
    {
        if(checkIfUsernameExists(new_username))
            throw new IllegalArgumentException("Username already taken. Pleas try again.");

        User new_user = new User(new_username, password,memberId, true);
        this.users.put(new_username, new_user);
        return true;
    }

    public boolean signInAsRegularUser(String new_username, String password, int memberId)
    {
        if(checkIfUsernameExists(new_username))
            throw new IllegalArgumentException("Username already taken. Please try again.");

        User new_user = new User(new_username, password,memberId, false);
        this.users.put(new_username, new_user);
        return true;
    }

    public boolean login(String username, String password)
    {
        User current = this.users.get(username);
        if(current == null)
            throw new IllegalArgumentException("Username doesn't exists in the system. Please try again.");
        else if(current.getPassword().equals(password))
        {
            // TODO: login process, change current login user
            return true;
        }
        throw new IllegalArgumentException("Password does'nt match the username. Please try again.");
    }


    private boolean checkIfUsernameExists(String username)
    {
        return (users.containsKey(username));
    }
}
