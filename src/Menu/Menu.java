package Menu;

import java.sql.*;
import java.util.Scanner;

public class Menu
{
    private static String url;
    private String schemaName;

    private Connection connection;

    private SubMenu drinksMenu;
    private SubMenu saucesMenu;
    private SubMenu dessertsMenu;
    private SubMenu toppingsMenu;

    public Menu()
    {
        url = "jdbc:mysql://127.0.0.1:3306/menu_schema";

        schemaName = "MENU_SCHEMA";

        drinksMenu = new SubMenu(schemaName, "Drinks", "MENU_DRINKS");
        saucesMenu = new SubMenu(schemaName, "Sauces", "MENU_SAUCES");
        dessertsMenu = new SubMenu(schemaName, "Desserts", "MENU_DESSERTS");
        toppingsMenu = new SubMenu(schemaName, "Toppings", "MENU_TOPPINGS");
    }

    public void logIn(String user, String password) throws SQLException
    {
        connection = DriverManager.getConnection(url, user, password);
    }

    public void showMenu()
    {
        drinksMenu.displayContents(connection);
        saucesMenu.displayContents(connection);
        dessertsMenu.displayContents(connection);
    }

    public void showToppings()
    {
        toppingsMenu.displayContents(connection);
    }

    private CountableProduct createCountableProduct(Scanner scanner)
    {
        if(scanner == null)
        {
            return null;
        }

        System.out.print("Enter product name: ");
        String productName = scanner.nextLine();

        float productPrice = 0.00f;
        do
        {
            System.out.print("Enter product price: ");
            productPrice = Float.parseFloat(scanner.nextLine());
        }while(productPrice <= 0.00f);

        System.out.print("Is the product vegan: ");
        boolean isProductVegan = Boolean.parseBoolean(scanner.nextLine());

        return new CountableProduct(productName, productPrice, isProductVegan);
    }

    public void addPizzaToMenu(Scanner scanner)
    {
        if(scanner == null)
        {
            return;
        }
    }

    public void manageDrinks(Scanner scanner)
    {
        if(scanner == null)
        {
            return;
        }

        int input;

        drinksMenu.displayContents(connection);
        System.out.println("\nOptions:\n1 - Add new drink\n2 - Remove a drink\n3 - Return");
        do
        {
            System.out.print(": ");
            input = Integer.parseInt(scanner.nextLine());
        }while(input < 1 || input > 3);

        switch(input)
        {
            case(1):
                CountableProduct newDrink = createCountableProduct(scanner);
                drinksMenu.addProductToSubMenu(connection, newDrink);
                break;

            case(2):
                do
                {
                    System.out.print("Enter index of product to be removed: ");
                    input = Integer.parseInt(scanner.nextLine());
                }while(input < 1 || input > drinksMenu.getNumberOfProducts(connection));

                drinksMenu.removeProductFromMenu(connection, input);
                break;
        }
    }

    public void addSauceToMenu(Scanner scanner)
    {
        if(scanner == null)
        {
            return;
        }

        int input;

        saucesMenu.displayContents(connection);
        System.out.println("\nOptions:\n1 - Add new sauce\n2 - Remove a sauce\n3 - Return");
        do
        {
            System.out.print(": ");
            input = Integer.parseInt(scanner.nextLine());
        }while(input < 1 || input > 3);

        switch(input)
        {
            case(1):
                CountableProduct newSauce = createCountableProduct(scanner);
                saucesMenu.addProductToSubMenu(connection, newSauce);
                break;

            case(2):
                do
                {
                    System.out.print("Enter index of product to be removed: ");
                    input = Integer.parseInt(scanner.nextLine());
                }while(input < 1 || input > saucesMenu.getNumberOfProducts(connection));

                saucesMenu.removeProductFromMenu(connection, input);
                break;
        }
    }

    public void addDessertToMenu(Scanner scanner)
    {
        if(scanner == null)
        {
            return;
        }

        int input;

        dessertsMenu.displayContents(connection);
        System.out.println("\nOptions:\n1 - Add new dessert\n2 - Remove a dessert\n3 - Return");
        do
        {
            System.out.print(": ");
            input = Integer.parseInt(scanner.nextLine());
        }while(input < 1 || input > 3);

        switch(input)
        {
            case(1):
                CountableProduct newDessert = createCountableProduct(scanner);
                dessertsMenu.addProductToSubMenu(connection, newDessert);
                break;

            case(2):
                do
                {
                    System.out.print("Enter index of product to be removed: ");
                    input = Integer.parseInt(scanner.nextLine());
                }while(input < 1 || input > dessertsMenu.getNumberOfProducts(connection));

                dessertsMenu.removeProductFromMenu(connection, input);
                break;
        }
    }

    public void addToppingToMenu(Scanner scanner)
    {
        if(scanner == null)
        {
            return;
        }

        int input;

        toppingsMenu.displayContents(connection);
        System.out.println("\nOptions:\n1 - Add new topping\n2 - Remove a topping\n3 - Return");
        do
        {
            System.out.print(": ");
            input = Integer.parseInt(scanner.nextLine());
        }while(input < 1 || input > 3);

        switch(input)
        {
            case(1):
                CountableProduct newTopping = createCountableProduct(scanner);
                toppingsMenu.addProductToSubMenu(connection, newTopping);
                break;

            case(2):
                do
                {
                    System.out.print("Enter index of product to be removed: ");
                    input = Integer.parseInt(scanner.nextLine());
                }while(input < 1 || input > toppingsMenu.getNumberOfProducts(connection));

                toppingsMenu.removeProductFromMenu(connection, input);
                break;
        }
    }
}
