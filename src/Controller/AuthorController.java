package Controller;

import Model.Author;
import Model.QueryLogic;

import java.sql.SQLException;
import java.util.List;

public class AuthorController {

    private final QueryLogic queryLogic;

    public AuthorController(QueryLogic queryLogic) {
        this.queryLogic = queryLogic;
    }

    public List<Author> getAllAuthors() throws SQLException {
        queryLogic.selectAllFromAuthor();
        return queryLogic.getAuthors();
    }

    public String getFirstName(Author author) {
        return author.getFirstName();
    }

    public String getLastName(Author author) {
        return author.getLastName();
    }

    public String getBirthDate(Author author) {
        return author.getBirthDate();
    }

    public String getDeathDate(Author author) {
        return author.getDeathDate() != null ? author.getDeathDate() : "N/A";
    }
}