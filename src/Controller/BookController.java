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

    public List<Book> getAllBooks() throws SQLException {
        queryLogic.selectAllFromBook();
        return queryLogic.getBooks();
    }

    public Book createBook(String title, List<Author> authors, String genre, int pages, String ISBN) throws SQLException {
        Book book = new Book(title, authors, genre, pages, ISBN);
        queryLogic.insertToBooks(book);
        return book;
    }

    public void assignAuthorToBook(String ISBN, String authorID) throws SQLException {
        queryLogic.bookAuthors(ISBN, authorID);
    }

    public List<Book> searchBookByTitle(String title) throws SQLException {
        if (queryLogic.getBooks().isEmpty()) {
            queryLogic.getBooks();
        }
        return queryLogic.getBooks().stream()
                .filter(book -> book.getTitle().toLowerCase().contains(title.toLowerCase()))
                .collect(Collectors.toList());
    }

    public List<Book> searchBookByAuthor(String firstName, String lastName) {
        if (queryLogic.getBooks().isEmpty()) {
            queryLogic.getBooks();
        }
        return queryLogic.getBooks().stream()
                .filter(book -> book.getAuthors().stream()
                        .anyMatch(author -> author.getFirstName().toLowerCase().contains(firstName.toLowerCase()) &&
                                author.getLastName().toLowerCase().contains(lastName.toLowerCase())))
                .collect(Collectors.toList());
    }

    public List<Book> searchBookByISBN(String isbn) throws SQLException {
        if (queryLogic.getBooks().isEmpty()) {
            queryLogic.getBooks();
        }


        return queryLogic.getBooks().stream()
                .filter(book -> book.getISBN().replaceAll("[^0-9]", "").equals(isbn))
                .collect(Collectors.toList());
    }

    public List<Book> searchBookByGenre(String genre) throws SQLException {
        if (queryLogic.getBooks().isEmpty()) {
            queryLogic.getBooks();
        }

        String normalizedGenre = genre.toLowerCase().replaceAll("[^a-z0-9]", "");

        return queryLogic.getBooks().stream()
                .filter(book -> book.getGenre().toLowerCase().replaceAll("[^a-z0-9]", "").contains(normalizedGenre))
                .collect(Collectors.toList());
    }

    public List<Book> searchBookByRating(int rating) throws SQLException {
        List<Book> booksWithMatchingRating = new ArrayList<>();
        if (queryLogic.getReviews().isEmpty()) {
            queryLogic.getReviews();
        }
        if (queryLogic.getBooks().isEmpty()) {
            queryLogic.getBooks();
        }
        for (Review review : queryLogic.getReviews()) {
            if (review.getRating() == rating) {
                String bookISBN = review.getBookISBN();
                Book book = queryLogic.getBooks().stream()
                        .filter(b -> b.getISBN().equalsIgnoreCase(bookISBN))
                        .findFirst()
                        .orElse(null);
                if (book != null && !booksWithMatchingRating.contains(book)) {
                    booksWithMatchingRating.add(book);
                }
            }
        }
        return booksWithMatchingRating;
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