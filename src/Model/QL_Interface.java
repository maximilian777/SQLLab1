package Model;

import java.sql.SQLException;
import java.util.List;

public interface QL_Interface {
    void selectAllFromAuthor() throws SQLException;
    void selectAllFromBook() throws SQLException;
    List<Author> selectAuthorsForBook(String ISBN) throws SQLException;
    void selectAllFromReview() throws SQLException;
    void insertToAuthors(Author author) throws SQLException;
    void insertToBooks(Book book) throws SQLException;
    void bookAuthors(String ISBN, String a_id) throws SQLException;
    void insertToReviews(String ISBN, int rating, String revText, String user) throws SQLException;
    void updateAuthor(Author oldAuthor, Author newAuthor) throws SQLException;
    void updateBook(Book newBook, Book oldBook) throws SQLException;
    void updateReview(Review oldReview, Review newReview) throws SQLException;
    void searchBookByTitle(String title) throws SQLException;
    void searchBookByISBN(String ISBN) throws SQLException;
    void searchBookByAuthor(Author author) throws SQLException;
    void searchBookByRating(int rating) throws SQLException;
    void searchBookByGenre(String genre) throws SQLException;
    List<Book> getBooks();
    List<Author> getAuthors();
    List<Review> getReviews();
}