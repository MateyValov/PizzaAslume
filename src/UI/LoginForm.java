package UI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

import Registration.*;

public class LoginForm extends JDialog
{
    private JTextField tf_Email;
    private JPasswordField pf_Password;
    private JButton btn_LogIn;
    private JButton btn_Cancel;
    private JPanel logInPanel;
    private JButton btn_Register;

    public User user;

    public LoginForm(JFrame parent)
    {
        super(parent);

        setTitle("Log in");
        setContentPane(logInPanel);
        setMinimumSize(new Dimension(450, 475));
        setResizable(false);
        setModal(true);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        btn_LogIn.addActionListener(new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                String email = tf_Email.getText();
                String password = String.valueOf(pf_Password.getPassword());

                user = getAuthenticatedUser(email, password);

                if(user != null)
                {
                    dispose();
                }
                else
                {
                    JOptionPane.showMessageDialog(LoginForm.this, "Invalid log in data!",
                            "Try again", JOptionPane.ERROR_MESSAGE);
                }
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

        btn_Register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e)
            {
                RegistrationForm form = new RegistrationForm(null);
            }
        });

        setVisible(true);
    }

    private User getAuthenticatedUser(String email, String password)
    {
        User user = null;

        final String url = "jdbc:mysql://127.0.0.1:3306/menu_schema";
        final String username = "root";
        final String pass = "pass";

        try
        {
            Connection connection = DriverManager.getConnection(url, username, pass);
            Statement statement = connection.createStatement();

            final String usersDatatable = "user_data";
            String insertQuery = "SELECT * FROM " + usersDatatable + " WHERE Email = ? AND Password = ?";

            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            preparedStatement.setString(1, email);
            preparedStatement.setString(2, password);

            ResultSet resultSet = preparedStatement.executeQuery();
            if(resultSet.next())
            {
                user = new User();
                user.setFirstName(resultSet.getString("FirstName"));
                user.setLastName(resultSet.getString("LastName"));
                user.setEmail(resultSet.getString("Email"));
                user.setPhone(resultSet.getString("Phone"));
                user.setAddress(resultSet.getString("Address"));
                user.setPassword(resultSet.getString("Password"));
            }

            statement.close();
            connection.close();
        }
        catch(SQLException e)
        {
            e.printStackTrace();
        }

        return user;
    }
}

