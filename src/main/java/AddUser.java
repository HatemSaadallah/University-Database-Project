import com.sun.jdi.connect.spi.Connection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.Statement;
import java.sql.DriverManager;
import java.sql.SQLException;

public class AddUser extends Container {
    private JPanel addUserPanel;
    private JTextField textField1;
    private JPasswordField passwordField1;
    private JButton addUserButton;

    public AddUser() {

        addUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = textField1.getText();
                String password = passwordField1.getText();
                if (username.equals("") || password.equals("")) {
                    JOptionPane.showMessageDialog(null, "Please fill all fields");
                } else {
                    new Main().addNewUser(username, password);
                    JOptionPane.showMessageDialog(null, "User added successfully");
                    close();
                }
            }
        });
    }

    public JPanel getAddUserPanel() {
        return addUserPanel;
    }
    void close() {
        this.removeAll();
    }
    public static void main(String[] args) {
        JFrame frame = new JFrame("Add User");
        frame.setContentPane(new AddUser().addUserPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }

}
