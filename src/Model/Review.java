package Model;

public class Review {
    private String ISBN;
    private String rating;
    private User reviewer;
    private String reviewText;

    Review() {
        this.ISBN = null;
        this.rating = "0";
        this.reviewer = null;
    }

    Review(String ISBN, String rating, User reviewer, String reviewText) {
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

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
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
