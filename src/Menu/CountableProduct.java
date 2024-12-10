package Menu;

public class CountableProduct extends Product
{
    protected int count;

    public CountableProduct()
    {
        super();
        count = 0;
    }

    public CountableProduct(String inName, float inPrice, boolean isProductVegan)
    {
        name = inName;
        price = inPrice;
        isVegan = isProductVegan;
        count = 0;
    }

    @Override
    public String toString()
    {
        return  name + " - " + isVegan + " - Amount in storage: " + count + " - " + price + "lv";
    }

    @Override
    protected String getSQLColumns()
    {
        return "(`ProductName`, `ProductPrice`, `IsProductVegan`, `ProductQuantity`)";
    }

    @Override
    protected String getSQLValues()
    {
        String output = "VALUES('%s', %.2f, %b, %d)";
        return String.format(output, name, price, isVegan, count);
    }
}
