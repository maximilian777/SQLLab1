public class Review {
    private final Book book;
    private int rating;
    private final User reviewer;
    private String reviewText;

    Review(Book book, int rating, User reviewer, String reviewText) {
        this.book = book;
        this.rating = rating;
        this.reviewer = reviewer;
        this.reviewText = reviewText;
    }

    public Book getBook() {return book;}
    public int getRating() {return rating;}
    public User getReviewer() {return reviewer;}
    public String getReviewText() {return reviewText;}
}
