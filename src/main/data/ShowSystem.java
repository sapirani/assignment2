package main.data;

import javafx.util.Pair;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;
import java.util.Scanner;

public class ShowSystem
{
    private final Facade facade = Facade.getInstance();

    private final List<String> main_menu_instructions;
    private final List<MenuHandler> main_menu_handler;

    private final Scanner scanner;

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
        this.main_menu_instructions.add("Add city to the system");
        this.main_menu_instructions.add("Add hall to the system");
        this.main_menu_instructions.add("Add Show");
        this.main_menu_instructions.add("Reserve Member Chairs");
        this.main_menu_instructions.add("Order sits");
        this.main_menu_instructions.add("Exit");

        this.main_menu_handler.add(this::signInAsAdministrativeUser);
        this.main_menu_handler.add(this::signInAsPaisMemberUser);
        this.main_menu_handler.add(this::login);
        this.main_menu_handler.add(this::logout);
        this.main_menu_handler.add(this::addCity);
        this.main_menu_handler.add(this::addHall);
        this.main_menu_handler.add(this::addShow);
        this.main_menu_handler.add(this::reserveMemberChairs);
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

        int memberId = getIntegerInputFromUser("Insert your Pais member id (if you don't have one, enter -1): ", "Member id must be integer.");

        Response response = facade.signIn(username, password, city, memberId, true);
        if(response.errorOccurred())
            System.out.println("Sign up failed. \n" + response.getErrorMessage() + "\nPlease try again.\n");
        else
            System.out.println("Signed up successfully to the system.\n");
    }

    public void signInAsPaisMemberUser()
    {
        System.out.println("Insert Username: ");
        String username = scanner.nextLine();

        System.out.println("Insert Password: ");
        String password = scanner.nextLine();

        System.out.println("Insert the city you work/live in: (if you don't want to, enter x)");
        String city = scanner.nextLine();

        int memberId = getIntegerInputFromUser("Insert your Pais member id: ", "Member id must be integer.");

        Response response = facade.signIn(username, password, city, memberId, false);
        if(response.errorOccurred())
            System.out.println("Sign up failed. \n" + response.getErrorMessage() + "\nPlease try again.\n");
        else
            System.out.println("Signed up successfully to the system.\n");
    }

    public void login()
    {
        System.out.println("Insert Username: ");
        String username = scanner.nextLine();

        System.out.println("Insert Password: ");
        String password = scanner.nextLine();

        Response response = facade.login(username, password);
        if(response.errorOccurred())
            System.out.println("Login failed. \n" + response.getErrorMessage() + "\n");
        else
            System.out.println("Logged in successfully to the system. \n");
    }

    public void logout()
    {
        Response response = facade.logout();
        if(response.errorOccurred())
            System.out.println("Logout failed. \n" + response.getErrorMessage());
        else
            System.out.println("Logged out successfully from the system. \n");
    }

    public void addCity()
    {
        System.out.println("Insert City name: ");
        String city = scanner.nextLine();

        Response response = this.facade.addCity(city);
        if(response.errorOccurred())
            System.out.println("Failed to add new city to the system. \n" + response.getErrorMessage() + "\nPlease try again.\n");
        else
            System.out.println("The new city added to the system.\n");

    }

    public void addHall()
    {
        System.out.println("Insert City name of hall: ");
        String city = scanner.nextLine();

        System.out.println("Insert the hall name: ");
        String hall = scanner.nextLine();

        int number_of_sits = getIntegerInputFromUser("Insert number of available sits in the hall: ", "Number of int must be integer.");

        Response response = this.facade.addHall(city, hall, number_of_sits);
        if(response.errorOccurred())
            System.out.println("Failed to add new hall to the system. \n" + response.getErrorMessage() + "\nPlease try again.\n");
        else
            System.out.println("The new hall added to the system.\n");
    }

    public void addShow()
    {
        System.out.println("Insert Show name: ");
        String show_name = scanner.nextLine();
        System.out.println("Insert City: ");
        String city = scanner.nextLine();
        System.out.println("Insert Hall: ");
        String hall = scanner.nextLine();
        System.out.println("Insert description: ");
        String desc = scanner.nextLine();
        long show_date = getValidDate("Insert show date: ", "The show date must be from the format dd.mm.yyyy"); //  scanner.nextLong();
        long last_order_date = getValidDate("Insert last order date: ", "The last order date must be from the format dd.mm.yyyy"); //  scanner.nextLong();
        double ticket_price = getDoubleInputFromUser("Insert ticket price: ", "Price must be positive number.");

        Pair<Boolean, Boolean> time_operation = handleTimeOfShow();
        LocalTime time = null;
        if(time_operation.getKey())
        {
            time = getValidTime("Insert the show time: ", "The time format should be hh:mm:ss");
        }

        Response response = facade.addShow(show_name, city, hall, desc, show_date, last_order_date, ticket_price, time, time_operation.getValue());
        if(response.errorOccurred())
            System.out.println("Failed to add new show to the system. \n" + response.getErrorMessage() + "\nPlease try again.\n");
        else
            System.out.println("The new show added to the system. The show ID is: " + response.getValue() +"\n");
    }

    public void reserveMemberChairs()
    {
        int show_id = getIntegerInputFromUser("Insert show id: ", "Show id must be positive Integer.");
        int sit_from = getIntegerInputFromUser("Insert from where to reserve the sits: ", "From index must be positive Integer.");
        int sit_to = getIntegerInputFromUser("Insert until where to reserve the sits: ", "To index must be positive integer.");

        Response response = this.facade.reserveMemberChairs(show_id, sit_from, sit_to);
        if(response.errorOccurred())
            System.out.println("Failed to reserve chairs in the show. \n" + response.getErrorMessage() + "\nPlease try again.\n");
        else
            System.out.println("Reserved the chairs successfully. \n");
    }

    public void orderSits()
    {
        int show_id = getIntegerInputFromUser("Insert show id: ", "Show id must be positive Integer.");
        Response all_available_chairs = this.facade.getAvailableChairsInShow(show_id);
        if(all_available_chairs.errorOccurred())
            System.out.println("Failed to get the available chairs in the show.. \n" + all_available_chairs.getErrorMessage() + "\nPlease try again.\n");
        else
        {
            System.out.println("The available chairs are: ");
            System.out.println("Regular chairs: " + ((Pair) all_available_chairs.getValue()).getKey().toString());
            System.out.println("Pais Members chairs: " + ((Pair) all_available_chairs.getValue()).getValue().toString());
        }

        System.out.println("\nIf you'r not logged in to the system, enter your name. Else, press enter");
        String name = scanner.nextLine();
        System.out.println("Enter phone number: ");
        String phone_number = scanner.nextLine();
        System.out.println("Insert all the sits you want to order: ");
        int[] chairs = getAllChairs();

        Response response = this.facade.addOrder(show_id, name, phone_number, chairs);
        if(response.errorOccurred())
            System.out.println("Failed to order chairs to the show. \n" + response.getErrorMessage() + "\nPlease try again.\n");
        else
            System.out.println("The order of chairs added successfully. The chairs are saved for you for one hour. Please call and pay. \n");

    }

    private int[] getAllChairs()
    {
        String[] chairs_as_string = scanner.nextLine().split(" ");
        int[] chairs = new int[chairs_as_string.length];
        for(int i = 0; i < chairs_as_string.length; i++)
        {
            try
            {
                int current = Integer.parseInt(chairs_as_string[i]);
                if(current >= 0)
                    chairs[i] = current;
                else
                    System.out.println("Chair must be positive Integer.");
            }
            catch (Exception e)
            {
                System.out.println("Chair must be positive Integer.");
            }
        }
        return chairs;
    }

    public void quit()
    {
        System.out.println("Good bye :)\n");
    }

    private Pair<Boolean,Boolean> handleTimeOfShow()
    {
        System.out.println("The show has time? y or n");
        boolean hasTime = true;
        boolean botton_clicked = false;
        if(scanner.nextLine().equals("n"))
        {
            hasTime = false;
            System.out.println("Do you wan't to click on the botton \"The time is not yet set\" ? y or n");
            if(scanner.nextLine().equals("y"))
            {
                botton_clicked = true;
            }
        }
        return new Pair<>(hasTime, botton_clicked);
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
                if(value >= 0 || value == -1)
                    flag = true; // got valid input
            } catch (Exception e) {
                System.out.println(error_message);
            }
        }
        return value;
    }

    private double getDoubleInputFromUser(String message, String error_message)
    {
        boolean flag = false;
        double value = Integer.MIN_VALUE;
        // loop until getting valid option
        while (!flag)
        {
            try {
                System.out.println(message);
                value = Double.parseDouble(scanner.nextLine());
                flag = true; // got valid input
            } catch (Exception e) {
                System.out.println(error_message);
            }
        }
        return value;
    }

    private LocalTime getValidTime(String message_for_user, String error_message_for_user)
    {
        LocalTime time = null;
        boolean input_time_success = false;

        // loop until getting valid option
        while (!input_time_success)
        {
            try {
                System.out.println(message_for_user);
                String input = scanner.nextLine();
                time = LocalTime.parse(input);
                input_time_success = true; // got valid time
            } catch (Exception e) {
                System.out.println(error_message_for_user);
            }
        }
        return time;
    }

    private long getValidDate(String message_for_user, String error_message_for_user) {

        long date = -1;
        SimpleDateFormat date_format = new SimpleDateFormat("dd.MM.yyyy");
        date_format.setLenient(false);
        boolean input_date_success = false;

        // loop until getting valid option
        while (!input_date_success) {
            try {
                System.out.println(message_for_user);
                String input = scanner.nextLine();
                date = date_format.parse(input).getTime();
                input_date_success = true; // got valid date
            } catch (Exception e) {
                System.out.println(error_message_for_user);
            }
        }
        return date;
    }
}
