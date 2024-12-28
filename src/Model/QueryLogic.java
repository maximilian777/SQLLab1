package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QueryLogic {

    Connection con;
    List<Book> books;
    List<Author> authors;
    List<Review> reviews;
    List<User> users;

    public QueryLogic(Connection con) {
        this.con = con;
        books = new ArrayList<>();
        authors = new ArrayList<>();
        reviews = new ArrayList<>();
    }



    public void selectAllFromAuthor() throws SQLException {
        //do we need 'try' here if we're not catching?
        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM T_Author");

            while (rs.next()) {
                Author author = new Author();
                author.setAuthorID(rs.getString("aID"));
                author.setFirstName(rs.getString("firstName"));
                author.setLastName(rs.getString("lastName"));
                author.setBirthDate(rs.getString("birthDate"));
                if (rs.getString("deathDate") != null) {
                    author.setDeathDate(rs.getString("deathDate"));
                }
                authors.add(author);
            }
            for (Author author : authors) {

                if (author.getDeathDate() != null) {
                    System.out.println(author.getFirstName() + " " + author.getLastName() + " " + author.getBirthDate() + " " + author.getDeathDate());
                } else {
                    System.out.println(author.getFirstName() + " " + author.getLastName() + " " + author.getBirthDate());
                }
            }
        }
    }

    public void selectAllFromBook() throws SQLException {
        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM T_Book");

            while (rs.next()) {
                Book book = new Book();
                book.setISBN(rs.getInt("ISBN"));
                book.setTitle(rs.getString("title"));
                book.setGenre(rs.getString("genre"));
                //book.setAuthors(authors);
                book.setPages(rs.getInt("pages"));

                books.add(book);
            }
        }
    }

    public void selectAllFromReview() throws SQLException {
        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM T_Review");

            while (rs.next()) {
                Review review = new Review();
                for (Book book : books) {
                    if (book.getISBN() == rs.getInt("ISBN")) {
                        review.setBook(book);
                    }
                }
                review.setRating(rs.getInt("rating"));
                for (User user : users) {
                    if (user.getUsername().equals(rs.getString("username"))) {
                        review.setReviewer(user);
                    }
                }
                review.setReviewText(rs.getString("reviewText"));

                reviews.add(review);
            }
        }
    }

    public void insertToAuthors(String firstName, String lastName, String birthDate) throws SQLException {
            String query = "INSERT TO T_Author VALUES (?, ?, ?, ?)";
            PreparedStatement ps = con.prepareStatement(query);

            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, birthDate);
            int res = ps.executeUpdate();

            System.out.println(res + " records inserted");
    }

    public void insertToAuthors(String firstName, String lastName, String birthDate, String deathDate) throws SQLException {
        String query = "INSERT INTO T_Author VALUES (?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(query);

        ps.setString(1, firstName);
        ps.setString(2, lastName);
        ps.setString(3, birthDate);
        ps.setString(4, deathDate);
        int res = ps.executeUpdate();

        System.out.println(res + " records inserted");
    }

    //need to add authors list perhaps? idfk how to do this one chief
    public void insertToBooks(String ISBN, String title, String genre, int pages) throws SQLException {
        String query = "INSERT INTO T_Book VALUES (?, ?, ?, ?)";
        PreparedStatement ps = con.prepareStatement(query);
        ps.setString(1, ISBN);
        ps.setString(2, title);
        ps.setString(3, genre);
        ps.setInt(4, pages);
        int res = ps.executeUpdate();
        System.out.println(res + " records inserted");

    }

    //users array needed I think idk how to do this
    public void insertToReviews(String ISBN) {

    }
}