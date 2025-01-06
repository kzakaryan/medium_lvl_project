package launcher.impl;

import utility.impl.UserPromptUtilityImpl;
import validator.SearchEngineValidator;
import exception.FileNotFoundException;
import org.slf4j.Logger;
import org.mockito.*;
import org.junit.jupiter.api.*;
import static org.mockito.Mockito.*;

/**
 * Coverage 100%
 */
class SearchEngineLauncherImplTest {

    @Mock
    private Logger logger;

    @Mock
    private UserPromptUtilityImpl userPromptUtility;

    @Mock
    private SearchEngineValidator searchEngineValidator;

    private SearchEngineLauncherImpl searchEngineLauncher;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        searchEngineLauncher = new SearchEngineLauncherImpl();
        doNothing().when(userPromptUtility).startMenu();
        searchEngineLauncher.userPromptUtility = userPromptUtility;
        searchEngineLauncher.searchEngineValidator = searchEngineValidator;
        searchEngineLauncher.setLog(logger);
    }

    @Test
    void testStartSearchEngine_FileNotFound() {
        String invalidFilename = "test1.txt";
        doThrow(new FileNotFoundException("File does not exist or is not a valid file"))
                .when(searchEngineValidator).validateInputFile(invalidFilename);

        searchEngineLauncher.startSearchEngine(invalidFilename);

        verify(logger, times(1)).error(eq("Error: {}"), eq("File does not exist or is not a valid file"));
        verify(userPromptUtility, never()).startMenu();
    }


    @Test
    void testStartSearchEngine_ValidFile() {
        String validFilename = "test.txt";
        doNothing().when(searchEngineValidator).validateInputFile(validFilename);
        searchEngineLauncher.startSearchEngine(validFilename);
        verify(userPromptUtility, times(1)).startMenu();
    }
}