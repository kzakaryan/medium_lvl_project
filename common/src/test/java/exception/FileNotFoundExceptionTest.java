package exception;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Coverage 100%
 */
class FileNotFoundExceptionTest {

    private String errorMessage;
    private FileNotFoundException exception;

    @BeforeEach
    void setUp() {
        errorMessage = "File access is denied";
        exception = new FileNotFoundException(errorMessage);
    }

    @DisplayName("Testing Message")
    @Test
    void testFileNotFoundExceptionMessage() {
        assertNotNull(exception);
        assertEquals(errorMessage, exception.getMessage());
    }

    @DisplayName("Testing Inheritance")
    @Test
    void testFileNotFoundExceptionInheritance() {
        assertTrue(exception instanceof RuntimeException);
    }
}