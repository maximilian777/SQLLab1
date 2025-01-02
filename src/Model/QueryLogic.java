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
        catch (Exception e) {
            //ya
        }
        finally {
            //con.close();
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
        finally {
            //con.close();
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
        finally {
            //con.close(); we dont need to close after each statement lol?
        }
    }

    public void insertToAuthors(String firstName, String lastName, String birthDate) throws SQLException {
            String query = "INSERT TO T_Author VALUES (?, ?, ?, ?)";
            PreparedStatement ps = null;
            try  {
                con.setAutoCommit(false);
                ps = con.prepareStatement(query);
                ps.setString(1, firstName);
                ps.setString(2, lastName);
                ps.setString(3, birthDate);
                int res = ps.executeUpdate();
                con.commit();
                System.out.println(res + " records inserted");
            }
            catch (Exception e) {
                if (con != null) {
                    con.rollback();
                }
                throw e;
            }
            finally {
                if (ps != null) {
                    ps.close();
                }
                con.setAutoCommit(true);
            }
    }

    public void insertToAuthors(String firstName, String lastName, String birthDate, String deathDate) throws SQLException {
        String query = "INSERT INTO T_Author VALUES (?, ?, ?, ?)";
        PreparedStatement ps = null;
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(query);
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ps.setString(3, birthDate);
            ps.setString(4, deathDate);
            int res = ps.executeUpdate();
            con.commit();
            System.out.println(res + " records inserted");
        }
        catch (Exception e) {
            if (con != null) {
                con.rollback();
            }
        }
        finally {
            if (ps != null) {
                ps.close();
            }
            con.setAutoCommit(true);
        }
    }

    //need to add authors list perhaps? idfk how to do this one chief
    public void insertToBooks(String ISBN, String title, String genre, int pages) throws SQLException {
        String query = "INSERT INTO T_Book VALUES (?, ?, ?, ?)";
        PreparedStatement ps = null;
        try  {
            con.setAutoCommit(false);
            ps = con.prepareStatement(query);
            ps.setString(1, ISBN);
            ps.setString(2, title);
            ps.setString(3, genre);
            ps.setInt(4, pages);
            int res = ps.executeUpdate();
            con.commit();
            System.out.println(res + " records inserted");
        }catch (Exception e) {
            if (con != null) {
                con.rollback();
            }
        }finally {
            if (ps != null) {
                ps.close();
            }
            con.setAutoCommit(true);
        }

    }

    //users array needed I think idk how to do this
    public void insertToReviews(String ISBN) {

    }

    public void updateAuthor(Author oldAuthor, Author newAuthor) throws SQLException {
        String query;
        if (newAuthor.getDeathDate() != null) {
            query = "UPDATE T_Authors SET firstName = ?, lastName = ?, birthDate = ?, deathDate = ? WHERE firstName ? AND lastName ? AND birthDate ?";
        } else {
            query = "UPDATE T_Authors SET firstName = ?, lastName = ?, birthDate = ? WHERE firstName ? AND lastName ? AND birthDate ?";
        }
        try {
            con.setAutoCommit(false);
            PreparedStatement ps = con.prepareStatement(query);
            if (newAuthor.getDeathDate() != null) {
                ps.setString(1, newAuthor.getFirstName());
                ps.setString(2, newAuthor.getLastName());
                ps.setString(3, newAuthor.getBirthDate());
                ps.setString(4, newAuthor.getDeathDate());

                ps.setString(5, oldAuthor.getFirstName());
                ps.setString(6, oldAuthor.getLastName());
                ps.setString(7, oldAuthor.getBirthDate());

                if (oldAuthor.getDeathDate() != null) {
                    ps.setString(8, oldAuthor.getDeathDate());
                }
            }


        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            con.setAutoCommit(true);
        }
    }

    public void updateBook(Book newBook, Book oldBook) throws SQLException {
        String query = "UPDATE T_Books SET title = ?, genre = ?, pages = ? WHERE ISBN = ?";
        try {
            con.setAutoCommit(false);
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, newBook.getTitle());
            ps.setString(2, newBook.getGenre());
            ps.setInt(3, newBook.getPages());
            ps.setInt(4, oldBook.getISBN());
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            con.setAutoCommit(true);
        }

    }

    public void updateReview(Review oldReview, Review newReview) throws SQLException {
        String query = "UPDATE T_Reviews SET rating = ?, reviewText = ?, user = ? WHERE ISBN = ?";
        try {
            con.setAutoCommit(false);
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, newReview.getRating());
            ps.setString(2, newReview.getReviewText());
            ps.setString(3, newReview.getReviewer().getUsername());
            ps.setInt(4, oldReview.getBook().getISBN());
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
        finally {
            con.setAutoCommit(true);
        }
    }
}