package View;

import Model.*;
import java.sql.Connection;

public class Controller {

    Connection con;

    public Controller(Connection con) {
        this.con = con;
    }


    public void startQuerying() {
        QueryLogic queryLogic = new QueryLogic(con);

        //queryLogic.executeQuery(con, "query.function");
    }
}
