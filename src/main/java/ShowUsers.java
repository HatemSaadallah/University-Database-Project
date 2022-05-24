import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class ShowUsers extends Container {
    private JPanel usersPanel;

    public ShowUsers() {
        usersPanel = new JPanel();
        usersPanel.setLayout(new GridLayout(0, 4));
        usersPanel.setBackground(Color.WHITE);
        usersPanel.setSize(600, 400);
        usersPanel.add(new JLabel("id"));
        usersPanel.add(new JLabel("Username"));
        usersPanel.add(new JLabel("Password"));
        usersPanel.add(new JLabel("Created At"));
        setSize(600, 400);
        ArrayList<UserData> users = new Main().getUsers();
        for (UserData user : users) {
            JLabel idLabel = new JLabel(String.valueOf(user.getId()));
            JLabel userNameLabel = new JLabel(user.getUsername());
            JLabel passwordLabel = new JLabel(user.getPassword());
            JLabel createdAtLabel = new JLabel(user.getCreatedAt().toString());
            usersPanel.add(idLabel);
            usersPanel.add(userNameLabel);
            usersPanel.add(passwordLabel);
            usersPanel.add(createdAtLabel);
        }
    }

    public static void main(String[] args) {
        new ShowUsers();
    }

    public Container getUsersPanel() {
        return usersPanel;
    }
}
