package model;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class PersonTest {

    private Person person;

    @BeforeEach
    void setUp() {
        person = new Person("Xachik", "Zakaryan", "kzakaryan@gmail.com");
    }

    @Test
    void PersonTest() {
        assertAll("Test Props Set:",
                () -> assertEquals(person.firstName(), "Xachik", "First Name Failed"),
                () -> assertEquals(person.lastName(), "Zakaryan", "Last Name Failed"),
                () -> assertEquals(person.email(), "kzakaryan@gmail.com", "Email Failed")
        );
    }

    @Test
    void toStringTest() {
        assertEquals(person.toString(), "Xachik Zakaryan kzakaryan@gmail.com", "toString Failed");
    }
}