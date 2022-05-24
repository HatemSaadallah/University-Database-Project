import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class DeleteUserById {
    private JFormattedTextField userId;
    private JButton deleteUserButton;
    private JPanel deleteUserPanel;

    public DeleteUserById() {
        deleteUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int id = Integer.parseInt(userId.getText());
                boolean userDeleted = new Main().deleteUserById(id);
                if (userDeleted) {
                    JOptionPane.showMessageDialog(null, "User deleted successfully");
                } else {
                    JOptionPane.showMessageDialog(null, "User not found");
                }
            }
        });
    }

    public Container getDeleteUserByIdPanel() {
        return deleteUserPanel;
    }
}
