package main.data;

import java.util.HashMap;
import java.util.Map;

public class UserController
{
    private Map<String, User> users;
    private User loged_in_user;

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
        this.loged_in_user = null;
    }

    public boolean signIn(String new_username, String password, String city,int memberId, boolean isAdmin)
    {
        if(checkIfUsernameExists(new_username))
            throw new IllegalArgumentException("Username already taken. Pleas try again.\n");

        User new_user = new User(new_username, password, city,memberId, isAdmin);
        this.users.put(new_username, new_user);
        return true;
    }

    public boolean login(String username, String password)
    {
        User current = this.users.get(username);
        if(current == null)
            throw new IllegalArgumentException("Username doesn't exists in the system. Please try again.\n");
        else if(this.loged_in_user != null)
            throw new IllegalArgumentException("There is another user who is logged in. Please try again later\n.");
        else if(current.getPassword().equals(password))
        {
            this.loged_in_user = current;
            return true;
        }
        throw new IllegalArgumentException("Password does'nt match the username. Please try again.\n");
    }

    public boolean logout()
    {
        if(this.loged_in_user == null)
            throw new IllegalArgumentException("There is no one logged in yet.\n");

        this.loged_in_user = null;
        return true;
    }


    private boolean checkIfUsernameExists(String username)
    {
        return (users.containsKey(username));
    }
}
