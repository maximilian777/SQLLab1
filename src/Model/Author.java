package Model;

public class Author {
    private String authorID;
    private String firstName;
    private String lastName;
    private java.sql.Date birthDate;
    private java.sql.Date deathDate;

    Author() {

    }

    public Author(String firstName, String lastname, java.sql.Date birthDate, java.sql.Date deathDate) {
        this.firstName = firstName;
        this.lastName = lastname;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
    }

    public String getAuthorID() {
        return authorID;
    }
    public void setAuthorID(String authorID) {
        this.authorID = authorID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public java.sql.Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(java.sql.Date birthDate) {
        this.birthDate = birthDate;
    }

    public java.sql.Date getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(java.sql.Date deathDate) {
        this.deathDate = deathDate;
    }
}
