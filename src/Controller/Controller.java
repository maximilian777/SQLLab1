package Controller;

import Model.*;
import View.UserView;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Controller {

    private Connection con;
    private QueryLogic queryLogic;
    private UserLogic userLogic;
    private BookController bookController;
    private AuthorController authorController;
    private ReviewController reviewController;
    private UserView userView;

    public Controller(Connection con) {
        this.con = con;
        this.queryLogic = new QueryLogic(con);
        this.userLogic = new UserLogic(con);
        this.bookController = new BookController(queryLogic);
        this.authorController = new AuthorController(queryLogic);
        this.reviewController = new ReviewController(queryLogic);
        this.userView = new UserView();
    }

    public void startQuerying() throws SQLException, InterruptedException {
        ExecutorService execute = Executors.newSingleThreadExecutor();
        try {
            execute.submit(() -> {
                try {
                    List<Book> books = bookController.getAllBooks();
                    List<Author> authors = authorController.getAllAuthors();
                    List<Review> reviews = reviewController.getAllReviews();
                    userView.showUserProfile(books, authors,reviews);
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            });
        } finally {
            execute.shutdown();
            execute.awaitTermination(10, TimeUnit.SECONDS);
            con.close();
        }
    }

    public void saveUserData(String username, String password) throws SQLException {
        userLogic.saveUserData(username, password);
    }

    public BookController getBookController() {
        return bookController;
    }

    public AuthorController getAuthorController() {
        return authorController;
    }
}