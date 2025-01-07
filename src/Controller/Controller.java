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
    private QL_Interface queryLogic;
    private UserLogic userLogic;
    private BookController bookController;
    private AuthorController authorController;
    private ReviewController reviewController;
    private UserView userView;

    private String currentUser;

    public Controller(Connection con) {
        this.con = con;
        this.queryLogic = new QueryLogic(con);
        this.userLogic = new UserLogic(con);
        this.bookController = new BookController(queryLogic);
        this.authorController = new AuthorController(queryLogic);
        this.reviewController = new ReviewController(queryLogic);
        this.userView = new UserView(bookController, authorController, reviewController, logoutAction);
    }

    public void startQuerying() throws SQLException, InterruptedException {
        System.out.println("beforethread");
        ExecutorService execute = Executors.newSingleThreadExecutor();
        System.out.println("after thread");
        try {
            System.out.println("try");
            execute.submit(() -> {
                try {
                    System.out.println("second try");
                    List<Book> books = bookController.getAllBooks();
                    List<Author> authors = authorController.getAllAuthors();
                    List<Review> reviews = reviewController.getAllReviews();
                    System.out.println("help");
                    userView.showUserProfile(books, authors, reviews, this::getCurrentUser);
                    System.out.println("help1");
                } catch (SQLException e) {
                    System.out.println("error???");
                    throw new RuntimeException(e);
                }
            });
        } finally {
            execute.shutdown();
            execute.awaitTermination(10, TimeUnit.SECONDS);
        }
    }

    private final Runnable logoutAction = () -> {
        System.out.println("Logout action invoked.");
        try {
            if (con != null && !con.isClosed()) {
                con.close();
                System.out.println("Connection closed successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Error while closing connection: " + e.getMessage());
            e.printStackTrace();
        }
    };

    public void saveUserData(String username, String password) throws SQLException {
        userLogic.saveUserData(username, password);
        this.currentUser = username;
    }

    public String getCurrentUser() {
        return getCurrentUser();
    }

    public BookController getBookController() {
        return bookController;
    }

    public AuthorController getAuthorController() {
        return authorController;
    }
}