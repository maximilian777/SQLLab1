package Model;


import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserLogic {
    List<User> users;
    Connection con;

    public UserLogic(Connection con){
        this.users = new ArrayList<>();
        this.con = con;
    }

    public void createRoot() {
        users.add(new User("root", "password"));
    }

    public void createUser(String username, String password) {
        for (User user : users) {
            if (username.equals(user.getUsername())) {
                System.out.println("Username is already in use");
            } else {
                users.add(new User(username, password));
            }
        }
    }

    public void saveUserData(String username, String password) throws SQLException {
        for (User user : users) {
            if (!username.equals(user.getUsername())) {
                User newuser = new User(username, password);
                users.add(newuser);
            }
        }
    }
}
