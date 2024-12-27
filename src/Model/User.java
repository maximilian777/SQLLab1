package Model;

import java.util.ArrayList;
import java.util.List;

public class User {
    private String username;
    private String password;
    private List<Review> reviews;
    private String accountCreationDate;

    User(String username, String password) {
        this.username = username;
        this.password = password;
        accountCreationDate = "";
        reviews = new ArrayList<>();
    }

    User(String username, String password, List<Review> reviews, String accountCreationDate) {
        this.username = username;
        this.password = password;
        this.reviews = reviews;
        this.accountCreationDate = accountCreationDate;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(String password) {
//        this.password = password;
//    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public String getAccountCreationDate() {
        return accountCreationDate;
    }

    public void setAccountCreationDate(String accountCreationDate) {
        this.accountCreationDate = accountCreationDate;
    }
}
