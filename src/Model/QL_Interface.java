package Model;

import java.sql.SQLException;
import java.util.List;

public interface QL_Interface {
    List<Author> selectAuthorsForBook(String ISBN) throws DatabaseException;
    void insertToAuthors(Author author) throws DatabaseException;
    void insertToBooks(Book book) throws DatabaseException;
    void bookAuthors(String ISBN, String a_id) throws DatabaseException;
    void insertToReviews(Review review) throws DatabaseException;
    void updateAuthor(Author oldAuthor, Author newAuthor) throws DatabaseException;
    void updateBook(Book newBook, Book oldBook) throws DatabaseException;
    void updateReview(Review oldReview, Review newReview) throws DatabaseException;
    List<Book> searchBookByTitle(String title) throws DatabaseException;
    List<Book> searchBookByISBN(String ISBN) throws DatabaseException;
    List<Book> searchBookByAuthor(String firstName, String lastName) throws DatabaseException;
    List<Book> searchBookByRating(int rating) throws DatabaseException;
    List<Book> searchBookByGenre(String genre) throws DatabaseException;
}