package View;

import Model.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
        //queryLogic.selectAllFromAuthor();
        List<Author> authorList = new ArrayList<Author>();
        Author newAuthor = new Author("John", "Smith", "1970-09-11", null);
        authorList.add(newAuthor);
        Book newBook = new Book("NewBook", authorList, "fantasy", 222, "9784010796445");
        //queryLogic.insertToAuthors("Johnny", "Jackson", "1966-6-2");
        queryLogic.insertToBooks(newBook);
        //queryLogic.selectAllFromBook();
    }

    public void saveUserData(String username, String password) throws SQLException {
        userLogic.saveUserData(username, password);
    }
}
