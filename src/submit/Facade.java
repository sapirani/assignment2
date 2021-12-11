package submit;

import javafx.util.Pair;
import submit.ShowPackage.OrderInfo;
import submit.ShowPackage.ShowController;
import submit.ShowPackage.ShowInfo;
import submit.UserPackage.User;
import submit.UserPackage.UserController;

import java.util.List;

public class Facade
{
    private final UserController user_controller = UserController.getInstance();
    private final ShowController show_controller = ShowController.getInstance();

    /**
     * Implementation of singleton
     */
    private static class FacadeHolder
    {
        private static final Facade INSTANCE = new Facade();
    }

    public static Facade getInstance()
    {
        return Facade.FacadeHolder.INSTANCE;
    }

    public Response signUp(String new_username, String password, String city, int memberId, boolean isAdmin)
    {
        try
        {
            this.user_controller.signUp(new_username, password, city, memberId, isAdmin);
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
            return new Response(e.getMessage());
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

    public Response addShow(ShowInfo show, boolean hasClicked)
    {
        try
        {
            User current_user = this.user_controller.getLogedInUser();
            int show_id = this.show_controller.addShow(current_user, show, hasClicked);
            return new Response(show_id);
        }
        catch (Exception e)
        {
            return new Response(e.getMessage());
        }
    }

    public Response reserveMemberChairs(int show_id, int sit_from, int sit_to)
    {
        try
        {
            return new Response(this.show_controller.reservedMemberChairs(show_id, sit_from, sit_to));
        }
        catch (Exception e)
        {
            return new Response(e.getMessage());
        }
    }

    public Response getAvailableChairsInShow(int show_id)
    {
        try
        {
            Pair<List<Integer>, List<Integer>> availableChairs = this.show_controller.getAvailableChairsInShow(show_id);
            return new Response(availableChairs);
        }
        catch (Exception e)
        {
            return new Response(e.getMessage());
        }
    }

    public Response addOrder(OrderInfo order)
    {
        try
        {
            User currentUser = this.user_controller.getLogedInUser();
            return new Response(this.show_controller.addOrder(currentUser, order));
        }
        catch (Exception e)
        {
            return new Response(e.getMessage());
        }
    }

    public Response getWaitings(int show_id)
    {
        try
        {
            return new Response(this.show_controller.getWaitings(show_id));
        }
        catch (Exception e)
        {
            return new Response(e.getMessage());
        }
    }
}
