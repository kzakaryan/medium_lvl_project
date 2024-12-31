package model;

/**
 * Person record designed to store people in search engine.
 */
public record Person(String firstName, String lastName, String email) {

    @Override
    public String toString() {
        return firstName + " " + lastName + " " + email;
    }

//    public String getFirstName() {
//        return firstName;
//    }
//
//    public String getLastName() {
//        return lastName;
//    }
//
//    public String getEmail() {
//        return email;
//    }
}