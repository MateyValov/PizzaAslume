package Menu;

public class CountableProduct extends Product
{
    protected int count;
    //protected ProductSize size;

    public CountableProduct()
    {
        super();
        count = 0;
        //size = ProductSize.DEFAULT;
    }

    public CountableProduct(/*int inId, */String inName, float inPrice/*, ProductSize inSize*/)
    {
        //id = inId;
        name = inName;
        price = inPrice;
        count = 0;
        //size = inSize;
    }

    public ProductSize getProductSize()
    {
        return ProductSize.DEFAULT;
    }

    @Override
    public String toString()
    {
        return /*id + " - " +*/ name + " - " /*+ size + " - "*/ + price + "lv";
    }
}
