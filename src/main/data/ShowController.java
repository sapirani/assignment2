package main.data;

import javafx.util.Pair;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

public class ShowController
{
    private Map<Integer, ShowInfo> shows;
    private Map<String, List<Hall>> city_and_hall;
    private int showId_counter;
    private int orderId_counter;

    /**
     * Implementation of singleton
     */
    private static class ShowControllerHolder
    {
        private static ShowController INSTANCE = new ShowController();
    }

    public static ShowController getInstance()
    {
        return ShowControllerHolder.INSTANCE;
    }

    public ShowController()
    {
        this.shows = new HashMap<>();
        this.city_and_hall = new HashMap<>();
        this.showId_counter = 1;
        this.orderId_counter = 1;
    }

    public int addShow(User user,String show_name, String description, String city, String hall, LocalTime time, boolean clicked_no_time,long show_date, long lastOrderDate, double price)
    {
        if(user == null || !user.getIsAdmin())
            throw new IllegalArgumentException("You must be Administrative User in order to add shows to the system.");

        if(!user.getCity().equals(city) || !checkIfCityContainsHall(this.city_and_hall.get(city), hall))
            throw new IllegalArgumentException("The show hall must be in the city you work.");

        if(!clicked_no_time && time == null)
            throw new IllegalArgumentException("You must click the botton titled \"The time has not yet been set\"");

        if(lastOrderDate > show_date)
            throw new IllegalArgumentException("The last order date must be befor the show starts.");
        try
        {
            long today = System.currentTimeMillis();
            if(show_date < today)
                throw new IllegalArgumentException("The show date must be new, after today.");
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException(e.getMessage());
        }

        int show_id = showId_counter;
        int number_of_sits = getNumberOfChairsInHall(this.city_and_hall.get(city), hall);
        ShowInfo new_show = new ShowInfo(show_name, city, hall, description, time, show_date, lastOrderDate, price, number_of_sits);
        this.shows.put(show_id, new_show);
        showId_counter++;
        return show_id;
    }

    public boolean addCity(String city)
    {
        if(this.city_and_hall.containsKey(city))
            throw new IllegalArgumentException("This city already exists in the system.");

        this.city_and_hall.put(city, new LinkedList<>());
        return true;
    }

    public boolean addHall(String city, String hall, int number_of_sits)
    {
        if(!this.city_and_hall.containsKey(city))
            throw new IllegalArgumentException("This city does not exists in the system.");

        if(checkIfCityContainsHall(this.city_and_hall.get(city), hall))
            throw new IllegalArgumentException("This hall already exists in the system.");

        this.city_and_hall.get(city).add(new Hall(hall,number_of_sits));
        return true;
    }

    public boolean reservedMemberChairs(int show_id, int sit_from, int sit_to)
    {
        if(!this.shows.containsKey(show_id))
            throw new IllegalArgumentException("This show does'nt exist in the system.");

        ShowInfo current_show = this.shows.get(show_id);
        if(!current_show.remained_regular_sits.contains(sit_from) || !current_show.remained_regular_sits.contains(sit_to))
            throw new IllegalArgumentException("The indexes aren't available.");

        if(current_show.checkIfThereAreEnoughChairs(sit_from, sit_to))
        {
            current_show.reserveMemberChairs(sit_from,sit_to);
        }

        return true;
    }

    public Pair<List<Integer>, List<Integer>> getAvailableChairsInShow(int show_id)
    {
        ShowInfo currentShow = this.shows.get(show_id);
        if(currentShow == null)
            throw new IllegalArgumentException("This show does'nt exist in the system.");

        return new Pair<List<Integer>, List<Integer>>(currentShow.remained_regular_sits, currentShow.remained_member_sits);
    }

    public int addOrder(User currentUser, int show_id, String name, String phone_number, int[] chairs, int memberId)
    {
        if(name == null || phone_number == null || phone_number == "" || chairs == null || chairs.length == 0)
            throw new IllegalArgumentException("Order must include name,phone number and chairs!");

        String order_name = name;
        if(currentUser != null && !name.equals(""))
            throw new IllegalArgumentException("You are logged in so your name is known.");
        else if(currentUser != null && name.equals(""))
            order_name = currentUser.getUsername();

        ShowInfo currentShow = this.shows.get(show_id);
        if(currentShow == null)
            throw new IllegalArgumentException("This show does'nt exist in the system.");

        try
        {
            long today = System.currentTimeMillis();
            if (currentShow.lastOrderDate < today)
                throw new IllegalArgumentException("You can't order chairs to this show. The last order day has passed.");
        }
        catch (Exception e)
        {
            throw new IllegalArgumentException(e.getMessage());
        }

        int member_id = -1;
        if(currentUser == null && memberId > 0)
            member_id = memberId;
        else if(currentUser != null)
            member_id = currentUser.getMemberId();
        else if(memberId != -1)
            throw new IllegalArgumentException("The given number does'nt match Pais member.");

        if(currentShow.checkIfContainsMemberChairs(chairs) && member_id == -1)
            throw new IllegalArgumentException("You are not a Pais member. If you are Pais member, please login first. ");

        currentShow.orderChairs(chairs);
        OrderInfo new_order = new OrderInfo(show_id, order_name, phone_number, chairs, member_id);
        int order_id = orderId_counter;
        boolean flag = true;
        if(!currentShow.hastime)
        {
           flag = currentShow.addUserToInform(new_order); // if flag = false then the user has 2 orders for the same show, we combine them.
        }

        if(flag)
            currentUser.addOrder(order_id, new_order);
        orderId_counter++;
        return order_id;
    }

    public List<OrderInfo> getWaitings(int show_id)
    {
        ShowInfo currentShow = this.shows.get(show_id);
        if(currentShow == null)
            throw new IllegalArgumentException("This show does'nt exist in the system.");

        return currentShow.userstoinform; // TODO: what need to return?
    }

    private int getNumberOfChairsInHall(List<Hall> halls, String hall)
    {
        for (Hall hall_object: halls)
        {
            if(hall_object.getHallName().equals(hall))
                return hall_object.getNumberOfChairs();
        }
        return 0;
    }

    private boolean checkIfCityContainsHall(List<Hall> halls, String hall)
    {
        for (Hall hall_object: halls)
        {
            if(hall_object.getHallName().equals(hall))
                return true;
        }
        return false;
    }
}
