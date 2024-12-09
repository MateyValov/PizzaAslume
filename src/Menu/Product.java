package Menu;

public abstract class Product
{
    //protected int id;
    protected String name;
    protected float price;
    protected boolean isVegan;

    public Product()
    {
        //id = -1;
        name = "None";
        price = 0.00f;
        isVegan = false;
    }

    public int getProductId()
    {
        return 0/*id*/;
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
        return /*id + " - " +*/ name + " - " + price + "lv";
    }
}
