package Model;

public class Author {
    private String authorID;
    private String firstName;
    private String lastName;
    private String birthDate;
    private String deathDate;

    Author() {

    }

    Author(String firstName, String lastname, String birthDate, String deathDate) {
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

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getDeathDate() {
        return deathDate;
    }

    public void setDeathDate(String deathDate) {
        this.deathDate = deathDate;
    }
}
