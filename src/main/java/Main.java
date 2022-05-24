import java.sql.*;
import java.util.ArrayList;

class UserData {
    private String username;
    private String password;
    private java.sql.Timestamp createdAt;
    private int id;

    public UserData(int id, String username, String password, Timestamp createdAt) {
        this.username = username;
        this.password = password;
        this.createdAt = createdAt;
        this.id = id;
    }
    public String getUsername() {
        return username;
    }
    public String getPassword() {
        return password;
    }
    public java.sql.Timestamp getCreatedAt() {
        return createdAt;
    }
    public int getId() {
        return id;
    }
}

public class Main {

    public void addNewUser(String username, String password) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/university_project", "root", "");
            Statement statement = connection.createStatement();
            statement.executeUpdate("INSERT INTO university_project.users (username, password, created_at) VALUES ('" + username + "', '" + password + "', '" + new Timestamp(System.currentTimeMillis()) + "')");
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<UserData> getUsers() {
        ArrayList<UserData> users = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/university_project", "root", "");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM university_project.users");
            while (resultSet.next()) {
                users.add(new UserData(resultSet.getInt("id"), resultSet.getString("username"), resultSet.getString("password"), resultSet.getTimestamp("created_at")));
            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    public boolean checkUserExists(int id) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/university_project", "root", "");
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM university_project.users WHERE id = " + id);
            if (resultSet.next()) {
                statement.close();
                connection.close();
                return true;
            }
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean deleteUserById(int id) {
        if(!checkUserExists(id)) {
            return false;
        }
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/university_project", "root", "");
            Statement statement = connection.createStatement();
            statement.executeUpdate("DELETE FROM university_project.users WHERE id = " + id);
            statement.close();
            connection.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean updateUserById(int id, String username, String password) {
        if(!checkUserExists(id)) {
            return false;
        }
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/university_project", "root", "");
            Statement statement = connection.createStatement();
            statement.executeUpdate("UPDATE university_project.users SET username = '" + username + "', password = '" + password + "' WHERE id = " + id);
            statement.close();
            connection.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public static void main(String[] args) {
        new CRUD();
    }
}
