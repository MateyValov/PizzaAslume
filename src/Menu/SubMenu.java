package Menu;

import java.sql.*;
import java.util.ArrayList;
import java.util.Scanner;

public class SubMenu
{
    private String schemaName;
    private String productName;
    private String subMenuTableName;

    private String productClass;

    private ArrayList<Product> products = new ArrayList<Product>();

    public SubMenu(String schemaName, String productType, String subMenuTableName, String productClass)
    {
        this.schemaName = schemaName;
        this.productName = productType;
        this.subMenuTableName = subMenuTableName;
        this.productClass = productClass;
    }

    public String getSchemaName()
    {
        return schemaName;
    }

    public void setSchemaName(String schemaName)
    {
        this.schemaName = schemaName;
    }

    public String getProductName()
    {
        return productName;
    }

    public void setProductName(String productName)
    {
        this.productName = productName;
    }

    public String getSubMenuTableName()
    {
        return subMenuTableName;
    }

    public void setSubMenuTableName(String subMenuTableName)
    {
        this.subMenuTableName = subMenuTableName;
    }

    public String getProductClass()
    {
        return productClass;
    }

    public void setProductClass(String productClass)
    {
        this.productClass = productClass;
    }

    public void fillMenuFromTable(Connection connection)
    {
        if(connection == null)
        {
            return;
        }

        Statement statement;
        ResultSet resultSet;

        try
        {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM " + subMenuTableName);
            ResultSetMetaData rsMetaData = resultSet.getMetaData();

            while(resultSet.next())
            {
                StringBuilder dataStringBuilder = new StringBuilder();
                for(int i = 1; i <= rsMetaData.getColumnCount(); i++)
                {
                    dataStringBuilder.append(resultSet.getString(i));
                    dataStringBuilder.append("|");
                }
                products.add(new CountableProduct(dataStringBuilder));
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void manageSubMenu(Scanner scanner, Connection connection)
    {
        if(scanner == null)
        {
            return;
        }

        int input;

        while(true)
        {
            displayContents(connection);
            System.out.println("\nOptions:\n1 - Add new product to database\n2 - Remove a product from database\n" +
                    "3 - Restock product\n4 - Discard product\n5 - Empty submenu\n6 - Return");
            do
            {
                System.out.print(": ");
                input = Integer.parseInt(scanner.nextLine());
            }while(input < 1 || input > 6);

            switch(input) {
                case (1):
                {
                    CountableProduct newProduct = Menu.createCountableProduct(scanner, getTableAutoIncrementValue(connection));
                    addProductToSubMenu(connection, newProduct);
                    break;
                }

                case (2):
                {
                    do
                    {
                        System.out.print("Enter index of product to be removed: ");
                        input = Integer.parseInt(scanner.nextLine());
                    } while (doesProductExistInMenu(connection, input) == false);

                    removeProductFromMenu(connection, input);
                    break;
                }

                case (3):
                {
                    do
                    {
                        System.out.print("Enter index of product to be restocked: ");
                        input = Integer.parseInt(scanner.nextLine());
                    } while (doesProductExistInMenu(connection, input) == false);

                    int amount;
                    do
                    {
                        System.out.print("Enter amount to be restocked: ");
                        amount = Integer.parseInt(scanner.nextLine());
                    } while (amount < 1);

                    updateProductQuantity(connection, input, getProductQuantity(connection, input) + amount);
                    break;
                }

                case (4):
                {
                    do
                    {
                        System.out.print("Enter index of product to be discarded: ");
                        input = Integer.parseInt(scanner.nextLine());
                    } while (doesProductExistInMenu(connection, input) == false);

                    int amount;
                    do
                    {
                        System.out.print("Enter amount to be discarded: ");
                        amount = Integer.parseInt(scanner.nextLine());
                    } while (getProductQuantity(connection, input) - amount < 0);

                    updateProductQuantity(connection, input, getProductQuantity(connection, input) - amount);
                    break;
                }

                case (5):
                {
                    emptySubMenu(connection);
                    break;
                }

                case(6):
                {
                    return;
                }
            }
        }
    }

    public void displayContents(Connection connection)
    {
        System.out.println("==========" + productName.toUpperCase() + "==========");
        for(Product product : products)
        {
            System.out.println(product.toString());
        }
    }

    public int getTableAutoIncrementValue(Connection connection)
    {
        int value = 0;
        if(connection == null)
        {
            return value;
        }

        Statement statement;
        ResultSet resultSet;

        try
        {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT `AUTO_INCREMENT` FROM  INFORMATION_SCHEMA.TABLES " +
                    "WHERE TABLE_SCHEMA = '" + schemaName + "' AND TABLE_NAME   = '" + subMenuTableName + "'");

            while(resultSet.next())
            {
                value = resultSet.getInt(1);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return value;
    }

    public int getNumberOfProducts(Connection connection)
    {
        int num = 0;
        if(connection == null)
        {
            return num;
        }

        Statement statement;
        ResultSet resultSet;

        try
        {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT COUNT(*) FROM " + subMenuTableName);

            while(resultSet.next())
            {
                num = resultSet.getInt(1);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return num;
    }

    public boolean doesProductExistInMenu(Connection connection, int id)
    {
        boolean doesExist = false;
        if(connection == null)
        {
            return doesExist;
        }

        Statement statement;
        ResultSet resultSet;

        try
        {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT * FROM " + subMenuTableName + " WHERE Id = " + id);

            doesExist = resultSet.next();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return doesExist;
    }

    public void addProductToSubMenu(Connection connection, Product product)
    {
        products.add(product);

        if(connection == null)
        {
            return;
        }

        PreparedStatement statement;

        try
        {
            String queryTemplate = "INSERT INTO `%s`.`%s` %s %s";

            statement = connection.prepareStatement(String.format(queryTemplate, schemaName, subMenuTableName, product.getSQLColumns(), product.getSQLValues()));
            statement.execute();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void removeProductFromMenu(Connection connection, int productId)
    {
        if(connection == null)
        {
            return;
        }

        PreparedStatement statement;

        try
        {
            String queryTemplate = "DELETE FROM %s WHERE Id = %d";

            statement = connection.prepareStatement(String.format(queryTemplate, subMenuTableName, productId));
            statement.execute();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public int getProductQuantity(Connection connection, int id)
    {
        int amount = 0;
        if(connection == null)
        {
            return amount;
        }

        Statement statement;
        ResultSet resultSet;

        try
        {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT ProductQuantity FROM " + subMenuTableName + " WHERE Id = " + id);

            while(resultSet.next())
            {
                amount = resultSet.getInt(1);
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return amount;
    }

    public void updateProductQuantity(Connection connection, int id, int amount)
    {
        if(connection == null)
        {
            return;
        }

        PreparedStatement statement;

        try
        {
            String queryTemplate = "UPDATE %s SET ProductQuantity = %d WHERE Id = %d";

            statement = connection.prepareStatement(String.format(queryTemplate, subMenuTableName, amount ,id));
            statement.execute();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }

    public void emptySubMenu(Connection connection)
    {
        if(connection == null)
        {
            return;
        }

        PreparedStatement statement;

        try
        {
            statement = connection.prepareStatement("ALTER TABLE " + subMenuTableName + " AUTO_INCREMENT = 1");
            statement.execute();
            statement = connection.prepareStatement("DELETE FROM " + subMenuTableName);
            statement.execute();

            products.clear();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
}
