package validator;

import exception.FileNotFoundException;
import model.Person;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.*;
import java.io.*;
import org.mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Coverage 96%
 */
@ExtendWith(MockitoExtension.class)
class SearchEngineValidatorTest {

    @Mock
    private BufferedReader bufferedReader;

    private SearchEngineValidator validator;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        validator = new SearchEngineValidator();
    }

    @Test
    void testCreateBufferedReader_FileNotFound() {
        File file = new File("nonexistentFile.txt");
        assertThrows(FileNotFoundException.class, () -> validator.createBufferedReader(file));
    }

    @Test
    void testCreateBufferedReader_ValidFile() throws IOException {

        String mockLine = "John Doe john@example.com";

        when(bufferedReader.readLine()).thenReturn(mockLine, null);
        BufferedReader injectedReader = validator.createBufferedReader(bufferedReader);

        assertNotNull(injectedReader);
        assertEquals(bufferedReader, injectedReader);

        assertEquals(mockLine, injectedReader.readLine());
        verify(bufferedReader).readLine();
    }

    @Test
    void testValidateInputFile_NullFilename() {
        assertThrows(FileNotFoundException.class, () -> {
            validator.validateInputFile(null);
        });
    }

    @Test
    void testValidateInputFile_EmptyFilename() {
        assertThrows(FileNotFoundException.class, () -> {
            validator.validateInputFile("");
        });
    }

    @Test
    void testValidateInputFile_ValidFilename() {
        assertDoesNotThrow(() -> {
            validator.validateInputFile("test.txt");
        });
    }

    @Test
    void testLoadDataFromFile_ValidFile() throws IOException {
        SearchEngineValidator validator = spy(new SearchEngineValidator());

        BufferedReader bufferedReader = mock(BufferedReader.class);
        when(bufferedReader.readLine()).thenReturn("John Doe john.doe@example.com")
                .thenReturn("Jane Smith jane.smith@example.com")
                .thenReturn(null);

        doReturn(bufferedReader).when(validator).createBufferedReader(any(File.class));

        validator.loadDataFromFile("/Users/kzakaryan/IdeaProjects/medium_lvl_project/common/src/test/resources/test.txt");

        assertEquals(2, validator.getPeople().size());

        assertTrue(validator.getInvertedIndex().containsKey("john"));
        assertTrue(validator.getInvertedIndex().containsKey("doe"));
        assertTrue(validator.getInvertedIndex().containsKey("john.doe@example.com"));
    }

    @Test
    void testLoadDataFromFile_FileNotFound() {
        String filename = "nonexistent-file.txt";
        assertDoesNotThrow(() -> validator.loadDataFromFile(filename));
    }


    @Test
    void testProcessLine_ValidData() {
        String line = "John Doe john.doe@example.com";
        int lineIndex = 0;

        validator.processLine(line, lineIndex);

        assertEquals(1, validator.getPeople().size());
        Person person = validator.getPeople().get(0);
        assertEquals("John", person.getFirstName());
        assertEquals("Doe", person.getLastName());
        assertEquals("john.doe@example.com", person.getEmail());

        assertTrue(validator.getInvertedIndex().containsKey("john"));
        assertTrue(validator.getInvertedIndex().containsKey("doe"));
        assertTrue(validator.getInvertedIndex().containsKey("john.doe@example.com"));
    }

    @Test
    void testPrintAllPeople() {

        validator.getPeople().add(new Person("John", "Doe", "john.doe@example.com"));

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        validator.printAllPeople();
        System.setOut(originalOut);

        String expectedOutput = "[John Doe john.doe@example.com]";
        assertTrue(outputStream.toString().contains(expectedOutput));
    }
}