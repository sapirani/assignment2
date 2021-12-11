package main.data;

public class Hall
{
    private String hall_name;
    private int number_of_chairs;

    public Hall(String name, int number_of_chairs)
    {
        this.hall_name = name;
        this.number_of_chairs = number_of_chairs;
    }

    public String getHallName()
    {
        return this.hall_name;
    }

    public int getNumberOfChairs()
    {
        return this.number_of_chairs;
    }
}
