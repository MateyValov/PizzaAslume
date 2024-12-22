package Menu;

import java.util.ArrayList;

public class Pizza extends Product
{
    ArrayList<String> toppings = new ArrayList<String>();

    public Pizza(String name, ArrayList<String> toppings, float price, boolean isVegan, boolean isAvailableToOrder)
    {
        this.name = name;
        this.toppings = toppings;
        this.price = price;
        this.isVegan = isVegan;
        this.isAvailableToOrder = isAvailableToOrder;
    }

    protected String getToppingsListAsString(boolean includeSpaces)
    {
        StringBuilder toppingsList = new StringBuilder();

        for(String topping : toppings)
        {
            toppingsList.append(topping);
            if(toppings.getLast().equals(topping))
            {
               break;
            }
            toppingsList.append(",");
            if(includeSpaces)
            {
                toppingsList.append(" ");
            }
        }

        return toppingsList.toString();
    }

    @Override
    public String toString()
    {
        String template = "#%d: %s, %s - %.2flv., Amount in storage - %d, Is vegan - %b, Is available to order - %b";
        return String.format(template, name, getToppingsListAsString(true), price, isVegan, isProductAvailableToOrder());
    }

    @Override
    protected String getSQLColumns()
    {
        return "(`ProductName`, `ProductToppings`, `ProductPrice`, `IsProductVegan`, `IsProductAvailableToOrder`)";
    }

    @Override
    protected String getSQLValues()
    {
        String output = "VALUES('%s', '%s', %.2f, %b, %b)";
        return String.format(output, name, getToppingsListAsString(false), price, isVegan, isAvailableToOrder);
    }
}
