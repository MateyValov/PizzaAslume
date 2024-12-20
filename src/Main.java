import Menu.Menu;

import java.sql.*;
import java.util.Scanner;

public class Main
{
    public static Menu menu;

    public static void logIn(Scanner scanner)
    {
        String user;
        String password;

        try
        {
            System.out.print("User: ");
            user = scanner.nextLine();

            System.out.print("Password: ");
            password = scanner.nextLine();

            menu.logIn(user, password);
        }
        catch(SQLException e)
        {
            System.out.println("Invalid log in data!!!");
            logIn(scanner);
        }
    }

    public static void main(String[] args) throws SQLException
    {
        Scanner scanner = new Scanner(System.in);




        /*menu = new Menu();

        logIn(scanner);

        int input;

        while(true)
        {
            System.out.println("===OPTIONS===\n1 - Display menu\n2 - Display list of toppings\n3 - Manage pizzas\n4 - Manage drinks\n" +
                    "5 - Manage sauces\n6 - Manage desserts\n7 - Manage toppings\n8 - Quit");
            System.out.print(": ");
            input = Integer.parseInt(scanner.nextLine());

            switch (input)
            {
                case(1):
                    menu.showMenu();
                    break;
                case(2):
                    menu.showToppings();
                    break;
                case(3):
                    menu.addPizzaToMenu(scanner);
                    break;
                case(4):
                    menu.manageDrinks(scanner);
                    break;
                case(5):
                    menu.addSauceToMenu(scanner);
                    break;
                case(6):
                    menu.addDessertToMenu(scanner);
                    break;
                case(7):
                    menu.addToppingToMenu(scanner);
                    break;
                case(8):
                    return;

            }
        }*/
    }
}