package launcher.impl;

import org.mockito.*;
import org.slf4j.*;
import org.junit.jupiter.api.*;
import utility.impl.UserPromptUtilityImpl;
import validator.SearchEngineValidator;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SearchEngineLauncherImplTest {

    @Mock
    private SearchEngineValidator searchEngineValidator;

    @Mock
    private UserPromptUtilityImpl userPromptUtility;

    @Mock
    private Logger log;

    @InjectMocks
    private SearchEngineLauncherImpl searchEngineLauncher;

    private final String filename = "data.txt";

    @BeforeEach
    public void setUp() {
        reset(searchEngineValidator, userPromptUtility, log);
    }

    @Test
    public void testStartSearchEngine_validFile() {
        doNothing().when(searchEngineValidator).validateInputFile(filename);
        doNothing().when(searchEngineValidator).loadDataFromFile(filename);
        doNothing().when(userPromptUtility).startMenu();

        searchEngineLauncher.startSearchEngine(filename);

        verify(searchEngineValidator, times(1)).validateInputFile(filename);
        verify(searchEngineValidator, times(1)).loadDataFromFile(filename);
        verify(userPromptUtility, times(1)).startMenu();
    }
}