package submit;

import main.bridge.Bridge;
import submit.ShowPackage.OrderInfo;
import submit.ShowPackage.ShowInfo;

import java.util.List;

public class RealBridge implements Bridge
{
    private Facade facade = Facade.getInstance();

    @Override
    public void addCity(String city)
    {
        this.facade.addCity(city);
    }

    @Override
    public void addHall(String city, String hall, int sits)
    {
        this.facade.addHall(city, hall, sits);
    }

    @Override
    public void addAdmin(String city, String user, String pass)
    {
        this.facade.signUp(user, pass, city, -1, true);
    }

    @Override
    public int addNewShow(String user, String pass, ShowInfo showInfo)
    {
        boolean has_clicked = showInfo.hastime ? false : true; // If the show doesn't have time, you must click the matching bottom.
        this.facade.login(user, pass); // You must login as admin user

        Response r = this.facade.addShow(showInfo, has_clicked);
        this.facade.logout();
        if(!r.errorOccurred())
            return (int) r.getValue();

        return -1;
    }

    @Override
    public void reserveMemberChairs(int showID, int from, int to)
    {
        this.facade.reserveMemberChairs(showID, from,to);
    }

    @Override
    public int newOrder(OrderInfo order)
    {
        Response r = this.facade.addOrder(order);
        if(!r.errorOccurred())
            return (int) r.getValue();

        return -1;
    }

    @Override
    public List<OrderInfo> getWaitings(int id)
    {
        Response r = this.facade.getWaitings(id);
        if(!r.errorOccurred())
            return (List<OrderInfo>) r.getValue();

        return null;
    }
}
