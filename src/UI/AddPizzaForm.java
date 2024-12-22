package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class AddPizzaForm extends JDialog
{
    private JPanel addPizzaPanel;
    private JTextField tf_Name;
    private JTextField tf_Price;
    private JCheckBox cb_IsAvailable;
    private JButton btn_AddPizza;
    private JButton btn_Cancel;
    private JPanel toppingsPanel;
    private JButton btn_AutoGeneratePrice;
    private JLabel lb_Name;

    public String productDatatable;

    public AddPizzaForm(JFrame parent, String pizzaDatatable)
    {
        this.productDatatable = pizzaDatatable;

        setTitle("Add new Pizza");

        setContentPane(addPizzaPanel);
        setMinimumSize(new Dimension(500, 450));
        setResizable(false);
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        toppingsPanel.setLayout(new GridLayout(4, 4));
        fillToppingsPanel();

        btn_AddPizza.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {

            }
        });

        btn_Cancel.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                dispose();
            }
        });

        btn_AutoGeneratePrice.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                autoGeneratePrice();
            }
        });

        setVisible(true);
    }

    private void fillToppingsPanel()
    {
        final String url = "jdbc:mysql://127.0.0.1:3306/menu_schema";
        final String username = "root";
        final String pass = "pass";

        final String toppingsDatatable = "menu_toppings";

        try
        {
            Connection connection = DriverManager.getConnection(url, username, pass);
            Statement statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT ProductName FROM " + toppingsDatatable);

            /*ResultSet resultSet = statement.executeQuery("SELECT ProductName FROM " + toppingsDatatable +
                    " WHERE IsProductAvailableToOrder = 1 AND ProductQuantity > 0");*/
            // The manager should be able to add new pizzas with ingredients that are not currently available
            // The pizza just won't be available to order when its ingredients aro not in stock

            while(resultSet.next())
            {
                try
                {
                    JRadioButton radioButton = new JRadioButton(resultSet.getString(1));
                    radioButton.setBackground(addPizzaPanel.getBackground());
                    radioButton.setFont(lb_Name.getFont());

                    toppingsPanel.add(radioButton, BorderLayout.AFTER_LAST_LINE);

                } catch (Exception e)
                {
                    e.printStackTrace();
                }
            }

            statement.close();
            connection.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    private void autoGeneratePrice()
    {
        float price = 10.f;

        final String url = "jdbc:mysql://127.0.0.1:3306/menu_schema";
        final String username = "root";
        final String pass = "pass";

        final String toppingsDatatable = "menu_toppings";

        try
        {
            Connection connection = DriverManager.getConnection(url, username, pass);
            Statement statement = connection.createStatement();

            StringBuilder getPricesQuery = new StringBuilder("SELECT ProductPrice FROM " + toppingsDatatable +
                    " Where ProductName IN (");

            boolean hasSelectedAnything = false;
            for(int i = 0 ; i < toppingsPanel.getComponents().length; i++)
            {
                JRadioButton radioButton = (JRadioButton) toppingsPanel.getComponents()[i];
                if(radioButton == null)
                {
                    continue;
                }

                if(radioButton.isSelected())
                {
                    String nameToAppend = "'"+radioButton.getText()+"',";
                    getPricesQuery.append(nameToAppend);

                    hasSelectedAnything = true;
                }
            }

            if(hasSelectedAnything)
            {
                getPricesQuery.deleteCharAt(getPricesQuery.length()-1);
                getPricesQuery.append(")");

                ResultSet resultSet = statement.executeQuery(getPricesQuery.toString());

                while(resultSet.next())
                {
                    price += resultSet.getFloat(1);
                }

                statement.close();
                connection.close();
            }
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        tf_Price.setText(Float.toString(price));
    }

    public static void main(String[] args)
    {
        AddPizzaForm form = new AddPizzaForm(null, "menu_pizzas");
    }
}


