package model;

/**
 * model.Person class designed
 */
public record Person(String firstName, String lastName, String email) {

    @Override
    public String toString() {
        return firstName + " " + lastName + " " + email;
    }
}