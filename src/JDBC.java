
import Controller.Controller;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBC {

    String user;
    String pass;

    JDBC(String username, String password) {
        this.user = username;
        this.pass = password;
    }

    public void connectToDB() throws SQLException {
        String database = "Library"; // the name of the specific database
        String server = "jdbc:mysql://localhost:3306/" + database + "?UseClientEnc=UTF8";

        Connection con = null;
        try {
            System.out.println("Connecting to database...");
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(server, user, pass);
            System.out.println("Connected as " + user);

            Controller controller = new Controller(con);
            controller.saveCurrentUser(user);
            controller.startQuerying();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            System.out.println("Access denied! Please check if you input your credentials correctly.");
        } catch (ClassNotFoundException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}