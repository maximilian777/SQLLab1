package Model;

public class Review {
    private Book book;
    private int rating;
    private User reviewer;
    private String reviewText;

    Review() {
        this.book = null;
        this.rating = 0;
        this.reviewer = null;
    }

    Review(Book book, int Rating, User reviewer, String reviewText) {
        this.book = book;
        this.rating = rating;
        this.reviewer = reviewer;
        this.reviewText = reviewText;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public User getReviewer() {
        return reviewer;
    }

    public void setReviewer(User reviewer) {
        this.reviewer = reviewer;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }
}
