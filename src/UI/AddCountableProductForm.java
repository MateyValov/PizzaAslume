package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import Menu.CountableProduct;

public class AddCountableProductForm extends JDialog
{
    private JLabel lb_ProductName;
    private JTextField tf_Name;
    private JTextField tf_Price;
    private JCheckBox cb_IsVegan;
    private JCheckBox cb_IsAvailable;
    private JTextField tf_Stock;
    private JButton btn_Cancel;
    private JButton btn_AddProduct;
    private JPanel addProductPanel;

    public String productDatatable;
    public CountableProduct product;

    public AddCountableProductForm(JFrame parent, String productName, String productDatatable)
    {
        this.productDatatable = productDatatable;

        setTitle("Add new " + productName);
        lb_ProductName.setText(productName);

        setContentPane(addProductPanel);
        setMinimumSize(new Dimension(450, 500));
        setResizable(false);
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        btn_AddProduct.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                addNewProduct();
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

        setVisible(true);
    }

    private void addNewProduct()
    {
        String productName = tf_Name.getText();
        String productPrice = tf_Price.getText();
        String productCount = tf_Stock.getText();
        String isProductVegan = cb_IsVegan.isSelected() ? "1" : "0";
        String isProductAvailable = cb_IsAvailable.isSelected() ? "1" : "0";;

        //System.out.println(productName +"|"+productPrice+"|"+productCount+"|"+isProductVegan+"|"+isProductAvailable);

        if(productName.isEmpty() || productPrice.isEmpty() || productCount.isEmpty())
        {
            JOptionPane.showMessageDialog(this, "One of more fields were empty!",
                    "Try again", JOptionPane.ERROR_MESSAGE);
            return;
        }

        float price;
        try
        {
            price = Float.parseFloat(productPrice);
        }
        catch(NumberFormatException e)
        {
            JOptionPane.showMessageDialog(this, "Product price is not a valid value!",
                    "Try again", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(price <= 0.f)
        {
            JOptionPane.showMessageDialog(this, "Product price is not a valid value!",
                    "Try again", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int count;
        try
        {
            count = Integer.parseInt(productCount);
        }
        catch(NumberFormatException e)
        {
            JOptionPane.showMessageDialog(this, "Product stock is not a valid value!",
                    "Try again", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if(count < 0)
        {
            JOptionPane.showMessageDialog(this, "Product stock is not a valid value!",
                    "Try again", JOptionPane.ERROR_MESSAGE);
            return;
        }

        product = addCountableProductToDatabase(productName, productPrice, productCount, isProductVegan, isProductAvailable);

        if(product != null)
        {
            dispose();
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Could not register new product!",
                    "Try again", JOptionPane.ERROR_MESSAGE);
        }
    }

    private CountableProduct addCountableProductToDatabase(String productName, String productPrice, String productCount, String isProductVegan, String isProductAvailable)
    {
        CountableProduct product = null;

        final String url = "jdbc:mysql://127.0.0.1:3306/menu_schema";
        final String username = "root";
        final String pass = "pass";

        try
        {
            Connection connection = DriverManager.getConnection(url, username, pass);
            Statement statement = connection.createStatement();

            String insertQuery = "INSERT INTO " + productDatatable + "(ProductName, ProductPrice, IsProductVegan, IsProductAvailableToOrder, " +
                    "ProductQuantity) VALUES (?, ?, ?, ?, ?)";

            PreparedStatement insertPreparedStatement = connection.prepareStatement(insertQuery);
            insertPreparedStatement.setString(1, productName);
            insertPreparedStatement.setString(2, productPrice);
            insertPreparedStatement.setString(3, isProductVegan);
            insertPreparedStatement.setString(4, isProductAvailable);
            insertPreparedStatement.setString(5, productCount);

            if(insertPreparedStatement.executeUpdate() > 0)
            {
                product = new CountableProduct(productName, productPrice, isProductVegan, isProductAvailable, productCount);
            }

            statement.close();
            connection.close();
        }
        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return product;
    }
}
