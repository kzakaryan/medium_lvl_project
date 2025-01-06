package launcher.impl;

import org.slf4j.*;
import org.junit.jupiter.api.*;
import utility.impl.UserPromptUtilityImpl;
import validator.SearchEngineValidator;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SearchEngineLauncherImplTest {


    private final String filename = "data.txt";

    @BeforeEach
    public void setUp() {
        SearchEngineValidator mockedSearchEngineValidator = mock(SearchEngineValidator.class);
        UserPromptUtilityImpl mockedUserPromptUtility = mock(UserPromptUtilityImpl.class);
        Logger mockedLogger = mock(Logger.class);
    }
}