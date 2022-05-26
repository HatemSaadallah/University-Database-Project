import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class UpdateUser {
    private JPanel updateUserPanel;
    private JFormattedTextField formattedTextField1;
    private JFormattedTextField formattedTextField2;
    private JPasswordField passwordField1;
    private JButton updateUserDataButton;

    public UpdateUser() {
        updateUserDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(formattedTextField1.getText());
                String username = formattedTextField2.getText();
                String password = passwordField1.getText();
                System.out.println(id + " " + username + " " + password);
//                boolean userExists = new Main().checkUserExists(id);
                boolean userUpdated = new Main().updateUserById(id, username, password);
                if (userUpdated) {
                    JOptionPane.showMessageDialog(updateUserPanel, "User updated");
                } else {
                    JOptionPane.showMessageDialog(updateUserPanel, "User not found");
                }

//                if (userExists) {
//                    if (userUpdated) {
//                        JOptionPane.showMessageDialog(null, "User updated successfully");
//                    } else {
//                        JOptionPane.showMessageDialog(null, "User not updated");
//                    }
//                } else {
//                    JOptionPane.showMessageDialog(null, "User not found");
//                }
            }
        });
    }

    public Container getUpdateUserPanel() {
        return updateUserPanel;
    }
}
