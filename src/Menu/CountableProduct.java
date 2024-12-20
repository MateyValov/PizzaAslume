package Menu;

import java.util.MissingFormatArgumentException;

public class CountableProduct extends Product
{
    protected int count;

    public CountableProduct()
    {
        super();
        count = 0;
    }

    public CountableProduct(int id, String name, float price, boolean isVegan, boolean isAvailableToOrder, int count)
    {
        this.id = id;
        this.name = name;
        this.price = price;
        this.isVegan = isVegan;
        this.isAvailableToOrder = isAvailableToOrder;
        this.count = count;
    }

    public CountableProduct(StringBuilder dataStringBuilder)
    {
        String[] productFields = dataStringBuilder.toString().split("[|\\s]");
        if(productFields.length != 6)
        {
            System.out.println("Invalid number of fields!");
            return;
        }

        this.name = productFields[1];
        this.price = Float.parseFloat(productFields[2]);
        this.isVegan = Boolean.parseBoolean(productFields[3]);
        this.isAvailableToOrder = Boolean.parseBoolean(productFields[4]);
        this.count = Integer.parseInt(productFields[5]);
    }

    @Override
    public boolean isProductAvailableToOrder()
    {
        return count != 0 && isAvailableToOrder;
    }

    @Override
    public String toString()
    {
        String template = "#%d: %s - %.2flv., Amount in storage - %d, Is vegan - %b, Is available to order - %b";
        return String.format(template, id, name, price, count, isVegan, isProductVegan());
    }

    @Override
    protected String getSQLColumns()
    {
        return "(`ProductName`, `ProductPrice`, `IsProductVegan`, `IsProductAvailableToOrder`, `ProductQuantity`)";
    }

    @Override
    protected String getSQLValues() throws MissingFormatArgumentException
    {
        String output = "VALUES('%s', %.2f, %b, %b, %d)";
        return String.format(output, name, price, isVegan, isAvailableToOrder, count);
    }
}
