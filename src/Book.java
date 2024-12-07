
public class Book {
    private final String title;
    private final String publishingDate;
    private final Author author;
    private final String genre;
    private int pages;

    Book(String title, String publishingDate, Author author, String genre, int pages) {
        this.title = title;
        this.publishingDate = publishingDate;
        this.author = author;
        this.genre = genre;
        this.pages = pages;
    }
}



