import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CRUD extends JFrame {
    private JButton addNewUserButton;
    private JPanel mainPanel;
    private JButton updateUserDataButton;
    private JButton showUsersButton;
    private JButton deleteUserButton;

    public CRUD() {
        setContentPane(mainPanel);
        setTitle("User Management System");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        addNewUserButton.addActionListener(e -> {
            JDialog dialog = new JDialog(CRUD.this, "Add User");
            dialog.setContentPane(new AddUser().getAddUserPanel());
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.pack();
            dialog.setVisible(true);
        });
        showUsersButton.addActionListener(e -> {
            JDialog dialog = new JDialog(CRUD.this, "Show Users");
            dialog.setContentPane(new ShowUsers().getUsersPanel());
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.pack();
            dialog.setVisible(true);
        });
        deleteUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog dialog = new JDialog(CRUD.this, "Delete User");
                dialog.setContentPane(new DeleteUserById().getDeleteUserByIdPanel());
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.pack();
                dialog.setVisible(true);
            }
        });
        updateUserDataButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JDialog dialog = new JDialog(CRUD.this, "Update User Info");
                dialog.setContentPane(new UpdateUser().getUpdateUserPanel());
                dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
                dialog.pack();
                dialog.setVisible(true);

            }
        });
    }

//    public static void main(String[] args) {
//        new CRUD();
//    }

}
