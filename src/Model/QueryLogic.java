package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QueryLogic implements QL_Interface {

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
        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM T_Author");

            while (rs.next()) {
                Author author = new Author();
                author.setAuthorID(rs.getString("aID"));
                author.setFirstName(rs.getString("firstName"));
                author.setLastName(rs.getString("lastName"));
                author.setBirthDate(rs.getDate("birthDate"));
                if (rs.getString("deathDate") != null) {
                    author.setDeathDate(rs.getDate("deathDate"));
                }
                authors.add(author);
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception occurred: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public void selectAllFromBook() throws SQLException {
        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM T_Book");
            addBooksToList(rs);
        } catch (SQLException e) {
            System.err.println("SQL Exception occurred: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

    public List<Author> selectAuthorsForBook(String ISBN) throws SQLException {
        List<Author> authorsForBook = new ArrayList<>();

        String query = "SELECT a.firstName, a.lastName, a.aID " +
                "FROM T_Author a " +
                "JOIN T_Book_Authors ba ON a.aID = ba.author_aID " +
                "WHERE ba.book_ISBN = ?";

        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, ISBN);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Author author = new Author();
                author.setAuthorID(rs.getString("aID"));
                author.setFirstName(rs.getString("firstName"));
                author.setLastName(rs.getString("lastName"));
                authorsForBook.add(author);
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception occurred: " + e.getMessage());
            e.printStackTrace();
            throw e;
        } finally {
            if (ps != null) {
                ps.close();
            }
        }

        return authorsForBook;
    }

    public void selectAllFromReview() throws SQLException {
        try (Statement stmt = con.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT * FROM T_Review");

            while (rs.next()) {
                Review review = new Review();
                for (Book book : books) {
                    if (book.getISBN() == rs.getString("ISBN")) {
                        review.setBookISBN(book.getISBN());
                    }
                }
                review.setRating(rs.getString("rating"));
                for (User user : users) {
                    if (user.getUsername().equals(rs.getString("username"))) {
                        review.setReviewer(user);
                    }
                }
                review.setReviewText(rs.getString("reviewText"));

                reviews.add(review);
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception occurred: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
    }

//    public void insertToAuthors(Author author) throws SQLException {
//        String query = "INSERT INTO T_Author (firstName, lastName, birthDate, deathDate) VALUES (?, ?, ?, ?)";
//        PreparedStatement ps = null;
//        ResultSet generatedKeys = null;
//        try {
//            con.setAutoCommit(false);
//            ps = con.prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
//            ps.setString(1, author.getFirstName());
//            ps.setString(2, author.getLastName());
//            ps.setString(3, author.getBirthDate());
//
//            if (author.getDeathDate() == null || author.getDeathDate().isEmpty()) {
//                ps.setNull(4, java.sql.Types.DATE);
//            } else {
//                ps.setString(4, author.getDeathDate());
//            }
//
//            int res = ps.executeUpdate();
//
//            generatedKeys = ps.getGeneratedKeys();
//            if (generatedKeys.next()) {
//                String authorID = generatedKeys.getString(1);
//                author.setAuthorID(authorID);
//            }
//
//            con.commit();
//            System.out.println(res + " records inserted. Author ID: " + author.getAuthorID());
//        } catch (Exception e) {
//            if (con != null) {
//                con.rollback();
//            }
//            throw e;
//        } finally {
//            if (ps != null) {
//                ps.close();
//            }
//            if (generatedKeys != null) {
//                generatedKeys.close();
//            }
//            con.setAutoCommit(true);
//        }
//    }

    public void insertToAuthors(Author author) throws SQLException {
        String query = "INSERT INTO T_Author (firstName, lastName, birthDate, deathDate) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, author.getFirstName());
            ps.setString(2, author.getLastName());
            ps.setDate(3, author.getBirthDate()); // Assuming LocalDate
            if (author.getDeathDate() != null) {
                ps.setDate(4, author.getDeathDate());
            } else {
                ps.setNull(4, java.sql.Types.DATE);
            }
            int affectedRows = ps.executeUpdate();
            if (affectedRows > 0) {
                try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        author.setAuthorID(generatedKeys.getString(1));
                    }
                }
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception occurred: " + e.getMessage());
            e.printStackTrace();
            throw e;
        } finally {
            if (ps != null) {
                ps.close();
            }
        }
    }

    public void insertToBooks(Book book) throws SQLException {
        String query = "INSERT INTO T_Book (ISBN, title, genre, pages) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = null;
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(query);
            ps.setString(1, book.getISBN());
            ps.setString(2, book.getTitle());
            ps.setString(3, book.getGenre());
            ps.setString(4, book.getPages());
            int res = ps.executeUpdate();
            con.commit();
            System.out.println(res + " records inserted");
        } catch (Exception e) {
            if (con != null) {
                con.rollback();
            }
            System.err.println("SQL Exception occurred: " + e.getMessage());
            e.printStackTrace();
            throw e;
        } finally {
            if (ps != null) {
                ps.close();
            }
            con.setAutoCommit(true);
        }

        for (Author author : book.getAuthors()) {
            bookAuthors(book.getISBN(), author.getAuthorID());
        }

    }

    public void bookAuthors(String ISBN, String a_id) throws SQLException {
        String query = "INSERT INTO T_Book_Authors (book_ISBN, author_aID) VALUES (?, ?)";
        PreparedStatement ps = null;
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(query);
            ps.setString(1, ISBN);
            ps.setString(2, a_id);
            int res = ps.executeUpdate();
            System.out.println(res + " records inserted");
        } catch (SQLException e) {
            if (con != null) {
                con.rollback();
            }
            System.err.println("SQL Exception occurred: " + e.getMessage());
            e.printStackTrace();
            throw e;
        } finally {
            if (ps != null) {
                ps.close();
            }
            con.setAutoCommit(true);
        }
    }

    public void insertToReviews(String ISBN, int rating, String revText, String user) throws SQLException {
        String query = "INSERT INTO T_Reviews (book_ISBN, rating, reviewText, user) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = null;
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(query);
            ps.setString(1, ISBN);
            ps.setInt(2, rating);
            ps.setString(3, revText);
            ps.setString(4, user);
            int res = ps.executeUpdate();
            System.out.println(res + " records inserted");
        } catch (Exception e) {
            if (con != null) {
                con.rollback();
            }
            System.err.println("SQL Exception occurred: " + e.getMessage());
            e.printStackTrace();
            throw e;
        } finally {
            if (ps != null) {
                ps.close();
            }
            con.setAutoCommit(true);
        }

    }

    public void updateAuthor(Author oldAuthor, Author newAuthor) throws SQLException {
        String query;
        if (newAuthor.getDeathDate() != null) {
            query = "UPDATE T_Authors SET firstName = ?, lastName = ?, birthDate = ?, deathDate = ? WHERE firstName = ? AND lastName = ? AND birthDate = ?";

        } else {
            query = "UPDATE T_Authors SET firstName = ?, lastName = ?, birthDate = ? WHERE firstName = ? AND lastName = ? AND birthDate = ?";

        }
        PreparedStatement ps = null;
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(query);
            if (newAuthor.getDeathDate() != null) {
                ps.setString(1, newAuthor.getFirstName());
                ps.setString(2, newAuthor.getLastName());
                ps.setDate(3, newAuthor.getBirthDate());
                ps.setDate(4, newAuthor.getDeathDate());

                ps.setString(5, oldAuthor.getFirstName());
                ps.setString(6, oldAuthor.getLastName());
                ps.setDate(7, oldAuthor.getBirthDate());

                if (oldAuthor.getDeathDate() != null) {
                    ps.setDate(8, oldAuthor.getDeathDate());
                }
            }
        } catch (Exception e) {
            if (con != null) {
                con.rollback();
            }
            System.err.println("SQL Exception occurred: " + e.getMessage());
            e.printStackTrace();
            throw e;
        } finally {
            if (ps != null) {
                ps.close();
            }
            con.setAutoCommit(true);
        }
    }

    public void updateBook(Book newBook, Book oldBook) throws SQLException {
        String query = "UPDATE T_Books SET title = ?, genre = ?, pages = ? WHERE ISBN = ?";
        PreparedStatement ps = null;
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(query);
            ps.setString(1, newBook.getTitle());
            ps.setString(2, newBook.getGenre());
            ps.setString(3, newBook.getPages());
            ps.setString(4, oldBook.getISBN());
            ps.executeUpdate();
        } catch (Exception e) {
            if (con != null) {
                con.rollback();
            }
            System.err.println("SQL Exception occurred: " + e.getMessage());
            e.printStackTrace();
            throw e;
        } finally {
            if (ps != null) {
                ps.close();
            }
            con.setAutoCommit(true);
        }

    }

    public void updateReview(Review oldReview, Review newReview) throws SQLException {
        String query = "UPDATE T_Reviews SET rating = ?, reviewText = ?, user = ? WHERE ISBN = ?";
        PreparedStatement ps = null;
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(query);
            ps.setString(1, newReview.getRating());
            ps.setString(2, newReview.getReviewText());
            ps.setString(3, newReview.getReviewer().getUsername());
            ps.executeUpdate();
        }
        catch (Exception e) {
            if (con != null) {
                con.rollback();
            }
            System.err.println("SQL Exception occurred: " + e.getMessage());
            e.printStackTrace();
            throw e;
        }
        finally {
            if (ps != null) {
                ps.close();
            }
            con.setAutoCommit(true);
        }
    }

    public void searchBookByTitle(String title) throws SQLException {
        String query = "SELECT * FROM T_Books WHERE title LIKE ?";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, "%" + title + "%");
            ResultSet rs = ps.executeQuery();
            addBooksToList(rs);
        } catch (Exception e) {
            System.err.println("SQL Exception occurred: " + e.getMessage());
            e.printStackTrace();
            throw e;
        } finally {
            if (ps != null) {
                ps.close();
            }
        }
    }

    public void searchBookByISBN(String ISBN) throws SQLException {
        String query = "SELECT * FROM T_Books WHERE ISBN = ?";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, "%" + ISBN + "%");
            ResultSet rs = ps.executeQuery();
            addBooksToList(rs);
        } catch (Exception e) {
            System.err.println("SQL Exception occurred: " + e.getMessage());
            e.printStackTrace();
            throw e;
        } finally {
            if (ps != null) {
                ps.close();
            }
        }
    }

    public void searchBookByAuthor(Author author) throws SQLException {
        String query = "SELECT * FROM T_Books WHERE aFirstName = ? AND lastName = ?";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, "%" + author.getFirstName() + "%");
            ps.setString(2, "%" + author.getLastName() + "%");
            ResultSet rs = ps.executeQuery();
            addBooksToList(rs);
        } catch (Exception e) {
            System.err.println("SQL Exception occurred: " + e.getMessage());
            e.printStackTrace();
            throw e;
        } finally {
            if (ps != null) {
                ps.close();
            }
        }
    }

    public void searchBookByRating(int rating) throws SQLException {
        String query = "SELECT * FROM T_Books WHERE rating = ?";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, "%" + rating + "%");
            ResultSet rs = ps.executeQuery();
            addBooksToList(rs);
        } catch (Exception e) {
            System.err.println("SQL Exception occurred: " + e.getMessage());
            e.printStackTrace();
            throw e;
        } finally {
            if (ps != null) {
                ps.close();
            }
        }
    }

    public void searchBookByGenre(String genre) throws SQLException {
        String query = "SELECT * FROM T_Books WHERE genre = ?";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(query);
            ps.setString(1, "%" + genre + "%");
            ResultSet rs = ps.executeQuery();
            addBooksToList(rs);
        } catch (Exception e) {
            System.err.println("SQL Exception occurred: " + e.getMessage());
            e.printStackTrace();
            throw e;
        } finally {
            if (ps != null) {
                ps.close();
            }
        }
    }

    private void addBooksToList(ResultSet rs) throws SQLException {
        while (rs.next()) {
            Book book = new Book();
            book.setISBN(rs.getString("ISBN"));
            book.setTitle(rs.getString("title"));
            book.setGenre(rs.getString("genre"));
            book.setPages(rs.getString("pages"));
            book.setAuthors(selectAuthorsForBook(book.getISBN()));

            int count = 0;
            for (Book book1 : books) {
                if (book1.getISBN().equals(book.getISBN())) {
                    count++;
                }
            }
            if (count <= 0) {
                books.add(book);
            }

        }
    }

    public List<Book> getBooks() {return books;}

    public List<Author> getAuthors() {return authors;}

    public List<Review> getReviews() {return reviews;}
}