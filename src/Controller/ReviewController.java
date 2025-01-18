package Controller;
import Model.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

    public class ReviewController {

        private QL_Interface queryLogic;

        public ReviewController(QL_Interface queryLogic) {
            this.queryLogic = queryLogic;
        }

        public Review createReview(String ISBN, int rating, String user, String reviewText) throws DatabaseException {
            Review review = new Review(ISBN, rating, user, reviewText);
            queryLogic.insertToReviews(review);
            return review;
        }

        public void updateReview(Review oldReview, Review newReview) throws DatabaseException {
            queryLogic.updateReview(oldReview, newReview);
        }

        public int getRating(Review review) {
            return review.getRating();
        }
}
