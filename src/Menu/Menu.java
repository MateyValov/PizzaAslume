package Menu;

import java.util.ArrayList;
import java.util.Scanner;

public class Menu
{
    private ArrayList<Pizza> pizzas;
    private ArrayList<CountableProduct> drinks;
    private ArrayList<CountableProduct> sauces;
    private ArrayList<CountableProduct> desserts;

    public Menu()
    {
        pizzas = new ArrayList<Pizza>();
        drinks = new ArrayList<CountableProduct>();
        sauces = new ArrayList<CountableProduct>();
        desserts = new ArrayList<CountableProduct>();
    }

    public void showMenu()
    {
        int i;

        System.out.println("==========PIZZAS==========");
        if(pizzas.isEmpty() == false)
        {
            for(Pizza pizza : pizzas)
            {
                System.out.println(pizza);
            }
        }

        System.out.println("==========DRINKS==========");
        if(drinks.isEmpty() == false)
        {
            for(CountableProduct drink : drinks)
            {
                System.out.println(drink);
            }
        }

        System.out.println("==========SAUCES==========");
        if(sauces.isEmpty() == false)
        {
            for(CountableProduct sauce : sauces)
            {
                System.out.println(sauce);
            }
        }

        System.out.println("==========DESSERTS==========");
        if(desserts.isEmpty() == false)
        {
            for(CountableProduct dessert : desserts)
            {
                System.out.println(dessert);
            }
        }
    }

    private CountableProduct createCountableProduct(Scanner scanner)
    {
        if(scanner == null)
        {
            return null;
        }

        /*System.out.print("Enter product id: ");
        int productId = Integer.parseInt(scanner.nextLine());*/

        System.out.print("Enter product name: ");
        String productName = scanner.nextLine();

        float productPrice = 0.00f;
        do
        {
            System.out.print("Enter product price: ");
            productPrice = Float.parseFloat(scanner.nextLine());
        }while(productPrice <= 0.00f);

        /*int productSizeInteger;
        do
        {
            System.out.print("Chose product size\n(0 - default, 1 - small, 2 - large, 3 - jumbo): ");
            productSizeInteger = Integer.parseInt(scanner.nextLine());
        }while(productSizeInteger < 0 || productSizeInteger > 3);
        ProductSize productSize = ProductSize.values()[productSizeInteger];*/

        return new CountableProduct(/*productId, */productName, productPrice/*, productSize*/);
    }

    public void addPizzaToMenu(Scanner scanner)
    {
        if(scanner == null)
        {
            return;
        }
    }

    public void addDrinkToMenu(Scanner scanner)
    {
        if(scanner == null)
        {
            return;
        }

        drinks.add(createCountableProduct(scanner));
    }

    public void addSauceToMenu(Scanner scanner)
    {
        if(scanner == null)
        {
            return;
        }

        sauces.add(createCountableProduct(scanner));
    }

    public void addDessertToMenu(Scanner scanner)
    {
        if(scanner == null)
        {
            return;
        }

        desserts.add(createCountableProduct(scanner));
    }
}
