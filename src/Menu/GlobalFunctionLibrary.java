package Menu;

import java.sql.*;

public class GlobalFunctionLibrary
{
    private static double checkStringSimilarity(String string1, String string2)
    {
        String longer = string1, shorter = string2;
        if (string1.length() < string2.length())
        {
            System.out.println("bro...");
            longer = string2; shorter = string1;
        }

        int longerLength = longer.length();
        return (longerLength == 0) ? 1.0 : (longerLength - editDistance(longer, shorter)) / (double) longerLength;
    }

    public static int editDistance(String longerString, String shorterString) {
        longerString = longerString.toLowerCase();
        shorterString = shorterString.toLowerCase();

        int[] costs = new int[shorterString.length() + 1];
        for (int i = 0; i <= longerString.length(); i++)
        {
            int lastValue = i;
            for (int j = 0; j <= shorterString.length(); j++)
            {
                if (i == 0)
                {
                    costs[j] = j;
                }
                else
                {
                    if (j > 0)
                    {
                        int newValue = costs[j - 1];
                        if (longerString.charAt(i - 1) != shorterString.charAt(j - 1))
                        {
                            newValue = Math.min(Math.min(newValue, lastValue), costs[j]) + 1;
                        }

                        costs[j - 1] = lastValue;
                        lastValue = newValue;
                    }
                }
            }
            if (i > 0)
            {
                costs[shorterString.length()] = lastValue;
            }
        }
        return costs[shorterString.length()];
    }

    public static boolean checkForSimilarProducts(Connection connection, String dataTableName, String newProductName)
    {
        if(connection == null)
        {
            return false;
        }

        Statement statement;
        ResultSet resultSet;

        try
        {
            statement = connection.createStatement();
            resultSet = statement.executeQuery("SELECT ProductName FROM " + dataTableName);
            ResultSetMetaData rsMetaData = resultSet.getMetaData();

            while(resultSet.next())
            {
                String productName = resultSet.getString("ProductName");
                if(checkStringSimilarity(newProductName, productName) >= 0.75)
                {
                    System.out.println(newProductName + " is similar to " + productName);
                    return true;
                }
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
        return true;
    }
}
