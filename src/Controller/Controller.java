package Controller;

import Model.*;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Controller implements Runnable {

    Connection con;
    QueryLogic queryLogic;
    UserLogic userLogic;

    public Controller(Connection con) {
        this.con = con;
        this.queryLogic = new QueryLogic(con);
        this.userLogic = new UserLogic(con);
    }


    public void startQuerying() throws SQLException, InterruptedException {
        ExecutorService execute = Executors.newSingleThreadExecutor();
        try {
//            List<Author> authorList = new ArrayList<Author>();
//            Author newAuthor = new Author("John", "Smith", "1970-09-11", null);
//            authorList.add(newAuthor);
//            Book newBook = new Book("NewBook", authorList, "fantasy", 222, "9784010796445");
            //queryLogic.selectAllFromAuthor();
            //queryLogic.insertToAuthors("Johnny", "Jackson", "1966-6-2");
            //queryLogic.insertToBooks(newBook);
            execute.submit(() -> {
                try  {
                    // Do queryLogic here
                    queryLogic.selectAllFromBook();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
        }
        finally {
            execute.shutdown();
            execute.awaitTermination(10, TimeUnit.SECONDS);
            con.close();
        }

    }

    public void saveUserData(String username, String password) throws SQLException {
        userLogic.saveUserData(username, password);
    }

    @Override
    public void run() {

    }
}
