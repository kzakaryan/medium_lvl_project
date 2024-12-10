package model;

/**
 * Person record designed to store people in search engine.
 */
public record Person(String firstName, String lastName, String email) {

    @Override
    public String toString() {
        return firstName + " " + lastName + " " + email;
    }
}