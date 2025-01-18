package Model;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class QueryLogic implements QL_Interface {

    Connection con;

    public QueryLogic(Connection con) {
        this.con = con;

    }

    public List<Author> selectAuthorsForBook(String ISBN) throws DatabaseException {
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
            throw new DatabaseException("SQL Error when searching a book by rating", e);
        }
        return authorsForBook;
    }

    public void insertToAuthors(Author author) throws DatabaseException {
        String query = "INSERT INTO T_Author (firstName, lastName, birthDate, deathDate) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = null;
        try {
            ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, author.getFirstName());
            ps.setString(2, author.getLastName());
            ps.setDate(3, author.getBirthDate());

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
            throw new DatabaseException("SQL Error when searching a book by rating", e);
        }
    }

    public void insertToBooks(Book book) throws DatabaseException {
        String query = "INSERT INTO T_Book (ISBN, title, genre, pages) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = null;
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(query);
            ps.setString(1, book.getISBN());
            ps.setString(2, book.getTitle());
            ps.setString(3, book.getGenre());
            ps.setInt(4, book.getPages());
            int res = ps.executeUpdate();
            con.commit();
            System.out.println(res + " records inserted");
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException e1) {
                    System.err.println("SQL Exception occurred: " + e1.getMessage());
                }
            }
            System.err.println("SQL Exception occurred: " + e.getMessage());
            e.printStackTrace();
            throw new DatabaseException("SQL Error when searching a book by rating", e);
        }
        finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                con.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("SQL Exception occurred: " + e.getMessage());
            }
        }

        for (Author author : book.getAuthors()) {
            bookAuthors(book.getISBN(), author.getAuthorID());
        }

    }

    public void bookAuthors(String ISBN, String a_id) throws DatabaseException {
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
                try {
                    con.rollback();
                } catch (SQLException e1) {
                    System.err.println("SQL Exception occurred: " + e1.getMessage());
                }
            }
            System.err.println("SQL Exception occurred: " + e.getMessage());
            e.printStackTrace();
            throw new DatabaseException("SQL Error when searching a book by rating", e);
        }
        finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                con.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("SQL Exception occurred: " + e.getMessage());
            }
        }

    }

    public void insertToReviews(Review review) throws DatabaseException {
        String query = "INSERT INTO T_Review (ISBN, rating, reviewText, username) VALUES (?, ?, ?, ?)";
        PreparedStatement ps = null;
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(query);
            ps.setString(1, review.getBookISBN());
            ps.setInt(2, review.getRating());
            System.out.println("sending review");
            if (review.getReviewText() != null) {
                System.out.println(review.getReviewText());
                ps.setBlob(3, new javax.sql.rowset.serial.SerialBlob(review.getReviewText().getBytes()));
            } else {
                System.out.println("review text is null");
                ps.setNull(3, java.sql.Types.BLOB);
            }
            ps.setString(4, review.getReviewer());
            int res = ps.executeUpdate();
            System.out.println(res + " records inserted");
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException e1) {
                    System.err.println("SQL Exception occurred: " + e1.getMessage());
                }
            }
            System.err.println("SQL Exception occurred: " + e.getMessage());
            e.printStackTrace();
            throw new DatabaseException("SQL Error when searching a book by rating", e);
        }
        finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                con.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("SQL Exception occurred: " + e.getMessage());
            }
        }

    }

    public void updateAuthor(Author oldAuthor, Author newAuthor) throws DatabaseException {
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
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException e1) {
                    System.err.println("SQL Exception occurred: " + e1.getMessage());
                }
            }
            System.err.println("SQL Exception occurred: " + e.getMessage());
            e.printStackTrace();
            throw new DatabaseException("SQL Error when searching a book by rating", e);
        }
        finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                con.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("SQL Exception occurred: " + e.getMessage());
            }
        }
    }

    public void updateBook(Book newBook, Book oldBook) throws DatabaseException {
        String query = "UPDATE T_Books SET title = ?, genre = ?, pages = ? WHERE ISBN = ?";
        PreparedStatement ps = null;
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(query);
            ps.setString(1, newBook.getTitle());
            ps.setString(2, newBook.getGenre());
            ps.setInt(3, newBook.getPages());
            ps.setString(4, oldBook.getISBN());
            ps.executeUpdate();
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException e1) {
                    System.err.println("SQL Exception occurred: " + e1.getMessage());
                }
            }
            System.err.println("SQL Exception occurred: " + e.getMessage());
            e.printStackTrace();
            throw new DatabaseException("SQL Error when searching a book by rating", e);
        }
        finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                con.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("SQL Exception occurred: " + e.getMessage());
            }
        }

    }

    public void updateReview(Review oldReview, Review newReview) throws DatabaseException {
        String query = "UPDATE T_Review SET rating = ?, reviewText = ?, user = ? WHERE ISBN = ?";
        PreparedStatement ps = null;
        try {
            con.setAutoCommit(false);
            ps = con.prepareStatement(query);
            ps.setInt(1, newReview.getRating());
            ps.setString(2, newReview.getReviewText());
            ps.setString(3, newReview.getReviewer());
            ps.executeUpdate();
        } catch (SQLException e) {
            if (con != null) {
                try {
                    con.rollback();
                } catch (SQLException e1) {
                    System.err.println("SQL Exception occurred: " + e1.getMessage());
                }
            }
            System.err.println("SQL Exception occurred: " + e.getMessage());
            e.printStackTrace();
            throw new DatabaseException("SQL Error when searching a book by rating", e);
        }
        finally {
            try {
                if (ps != null) {
                    ps.close();
                }
                con.setAutoCommit(true);
            } catch (SQLException e) {
                System.err.println("SQL Exception occurred: " + e.getMessage());
            }
        }
    }

    public List<Book> searchBookByTitle(String title) throws DatabaseException {
        String query = "SELECT * FROM T_Book WHERE title LIKE ?";
        PreparedStatement ps = null;
        List<Book> resultBooks = new ArrayList<>();

        try {
            ps = con.prepareStatement(query);
            ps.setString(1, "%" + title + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Book book = new Book();
                book.setTitle(rs.getString("title"));
                System.out.println(rs.getString("title"));
                book.setGenre(rs.getString("genre"));
                book.setPages(rs.getInt("pages"));
                book.setISBN(rs.getString("ISBN"));

                List<Author> authors = selectAuthorsForBook(rs.getString("ISBN"));
                book.setAuthors(authors);

                resultBooks.add(book);
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception occurred: " + e.getMessage());
            e.printStackTrace();
            throw new DatabaseException("SQL Error when searching a book by title", e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return resultBooks;
    }

    public List<Book> searchBookByISBN(String ISBN) throws DatabaseException {
        String query = "SELECT * FROM T_Book WHERE ISBN = ?";
        PreparedStatement ps = null;
        List<Book> resultBooks = new ArrayList<>();

        try {
            ps = con.prepareStatement(query);
            ps.setString(1, "%" + ISBN + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Book book = new Book();
                book.setTitle(rs.getString("title"));
                book.setGenre(rs.getString("genre"));
                book.setPages(rs.getInt("pages"));
                book.setISBN(rs.getString("ISBN"));

                List<Author> authors = selectAuthorsForBook(rs.getString("ISBN"));
                book.setAuthors(authors);

                resultBooks.add(book);
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception occurred: " + e.getMessage());
            e.printStackTrace();
            throw new DatabaseException("SQL Error when searching a book by ISBN", e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return resultBooks;
    }

    public List<Book> searchBookByAuthor(String firstName, String lastName) throws DatabaseException {
        String query = "SELECT b.* FROM T_Book b " +
                "JOIN T_Book_Authors ba ON b.ISBN = ba.book_ISBN " +
                "JOIN T_Author a ON ba.author_aID = a.aID " +
                "WHERE a.firstName = ? AND a.lastName = ?";

        PreparedStatement ps = null;
        List<Book> resultBooks = new ArrayList<>();

        try {
            ps = con.prepareStatement(query);
            ps.setString(1, firstName);
            ps.setString(2, lastName);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Book book = new Book();
                book.setTitle(rs.getString("title"));
                book.setGenre(rs.getString("genre"));
                book.setPages(rs.getInt("pages"));
                book.setISBN(rs.getString("ISBN"));

                List<Author> authors = selectAuthorsForBook(rs.getString("ISBN"));
                book.setAuthors(authors);

                resultBooks.add(book);
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception occurred: " + e.getMessage());
            e.printStackTrace();
            throw new DatabaseException("SQL Error when searching a book by author", e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return resultBooks;
    }

    public List<Book> searchBookByRating(int rating) throws DatabaseException {
        String query = "SELECT b.ISBN, b.title, b.genre, b.pages " +
                "FROM T_Book b " +
                "INNER JOIN T_Review r ON b.ISBN = r.ISBN " +
                "WHERE r.rating = ?";
        PreparedStatement ps = null;
        List<Book> resultBooks = new ArrayList<>();

        try {
            ps = con.prepareStatement(query);
            ps.setInt(1, rating);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Book book = new Book();
                book.setISBN(rs.getString("ISBN"));
                book.setTitle(rs.getString("title"));
                book.setGenre(rs.getString("genre"));
                book.setPages(rs.getInt("pages"));
                List<Author> authors = selectAuthorsForBook(rs.getString("ISBN"));
                book.setAuthors(authors);

                resultBooks.add(book);
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception occurred: " + e.getMessage());
            e.printStackTrace();
            throw new DatabaseException("SQL Error when searching books by rating", e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return resultBooks;
    }

    public List<Book> searchBookByGenre(String genre) throws DatabaseException {
        String query = "SELECT * FROM T_Book WHERE genre = ?";
        PreparedStatement ps = null;
        List<Book> resultBooks = new ArrayList<>();

        try {
            ps = con.prepareStatement(query);
            ps.setString(1, "%" + genre + "%");
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Book book = new Book();
                book.setTitle(rs.getString("title"));
                book.setGenre(rs.getString("genre"));
                book.setPages(rs.getInt("pages"));
                book.setISBN(rs.getString("ISBN"));

                List<Author> authors = selectAuthorsForBook(rs.getString("ISBN"));
                book.setAuthors(authors);

                resultBooks.add(book);
            }
        } catch (SQLException e) {
            System.err.println("SQL Exception occurred: " + e.getMessage());
            e.printStackTrace();
            throw new DatabaseException("SQL Error when searching a book by title", e);
        } finally {
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return resultBooks;
    }
}