package model;

/**
 * Person class designed
 */
public class Person {

    /**
     * Instance Variables
     */
    private String firstName;
    private String lastName;
    private String email;

    /**
     * All-Arg Constructor
     * @param firstName name of Person
     * @param lastName last name of Person
     * @param email email of Person
     */
    public Person(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    /**
     * Getter for firstName instance variable
     * @return name of person
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Getter for lastName instance variable
     * @return last name of person
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Getter for email instance variable
     * @return email of person
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns a string representation of the person, including their first name, last name,
     * and email (if available).
     * @return A string representation of the person's full name and email.
     */
    @Override
    public String toString() {
        return firstName + " " + lastName + " " + email;
    }
}