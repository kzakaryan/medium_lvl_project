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
    void PersonParamTest() {
        assertAll("Test Props Set:",
                () -> assertEquals("Xachik", person.firstName(), "First Name Failed"),
                () -> assertEquals("Zakaryan", person.lastName(), "Last Name Failed"),
                () -> assertEquals("kzakaryan@gmail.com", person.email(), "Email Failed")
        );
    }

    @Test
    void toStringTest() {
        assertEquals("Xachik Zakaryan kzakaryan@gmail.com", person.toString(), "toString Failed");
    }
}