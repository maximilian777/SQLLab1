package Model;

import java.sql.SQLException;
import java.util.List;

public interface QL_Interface {
    void selectAllFromAuthor() throws DatabaseException;
    void selectAllFromBook() throws DatabaseException;
    List<Author> selectAuthorsForBook(String ISBN) throws DatabaseException;
    void selectAllFromReview() throws DatabaseException;
    void insertToAuthors(Author author) throws DatabaseException;
    void insertToBooks(Book book) throws DatabaseException;
    void bookAuthors(String ISBN, String a_id) throws DatabaseException;
    void insertToReviews(Review review) throws DatabaseException;
    void updateAuthor(Author oldAuthor, Author newAuthor) throws DatabaseException;
    void updateBook(Book newBook, Book oldBook) throws DatabaseException;
    void updateReview(Review oldReview, Review newReview) throws DatabaseException;
    void searchBookByTitle(String title) throws DatabaseException;
    void searchBookByISBN(String ISBN) throws DatabaseException;
    void searchBookByAuthor(Author author) throws DatabaseException;
    void searchBookByRating(int rating) throws DatabaseException;
    void searchBookByGenre(String genre) throws DatabaseException;
    List<Book> getBooks();
    List<Author> getAuthors();
    List<Review> getReviews();
}