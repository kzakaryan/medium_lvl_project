package model;

public class Person {

    private String firstName;
    private String lastName;
    private String email;

    public Person(String firstName, String lastName, String email) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName + " " + email;
    }

    public boolean matches(String query) {
        String lowerCaseQuery = query.toLowerCase().trim();
        return firstName.toLowerCase().contains(lowerCaseQuery) ||
                lastName.toLowerCase().contains(lowerCaseQuery) ||
                (email != null && email.toLowerCase().contains(lowerCaseQuery));
    }
}