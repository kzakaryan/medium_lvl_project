package utility.impl;

import validator.SearchEngineValidator;
import mechanism.impl.SearchMechanismImpl;
import org.junit.jupiter.api.*;
import org.mockito.*;
import java.util.*;
import static org.mockito.Mockito.*;

/**
 * Coverage 96%
 */
public class UserPromptUtilityImplTest {

    @Mock
    private SearchEngineValidator searchEngineValidator;

    @Mock
    private SearchMechanismImpl searchMechanism;

    @Mock
    private Scanner scanner;

    private UserPromptUtilityImpl userPromptUtility;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        when(searchEngineValidator.getInvertedIndex()).thenReturn(new HashMap<>());
        when(searchEngineValidator.getPeople()).thenReturn(new ArrayList<>());
        userPromptUtility = new UserPromptUtilityImpl(searchEngineValidator);
        userPromptUtility.scanner = scanner;
    }

    @Test
    void testStartMenu_PrintAllPeople() {
        when(scanner.nextLine())
                .thenReturn("2")
                .thenReturn("0");

        userPromptUtility.startMenu();

        verify(searchEngineValidator).printAllPeople();
        verify(searchMechanism, never()).findPeople();
    }

    @Test
    void testStartMenu_Exit() {
        when(scanner.nextLine()).thenReturn("0");

        userPromptUtility.startMenu();

        verify(searchEngineValidator, never()).printAllPeople();
        verify(searchMechanism, never()).findPeople();
    }

    @Test
    void testStartMenu_InvalidChoice() {
        when(scanner.nextLine())
                .thenReturn("999")
                .thenReturn("0");

        userPromptUtility.startMenu();

        verify(searchEngineValidator, never()).printAllPeople();
        verify(searchMechanism, never()).findPeople();
    }
}