package Model;

import java.sql.SQLException;
import java.util.List;

public interface QL_Interface {
    void selectAllFromAuthor() throws SQLException;
    void selectAllFromBook() throws SQLException;
    List<Author> selectAuthorsForBook(String ISBN) throws SQLException;
    void selectAllFromReview() throws SQLException;
    void insertToAuthors(String firstName, String lastName, String birthDate) throws SQLException;
    void insertToAuthors(String firstName, String lastName, String birthDate, String deathDate) throws SQLException;
    void insertToBooks(Book book) throws SQLException;
    void bookAuthors(String ISBN, String a_id) throws SQLException;
    void insertToReviews(String ISBN);
    void updateAuthor(Author oldAuthor, Author newAuthor) throws SQLException;
    void updateBook(Book newBook, Book oldBook) throws SQLException;
    void updateReview(Review oldReview, Review newReview) throws SQLException;
    List<Book> getBooks();
    List<Author> getAuthors();
    List<Review> getReviews();
}