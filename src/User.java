public class User {
    private final String username;
    private final String password;
    private Review[] reviews;
    private final String accountCreationDate;

    User(String username, String password, Review[] reviews, String accountCreationDate) {
        this.username = username;
        this.password = password;
        this.reviews = reviews;
        this.accountCreationDate = accountCreationDate;
    }

    public String getUsername() {return username;}
    public String getPassword() {return password;}
    public Review[] getReviews() {return reviews;}
    public String getAccountCreationDate() {return accountCreationDate;}
}
