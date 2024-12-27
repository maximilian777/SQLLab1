package View;

import Model.*;
import java.sql.Connection;
import java.sql.SQLException;

public class Controller {

    Connection con;
    QueryLogic queryLogic;
    UserLogic userLogic;

    public Controller(Connection con) {
        this.con = con;
        this.queryLogic = new QueryLogic(con);
        this.userLogic = new UserLogic(con);
    }


    public void startQuerying() throws SQLException {
        queryLogic.selectAllFromAuthor();
    }

    public void saveUserData(String username, String password) throws SQLException {
        userLogic.saveUserData(username, password);
    }
}
