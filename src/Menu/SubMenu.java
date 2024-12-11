package Menu;

import java.sql.*;
import java.util.ArrayList;

public class SubMenu
{
    private String schemaName;
    private String productType;
    private String subMenuTableName;

    public SubMenu(String schemaName, String productType, String subMenuTableName)
    {
        this.schemaName = schemaName;
        this.productType = productType;
        this.subMenuTableName = subMenuTableName;
    }

    public String getSchemaName()
    {
        return schemaName;
    }

    public String getProductType()
    {
        return productType;
    }

    public String getSubMenuTableName()
    {
        return subMenuTableName;
    }

    public void displayContents(Connection connection)
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

            System.out.println("==========" + productType.toUpperCase() + "==========");
            while(resultSet.next())
            {
                for(int i = 1; i <= rsMetaData.getColumnCount(); i++)
                {
                    System.out.print(resultSet.getString(i) + " ");
                }
                System.out.println();
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public int getNumberOfProducts(Connection connection)
    {
        int num = 0;
        if(connection == null)
        {
            return num;
        }
        try
        {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM " + subMenuTableName);

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

    public void addProductToSubMenu(Connection connection, Product product)
    {
        if(connection == null)
        {
            return;
        }

        String queryTemplate = "INSERT INTO `%s`.`%s` %s %s";
        String insertQuery = String.format(queryTemplate, schemaName, subMenuTableName, product.getSQLColumns(), product.getSQLValues());

        try
        {
            PreparedStatement statement = connection.prepareStatement(insertQuery);
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

        String queryTemplate = "DELETE FROM %s WHERE Id = %d";
        String insertQuery = String.format(queryTemplate, subMenuTableName, productId);

        try
        {
            PreparedStatement statement = connection.prepareStatement(insertQuery);
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

        String queryTemplate = "DELETE FROM " + subMenuTableName;

        try
        {
            PreparedStatement statement = connection.prepareStatement(queryTemplate);
            statement.execute();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }
    }
}
