package Menu;

public abstract class Product
{
    protected String name;
    protected float price;
    protected boolean isVegan;

    public Product()
    {
        name = "None";
        price = 0.00f;
        isVegan = false;
    }

    public String getName()
    {
        return name;
    }

    public float getProductPrice()
    {
        return price;
    }

    public boolean isProductVegan()
    {
        return isVegan;
    }

    @Override
    public String toString()
    {
        return  name + " - " + isVegan + " - " + price + "lv";
    }

    protected String getSQLColumns()
    {
        return "(`ProductName`, `ProductPrice`, `IsProductVegan`)";
    }

    protected String getSQLValues()
    {
        String output = "VALUES('%s', %.2f, %b)";
        return String.format(output, name, price, isVegan);
    }
}
