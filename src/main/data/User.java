package main.data;

import java.util.HashMap;
import java.util.Map;

public class User
{
    private String username;
    private String password;
    private int memberId;
    private boolean isAdmin;
    private String city;
    private Map<Integer, OrderInfo> orders;

    public User(String username, String password, String city, int memberId, boolean isAdmin)
    {
        this.username = username;
        this.password = password;
        this.memberId = memberId;
        this.city = city;
        this.isAdmin = isAdmin;
        this.orders = new HashMap<>();
    }

    public String getUsername()
    {
        return this.username;
    }

    public String getPassword()
    {
        return this.password;
    }

    public int getMemberId()
    {
        return this.memberId;
    }

    public boolean getIsAdmin()
    {
        return this.isAdmin;
    }

    public Map<Integer,OrderInfo> getOrders()
    {
        return this.orders;
    }
}
