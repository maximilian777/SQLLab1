package Model;

public class Review {
    private String ISBN;
    private int rating;
    private String reviewer;
    private String reviewText;

    public Review() {
        this.ISBN = null;
        this.rating = 0;
        this.reviewer = null;
    }

    public Review(String ISBN, int rating, String reviewer, String reviewText) {
        this.ISBN = ISBN;
        this.rating = rating;
        this.reviewer = reviewer;
        this.reviewText = reviewText;
    }


    public String getBookISBN() {
        return ISBN;
    }

    public void setBookISBN(String ISBN) {
        this.ISBN = ISBN;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReviewer() {
        return reviewer;
    }

    public void setReviewer(String reviewer) {
        this.reviewer = reviewer;
    }

    public String getReviewText() {
        return reviewText;
    }

    public void setReviewText(String reviewText) {
        this.reviewText = reviewText;
    }
}
