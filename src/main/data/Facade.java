package main.data;

import java.time.LocalTime;

public class Facade
{
    private UserController user_controller = UserController.getInstance();
    private ShowController show_controller = ShowController.getInstance();

    /**
     * Implementation of singleton
     */
    private static class FacadeHolder
    {
        private static Facade INSTANCE = new Facade();
    }

    public static Facade getInstance()
    {
        return Facade.FacadeHolder.INSTANCE;
    }

    public Response signIn(String new_username, String password, String city,int memberId, boolean isAdmin)
    {
        try
        {
            this.user_controller.signIn(new_username, password, city, memberId, isAdmin);
            return new Response(true);
        }
        catch (Exception e)
        {
            return new Response(e.getMessage());
        }
    }

    public Response login(String new_username, String password)
    {
        try
        {
            this.user_controller.login(new_username, password);
            return new Response(true);
        }
        catch (Exception e)
        {
            return new Response(e.getMessage());
        }
    }

    public Response logout()
    {
        try
        {
            this.user_controller.logout();
            return new Response(true);
        }
        catch (Exception e)
        {
            return new Response(e.getMessage()); // TODO: what value should return? need Respone object?
        }
    }

    public Response addCity(String city)
    {
        try
        {
            return new Response(this.show_controller.addCity(city));
        }
        catch (Exception e)
        {
            return new Response(e.getMessage());
        }
    }

    public Response addHall(String city, String hall, int number_of_sits)
    {
        try
        {
            return new Response(this.show_controller.addHall(city, hall, number_of_sits));
        }
        catch (Exception e)
        {
            return new Response(e.getMessage());
        }
    }

    public Response addShow(String show_name, String city, String hall, String description,
                            long showDate, long last_date, double ticket_price, LocalTime show_time, boolean hasClicked)
    {
        try
        {
            User current_user = this.user_controller.getLogedInUser();
            int show_id = this.show_controller.addShow(current_user, show_name, description, city, hall, show_time, hasClicked, showDate, last_date, ticket_price);
            return new Response(show_id);
        }
        catch (Exception e)
        {
            return new Response(e.getMessage());
        }
    }
}
