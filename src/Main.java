import Menu.Menu;

import java.util.Scanner;

public class Main
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        Menu menu = new Menu();

        int input;

        while(true)
        {
            System.out.print("===OPTIONS===\n1 - Display menu\n2 - Add new pizza\n3 - Add new drink\n" +
                    "4 - Add new sauce\n5 - Add new dessert\n6 - Quit\n: ");
            input = Integer.parseInt(scanner.nextLine());

            switch (input)
            {
                case(1):
                    menu.showMenu();
                    break;
                case(2):
                    menu.addPizzaToMenu(scanner);
                    break;
                case(3):
                    menu.addDrinkToMenu(scanner);
                    break;
                case(4):
                    menu.addSauceToMenu(scanner);
                    break;
                case(5):
                    menu.addDessertToMenu(scanner);
                    break;
                case(6):
                   return;
            }
        }
    }
}