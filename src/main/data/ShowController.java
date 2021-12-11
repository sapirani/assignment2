package main.data;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ShowController
{
    private Map<Integer, ShowInfo> shows;
    private Map<String, List<Hall>> city_and_hall;
    private int showId_counter;

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
    }

    public int addShow(User user,String show_name, String description, String city, String hall, LocalTime time, boolean clicked_no_time,long show_date, long lastOrderDate, double price)
    {
        if(user == null || !user.getIsAdmin())
            throw new IllegalArgumentException("You must be Administrative User in order to add shows to the system.");

        if(user.getCity().equals(city))
            throw new IllegalArgumentException("The show hall must be in the city you work.1");
        if(!checkIfCityContainsHall(this.city_and_hall.get(city), hall))
            throw new IllegalArgumentException("The show hall must be in the city you work.2");

        if(!clicked_no_time && time == null)
            throw new IllegalArgumentException("You must click the botton titled \"The time has not yet been set\"");

        if(lastOrderDate > show_date)
            throw new IllegalArgumentException("The last order date must be befor the show starts.");

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
