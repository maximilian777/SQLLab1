package Controller;

import Model.Author;
import Model.QL_Interface;
import Model.QueryLogic;

import java.sql.SQLException;
import java.util.List;

public class AuthorController {

    private final QL_Interface queryLogic;

    public AuthorController(QL_Interface queryLogic) {
        this.queryLogic = queryLogic;
    }

    public List<Author> getAllAuthors() throws SQLException {
        queryLogic.selectAllFromAuthor();
        return queryLogic.getAuthors();
    }

    public Author createAuthor(String firstName, String lastname, java.sql.Date birthDate, java.sql.Date deathDate) throws SQLException {
        Author author = new Author(firstName, lastname, birthDate, deathDate);
        queryLogic.insertToAuthors(author);
        return author;
    }

    public String getFirstName(Author author) {
        return author.getFirstName();
    }

    public String getLastName(Author author) {
        return author.getLastName();
    }

    public java.sql.Date getBirthDate(Author author) {
        return author.getBirthDate();
    }

    public java.sql.Date getDeathDate(Author author) {
        if (author.getDeathDate() == null) {
            System.out.println("Death date is null");
        }
        return author.getDeathDate();
    }
}