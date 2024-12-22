package Menu;

public abstract class Product
{
    protected String name;
    protected float price;
    protected boolean isVegan;
    protected boolean isAvailableToOrder;

    public Product()
    {
        name = "None";
        price = 0.00f;
        isVegan = false;
        isAvailableToOrder = false;
    }

    public Product(StringBuilder dataStringBuilder) {}

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

    public boolean isProductAvailableToOrder()
    {
        return isAvailableToOrder;
    }

    @Override
    public abstract String toString();

    protected abstract String getSQLColumns();

    protected abstract String getSQLValues();

}
