
import View.Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;


//this will be a part of the logic, it will basically handle the connection and send all the relevant data to controller
public class JDBC {

    String user;
    String pass;

    JDBC(String username, String password) {
        this.user = username;
        this.pass = password;
    }

    //public static void main(String[] args) {

    public void connectToDB() throws SQLException {
        String database = "Library"; // the name of the specific database
        String server
                = "jdbc:mysql://localhost:3306/" + database
                + "?UseClientEnc=UTF8";

        Connection con = null;
        try {
            System.out.println("Connecting to database...");
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(server, user, pass);
            System.out.println("Connected!");

            Controller controller = new Controller(con);
            controller.saveUserData(user, pass); //todo:fix "table 'mysql.users' doesn't exist"
            controller.startQuerying();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Access denied! Please check if you input your credentials correctly.");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                if (con != null) {
                    con.close();
                    System.out.println("Connection closed.");
                }
            } catch (SQLException e) {

            }
        }
    }
}
