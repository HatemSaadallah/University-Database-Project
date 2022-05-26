import java.sql.*;
import java.util.ArrayList;

import oracle.jdbc.OracleTypes;
import oracle.jdbc.driver.OracleDriver;
import oracle.sql.DATE;

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
//            Connect to sql plus
            Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "dream", "dream");
            Statement statement = connection.createStatement();
            System.out.println("Connected to database");
//            Call procedure insertuser
            CallableStatement callableStatement = connection.prepareCall("{call insert_user(?, ?)}");
            callableStatement.setString(1, username);
            callableStatement.setString(2, password);

            callableStatement.execute();
            System.out.println("User added");
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<UserData> getUsers() {
        ArrayList<UserData> users = new ArrayList<>();
        try {
            Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "dream", "dream");
            Statement statement = connection.createStatement();
//            Call procedure get_all_users
            CallableStatement callableStatement = connection.prepareCall("{call get_all_users(?)}");
            callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
            callableStatement.execute();

            ResultSet resultSet = (ResultSet) callableStatement.getObject(1);

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String username = resultSet.getString("username");
                String password = resultSet.getString("password");
                Timestamp createdAt = resultSet.getTimestamp("created_at");
                users.add(new UserData(id, username, password, createdAt));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

//    public boolean checkUserExists(int id) {
//        try {
//            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/university_project", "dream", "dream");
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery("SELECT * FROM users WHERE id = " + id);
//            if (resultSet.next()) {
//                statement.close();
//                connection.close();
//                return true;
//            }
//            statement.close();
//            connection.close();
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//        return false;
//    }
    public boolean deleteUserById(int id) {
//        if(!checkUserExists(id)) {
//            return false;
//        }
        try {
            Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "dream", "dream");
            Statement statement = connection.createStatement();
//            Call Procedure delete_user_by_id
            CallableStatement callableStatement = connection.prepareCall("{call delete_user_by_id(?)}");
            callableStatement.setInt(1, id);
            callableStatement.execute();

            statement.close();
            connection.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static void getUserById(int id) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "dream", "dream");
            Statement statement = connection.createStatement();
            CallableStatement callableStatement = connection.prepareCall("{call get_user_by_id(?, ?)}");
            callableStatement.setInt(1, id);

//            Get second parameter from procedure of type SYS_REFCURSOR
            callableStatement.registerOutParameter(2, OracleTypes.CURSOR);
            callableStatement.execute();

            ResultSet resultSet = (ResultSet) callableStatement.getObject(2);
            assert !callableStatement.wasNull();

            if (resultSet.next()) {
                System.out.println("Username: " + resultSet.getString("username"));
                System.out.println("Password: " + resultSet.getString("password"));
                System.out.println("Created at: " + resultSet.getTimestamp("created_at"));
            }


            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean updateUserById(int id, String username, String password) {
//        if(!checkUserExists(id)) {
//            return false;
//        }
        try {
            Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "dream", "dream");
            Statement statement = connection.createStatement();

            CallableStatement callableStatement = connection.prepareCall("{call update_user_by_id(?, ?, ?)}");
            callableStatement.setInt(1, id);
            callableStatement.setString(2, username);
            callableStatement.setString(3, password);
            callableStatement.executeUpdate();

            System.out.println("User updated");

            statement.close();
            connection.close();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

//    void getAllUsers() {
//        try {
//            Connection connection = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "dream", "dream");
//            Statement statement = connection.createStatement();
////            Call procedure get_all_users
//            CallableStatement callableStatement = connection.prepareCall("{call get_all_users(?)}");
//            callableStatement.registerOutParameter(1, OracleTypes.CURSOR);
//            callableStatement.execute();
//
//            ResultSet resultSet = (ResultSet) callableStatement.getObject(1);
//
//            while (resultSet.next()) {
//                System.out.println("Username: " + resultSet.getString("username"));
//                System.out.println("Password: " + resultSet.getString("password"));
//                System.out.println("Created at: " + resultSet.getTimestamp("created_at"));
//            }
//
//        } catch (SQLException e) {
//            e.printStackTrace();
//        }
//    }
        public static void main(String[] args) {
        new CRUD();
    }
}
