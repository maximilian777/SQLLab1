package Controller;

import Model.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookController {

    private final QL_Interface queryLogic;

    public BookController(QL_Interface queryLogic) {
        this.queryLogic = queryLogic;
    }

    public List<Book> getAllBooks() throws DatabaseException {
        queryLogic.selectAllFromBook();
        return queryLogic.getBooks();
    }

    public Book createBook(String title, List<Author> authors, String genre, int pages, String ISBN) throws DatabaseException {
        Book book = new Book(title, authors, genre, pages, ISBN);
        queryLogic.insertToBooks(book);
        return book;
    }

    public void assignAuthorToBook(String ISBN, String authorID) throws DatabaseException {
        queryLogic.bookAuthors(ISBN, authorID);
    }

    public List<Book> searchBookByTitle(String title) throws DatabaseException {
        return queryLogic.searchBookByTitle(title);
    }
    public List<Book> searchBookByAuthor(String firstName, String lastName) throws DatabaseException {
        return queryLogic.searchBookByAuthor(firstName, lastName);
    }

    public List<Book> searchBookByISBN(String isbn) throws DatabaseException {
        return queryLogic.searchBookByISBN(isbn);
    }

    public List<Book> searchBookByGenre(String genre) throws DatabaseException {
        return queryLogic.searchBookByGenre(genre);
    }

    public List<Book> searchBookByRating(int rating) throws DatabaseException {
        return queryLogic.searchBookByRating(rating);
    }

    public String getTitle(Book book) {
        return book.getTitle();
    }

    public String getGenre(Book book) {
        return book.getGenre();
    }

    public int getPages(Book book) {
        return book.getPages();
    }

    public String getISBN(Book book) {
        return book.getISBN();
    }

    public List<Author> getAuthors(Book book) {
        return book.getAuthors();
    }
}