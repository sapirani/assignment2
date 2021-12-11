package submit.UserPackage;

import submit.ShowPackage.OrderInfo;

import java.util.HashMap;
import java.util.Map;

public class User
{
    private String username;
    private String password;
    private int memberId;
    private boolean isAdmin;
    private String city;
    public Map<Integer, OrderInfo> orders = new HashMap<>();

    public User(String username, String password, String city, int memberId, boolean isAdmin)
    {
        this.username = username;
        this.password = password;
        this.memberId = memberId;
        this.city = city;
        this.isAdmin = isAdmin;
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

    public String getCity()
    {
        return this.city;
    }

    public void addOrder(int order_id, OrderInfo new_order)
    {
        this.orders.put(order_id, new_order);
    }
}
