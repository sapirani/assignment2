package main.data;

import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class ShowSystem
{
    private Facade facade = Facade.getInstance();

    private final List<String> main_menu_instructions;
    private final List<MenuHandler> main_menu_handler;

    private Scanner scanner;

    public ShowSystem()
    {
        this.scanner = new Scanner(System.in);
        main_menu_instructions = new LinkedList<>();
        main_menu_handler = new LinkedList<>();
        manageMainMenu();
    }

    private void manageMainMenu()
    {
        this.main_menu_instructions.add("Sign in as administrative user");
        this.main_menu_instructions.add("Sign in as Pais member only");
        this.main_menu_instructions.add("Login");
        this.main_menu_instructions.add("Logout");
        this.main_menu_instructions.add("Add Show");
        this.main_menu_instructions.add("Order sits");
        this.main_menu_instructions.add("Exit");

        this.main_menu_handler.add(this::signInAsAdministrativeUser);
        this.main_menu_handler.add(this::signInAsPaisMemberUser);
        this.main_menu_handler.add(this::login);
        this.main_menu_handler.add(this::logout);
        this.main_menu_handler.add(this::addShow);
        this.main_menu_handler.add(this::orderSits);
        this.main_menu_handler.add(this::quit);
    }

    /**
     * Generic function for handling menu.
     * Displays the menu instructions, gets the chosen option, and run the right function
     */
    public void runMenu()
    {
        int option = getValidMenuOption("Main System", main_menu_instructions);
        System.out.println();
        main_menu_handler.get(option - 1).handleChoice();
        if(option == main_menu_instructions.size())
            System.exit(0);
        else
        {
            runMenu();
        }
    }

    /**
     * Helper function for getting valid option of menu - get option in the valid range
     * Loops until getting valid option
     * @param menu_name - the running menu name
     * @param menu_instructions - the running menu instructions
     * @return valid menu option
     */
    protected int getValidMenuOption(String menu_name, List<String> menu_instructions)
    {
        int value = Integer.MIN_VALUE;
        // loop until getting valid input
        while (value < 1 || value > menu_instructions.size()) {
            try {
                printMenuForUser(menu_name, menu_instructions);
                value = Integer.parseInt(scanner.nextLine());
                if (value >= 1 && value <= menu_instructions.size()) // if got valid input
                    break;
                System.out.println(menuCodeError());
            } catch (Exception e) {
                System.out.println(menuCodeError());
            }
        }
        return value;
    }

    /**
     * Prints the menu instructions
     * @param menu_name - the name of the running menu
     * @param menu_instructions - the instructions of the running menu
     */
    protected void printMenuForUser(String menu_name, List<String> menu_instructions)
    {
        System.out.println("*** " + menu_name + " Menu ***");
        System.out.println("Please choose option:");
        for (int i = 0; i < menu_instructions.size(); i++)
        {
            System.out.println((i + 1) + ") " + menu_instructions.get(i));
        }
        System.out.print("Enter your choice: ");
    }

    /**
     * Helper function in order to get error message when the user choose wrong option from the menu
     */
    protected String menuCodeError()
    {
        return "\nChoose option from the displayed menu!\n";
    }

    public void signInAsAdministrativeUser()
    {
        System.out.println("Insert Username: ");
        String username = scanner.nextLine();

        System.out.println("Insert Password: ");
        String password = scanner.nextLine();

        System.out.println("Insert the city you work in: ");
        String city = scanner.nextLine();

        int memberId = getIntegerInputFromUser("Enter your pais member id (if you don't have one, enter -1): ", "Member id must be integer.");

        Response response = facade.signIn(username, password, city, memberId, true);
        if(response.errorOccurred())
            System.out.println("Sign up failed. \n" + response.getErrorMessage() + "\nPlease try again.");
        else
            System.out.println("Signed up successfully to the system.");
    }

    public void signInAsPaisMemberUser()
    {
        System.out.println("Insert Username: ");
        String username = scanner.nextLine();

        System.out.println("Insert Password: ");
        String password = scanner.nextLine();

        System.out.println("Insert the city you work/live in: ");
        String city = scanner.nextLine();

        int memberId = getIntegerInputFromUser("Enter your pais member id: ", "Member id must be integer.");

        Response response = facade.signIn(username, password, city, memberId, false);
        if(response.errorOccurred())
            System.out.println("Sign up failed. \n" + response.getErrorMessage() + "\nPlease try again.");
        else
            System.out.println("Signed up successfully to the system.");
    }

    public void login()
    {
        System.out.println("Insert Username: ");
        String username = scanner.nextLine();

        System.out.println("Insert Password: ");
        String password = scanner.nextLine();

        Response response = facade.login(username, password);
        if(response.errorOccurred())
            System.out.println("Login failed. \n" + response.getErrorMessage() + "\nPlease try again.");
        else
            System.out.println("Logged in successfully to the system.");
    }

    public void logout()
    {
        Response response = facade.logout();
        if(response.errorOccurred())
            System.out.println("Logout failed. \n" + response.getErrorMessage());
        else
            System.out.println("Logged out successfully from the system.");
    }

    public void addShow()
    {

    }

    public void orderSits()
    {

    }

    public void quit()
    {
        System.out.println("Good bye :)\n");
    }

    private int getIntegerInputFromUser(String message, String error_message)
    {
        boolean flag = false;
        int value = Integer.MIN_VALUE;
        // loop until getting valid option
        while (!flag) {
            try {
                System.out.println(message);
                value = Integer.parseInt(scanner.nextLine());
                flag = true; // got valid input
            } catch (Exception e) {
                System.out.println(error_message);
            }
        }
        return value;
    }
}
