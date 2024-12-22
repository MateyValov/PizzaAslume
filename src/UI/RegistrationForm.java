package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import Registration.*;

public class RegistrationForm extends JDialog
{
    private JTextField tf_FirstName;
    private JTextField tf_LastName;
    private JTextField tf_Email;
    private JTextField tf_Phone;
    private JTextField tf_Address;
    private JButton btn_Register;
    private JButton btn_Cancel;
    private JPanel registerPanel;
    private JPasswordField pf_Password;
    private JPasswordField pf_ConfirmPassword;

    public User user = null;

    public RegistrationForm(JFrame parent)
    {
        super(parent);
        setTitle("Register");
        setContentPane(registerPanel);

        Dimension dimension = new Dimension(450, 475);
        setMinimumSize(dimension);
        setResizable(false);

        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        btn_Register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try
                {
                    registerUser();
                }
                catch (SQLException ex)
                {
                    ex.printStackTrace();
                }
            }
        });
        btn_Cancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        setVisible(true);
    }

    private void registerUser() throws SQLException
    {
        String firstName = tf_FirstName.getText();
        String lastName = tf_LastName.getText();
        String email = tf_Email.getText();
        String phone = tf_Phone.getText();
        String address = tf_Address.getText();
        String password = String.valueOf(pf_Password.getPassword());
        String confirmPassword = String.valueOf(pf_ConfirmPassword.getPassword());

        if(firstName.isEmpty() || lastName.isEmpty() || email.isEmpty() || phone.isEmpty() ||
        address.isEmpty() || password.isEmpty() || confirmPassword.isEmpty())
        {
            JOptionPane.showMessageDialog(this, "One of more fields were empty!",
                    "Try again", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if(!password.equals(confirmPassword))
        {
            JOptionPane.showMessageDialog(this, "Confirm password does not match password!",
                    "Try again", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        user = addUserToDatabase(firstName, lastName, email, phone, address, password);
        if(user != null)
        {
            dispose();
        }
        else
        {
            JOptionPane.showMessageDialog(this, "Could not register new user!",
                    "Try again", JOptionPane.ERROR_MESSAGE);
        }
    }

    private User addUserToDatabase(String firstName, String lastName, String email, String phone, String address, String password) throws SQLException {
        User newUser = null;
        final String url = "jdbc:mysql://127.0.0.1:3306/menu_schema";
        final String username = "root";
        final String pass = "pass";

        try
        {
            Connection connection = DriverManager.getConnection(url, username, pass);
            Statement statement = connection.createStatement();

            final String usersDatatable = "user_data";

            String checkEmailQuery = "SELECT * FROM " + usersDatatable + " WHERE Email = " + email;

            PreparedStatement emailCheckPreparedStatement = connection.prepareStatement(checkEmailQuery);
            ResultSet resultSet = emailCheckPreparedStatement.executeQuery();

            if(resultSet.next())
            {
                JOptionPane.showMessageDialog(this, "A user with the same email address already exists!",
                        "Try again", JOptionPane.ERROR_MESSAGE);
                return null;
            }

            String insertQuery = "INSERT INTO " + usersDatatable + "(FirstName, LastName, Email, Phone, Address, Password)" +
                    " VALUES (?, ?, ?, ?, ?, ?)";

            PreparedStatement insertPreparedStatement = connection.prepareStatement(insertQuery);
            insertPreparedStatement.setString(1, firstName);
            insertPreparedStatement.setString(2, lastName);
            insertPreparedStatement.setString(3, email);
            insertPreparedStatement.setString(4, phone);
            insertPreparedStatement.setString(5, address);
            insertPreparedStatement.setString(6, password);

            if(insertPreparedStatement.executeUpdate() > 0)
            {
                newUser = new User(firstName, lastName, email, phone, address, password);
            }

            statement.close();
            connection.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        return newUser;
    }
}
