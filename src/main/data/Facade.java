package main.data;

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
            return new Response(e.getMessage()); // TODO: what value should return? need Respone object?
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
            return new Response(e.getMessage()); // TODO: what value should return? need Respone object?
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
}
