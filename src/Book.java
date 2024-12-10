
public class Book {
    private final String title;
    private final Author author;
    private final String genre;
    private final int pages;
    private final int ISBN;

    Book(String title, Author author, String genre, int pages, int ISBN) {
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.pages = pages;
        this.ISBN = ISBN;
    }

    public String getTitle() {return title;}
    public Author getAuthor() {return author;}
    public String getGenre() {return genre;}
    public int getPages() {return pages;}
    public int getISBN() {return ISBN;}
}



