package main.data;

import java.time.LocalTime;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class ShowController
{
    private Map<Integer, ShowInfo> shows;
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
        this.showId_counter = 1;
    }

    public boolean addShow(String show_name, String description, String city, String hall, LocalTime time, boolean clicked_no_time,long show_date, long lastOrderDate, double price)
    {
        // TODO: ask where the user checking should be?
        if (checkIfShowExists(show_name))
            throw new IllegalArgumentException("Show already exists. Please try again.");

        if(!clicked_no_time && time == null)
            throw new IllegalArgumentException("You must click the botton titled \"The time has not yet been set\"");
        ShowInfo new_show = new ShowInfo();
        this.shows.put(showId_counter, new_show);
        showId_counter++;
        return true;
    }

    private boolean checkIfShowExists(String show_name)
    {
        // TODO: can be show with the same name? if so, check by id?
        return this.shows.containsKey(show_name);
    }
}
