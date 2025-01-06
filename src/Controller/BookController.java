package Controller;

import Model.Author;
import Model.Book;
import Model.QL_Interface;
import Model.QueryLogic;

import java.sql.SQLException;
import java.util.List;

public class BookController {

    private final QL_Interface queryLogic;

    public BookController(QL_Interface queryLogic) {
        this.queryLogic = queryLogic;
    }

    public List<Book> getAllBooks() throws SQLException {
        queryLogic.selectAllFromBook();
        return queryLogic.getBooks();
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