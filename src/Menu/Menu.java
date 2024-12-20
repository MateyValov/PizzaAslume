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

        drinksMenu = new SubMenu(schemaName, "Drinks", "MENU_DRINKS", "CountableProduct");
        saucesMenu = new SubMenu(schemaName, "Sauces", "MENU_SAUCES", "CountableProduct");
        dessertsMenu = new SubMenu(schemaName, "Desserts", "MENU_DESSERTS", "CountableProduct");
        toppingsMenu = new SubMenu(schemaName, "Toppings", "MENU_TOPPINGS", "CountableProduct");
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

    protected static CountableProduct createCountableProduct(Scanner scanner, int productId)
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

        char isVegan;
        do
        {
            System.out.print("Is the product vegan (Y, N): ");
            isVegan = scanner.nextLine().charAt(0);
        }while(isVegan != 'y' && isVegan != 'n' && isVegan != 'Y' && isVegan != 'N');

        boolean isProductVegan = switch (isVegan)
        {
            case ('y'), ('Y') -> true;
            case ('n'), ('N') -> false;
            default -> false;
        };

        char isAvailable;
        do
        {
            System.out.print("Is the product available to order immediately (Y, N): ");
            isAvailable = scanner.nextLine().charAt(0);
        }while(isAvailable != 'y' && isAvailable != 'n' && isAvailable != 'Y' && isAvailable != 'N');

        boolean isProductAvailable = switch (isAvailable)
        {
            case ('y'), ('Y') -> true;
            case ('n'), ('N') -> false;
            default -> false;
        };

        int productQuantity;
        do
        {
            System.out.print("Enter initial product quantity: ");
            productQuantity = Integer.parseInt(scanner.nextLine());
        }while(productQuantity < 0);

        return new CountableProduct(productId, productName, productPrice, isProductVegan, isProductAvailable, productQuantity);
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
        drinksMenu.manageSubMenu(scanner, connection);
    }

    public void addSauceToMenu(Scanner scanner)
    {
        saucesMenu.manageSubMenu(scanner, connection);
    }

    public void addDessertToMenu(Scanner scanner)
    {
        dessertsMenu.manageSubMenu(scanner, connection);
    }

    public void addToppingToMenu(Scanner scanner)
    {
        toppingsMenu.manageSubMenu(scanner, connection);
    }
}
