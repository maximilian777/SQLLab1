public class Author {
    private final String firstName;
    private final String lastName;
    private String[] bibliography;
    private final String birthDate;

    Author(String firstName, String lastname, String[] bibliography, String birthDate) {
        this.firstName = firstName;
        this.lastName = lastname;
        this.bibliography = bibliography;
        this.birthDate = birthDate;
    }
}
