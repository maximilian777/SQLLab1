package Model;

public class Review {
    private final Book book;
    private int rating;
    private final User reviewer;
    private String reviewText;

    Review(Book book, int Rating, User reviewer, String reviewText) {
        this.book = book;
        this.rating = rating;
        this.reviewer = reviewer;
        this.reviewText = reviewText;
    }
}
