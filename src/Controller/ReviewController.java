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

        public List<Review> getAllReviews() throws SQLException {
            queryLogic.selectAllFromReview();
            return queryLogic.getReviews();
        }

        public Review createReview(String ISBN, String rating, String user, String reviewText) throws SQLException {
            queryLogic.selectAllFromReview();
            return (Review) queryLogic.getReviews();
        }

        public void updateReview(Review oldReview, Review newReview) throws SQLException {
            queryLogic.updateReview(oldReview, newReview);
        }

        public String getRating(Review review) {
            return review.getRating();
        }
}
