package Menu;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Menu
{
    private static String url;
    private Connection connection;

    /*private ArrayList<Pizza> pizzas;
    private ArrayList<CountableProduct> drinks;
    private ArrayList<CountableProduct> sauces;
    private ArrayList<CountableProduct> desserts;*/

    private String schemaName;
    private String drinksMenuTable;
    private String saucesMenuTable;
    private String dessertsMenuTable;
    private String toppingsMenuTable;

    public Menu()
    {
        url = "jdbc:mysql://127.0.0.1:3306/menu_schema";

        /*pizzas = new ArrayList<Pizza>();
        drinks = new ArrayList<CountableProduct>();
        sauces = new ArrayList<CountableProduct>();
        desserts = new ArrayList<CountableProduct>();*/

        schemaName = "MENU_SCHEMA";
        drinksMenuTable = "MENU_DRINKS";
        saucesMenuTable = "MENU_SAUCES";
        dessertsMenuTable = "MENU_DESSERTS";
        toppingsMenuTable = "MENU_TOPPINGS";
    }

    public void logIn(String user, String password) throws SQLException
    {
        connection = DriverManager.getConnection(url, user, password);
    }

    public void displayCountableProductTableContents(String tableName, String productType)
    {
        if(connection == null)
        {
            return;
        }
        try
        {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM " + tableName);

            System.out.println("==========" + productType.toUpperCase() + "==========");
            while(resultSet.next())
            {
                System.out.print(resultSet.getString("Id") + " ");
                System.out.print(resultSet.getString("ProductName") + " ");
                System.out.print(resultSet.getString("ProductPrice") + " ");
                System.out.print(resultSet.getString("IsProductVegan") + " ");
                System.out.print(resultSet.getString("ProductQuantity") + " ");
                System.out.println();
            }
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

    }

    public void showMenu()
    {
        displayCountableProductTableContents(drinksMenuTable, "Drinks");
        displayCountableProductTableContents(saucesMenuTable, "Sauces");
        displayCountableProductTableContents(dessertsMenuTable, "Desserts");
    }

    public void showToppings()
    {
        displayCountableProductTableContents(toppingsMenuTable, "Toppings");
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

    public void addDrinkToMenu(Scanner scanner)
    {
        if(scanner == null)
        {
            return;
        }

        CountableProduct newDrink = createCountableProduct(scanner);
        String queryTemplate = "INSERT INTO `%s`.`%s` %s %s";
        String insertQuery = String.format(queryTemplate, schemaName, drinksMenuTable, newDrink.getSQLColumns(), newDrink.getSQLValues());

        try
        {
            if(connection == null)
            {
                return;
            }

            PreparedStatement statement = connection.prepareStatement(insertQuery);
            statement.execute();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        //drinks.add(newDrink);
    }

    public void addSauceToMenu(Scanner scanner)
    {
        if(scanner == null)
        {
            return;
        }

        CountableProduct newSauce = createCountableProduct(scanner);
        String queryTemplate = "INSERT INTO `%s`.`%s` %s %s";
        String insertQuery = String.format(queryTemplate, schemaName, saucesMenuTable, newSauce.getSQLColumns(), newSauce.getSQLValues());

        try{
            if(connection == null)
            {
                return;
            }

            PreparedStatement statement = connection.prepareStatement(insertQuery);
            statement.execute();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        //sauces.add(newSauce);
    }

    public void addDessertToMenu(Scanner scanner)
    {
        if(scanner == null)
        {
            return;
        }

        CountableProduct newDessert = createCountableProduct(scanner);
        String queryTemplate = "INSERT INTO `%s`.`%s` %s %s";
        String insertQuery = String.format(queryTemplate, schemaName, dessertsMenuTable, newDessert.getSQLColumns(), newDessert.getSQLValues());

        try
        {
            if(connection == null)
            {
                return;
            }

            PreparedStatement statement = connection.prepareStatement(insertQuery);
            statement.execute();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        //sauces.add(newDessert);
    }

    public void addToppingToMenu(Scanner scanner)
    {
        if(scanner == null)
        {
            return;
        }

        CountableProduct newTopping = createCountableProduct(scanner);
        String queryTemplate = "INSERT INTO `%s`.`%s` %s %s";
        String insertQuery = String.format(queryTemplate, schemaName, toppingsMenuTable, newTopping.getSQLColumns(), newTopping.getSQLValues());

        try
        {
            if(connection == null)
            {
                return;
            }

            PreparedStatement statement = connection.prepareStatement(insertQuery);
            statement.execute();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
}
