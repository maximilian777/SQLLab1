package Model;

import java.util.ArrayList;
import java.util.List;

public class Book {
    private String title;
    private List<Author> authors;
    private String genre;
    private int pages;
    private String ISBN;

    Book() {
        this.title = "";
        this.authors = new ArrayList<Author>();
        this.genre = "";
        this.pages = 0;
        this.ISBN = "";
    }

    public Book(String title, List<Author> authors, String genre, int pages, String ISBN) {
        this.title = title;
        this.authors = new ArrayList<Author>();
        this.genre = genre;
        this.pages = pages;
        this.ISBN = ISBN;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Author> getAuthors() {
        return authors;
    }

    public void setAuthors(List<Author> authors) {
        this.authors = authors;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public String getISBN() {
        return ISBN;
    }

    public void setISBN(String ISBN) {
        this.ISBN = ISBN;
    }
}


