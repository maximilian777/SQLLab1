public class Author {
    private final String firstName;
    private final String lastName;
    private String[] bibliography;
    private final String birthDate;
    private final String deathDate;

    Author(String firstName, String lastname, String birthDate, String deathDate) {
        this.firstName = firstName;
        this.lastName = lastname;
        this.birthDate = birthDate;
        this.deathDate = deathDate;
    }

    public String getFirstName() {return firstName;}
    public String getLastName() {return lastName;}
    public String[] getBibliography() {return bibliography;}
    public String getBirthDate() {return birthDate;}
    public String getDeathDate() {return deathDate;}
}
