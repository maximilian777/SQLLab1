package Controller;
import Model.Review;
import Model.Book;
import Model.User;
import Model.QueryLogic;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

    public class ReviewController {

        private QueryLogic queryLogic;

        public ReviewController(QueryLogic queryLogic) {
            this.queryLogic = queryLogic;
        }

        public List<Review> getAllReviews() throws SQLException {
            queryLogic.selectAllFromReview();
            return queryLogic.getReviews();
        }

        public void updateReview(Review oldReview, Review newReview) throws SQLException {
            queryLogic.updateReview(oldReview, newReview);
        }
}
