package launcher.impl;

import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;

class SearchEngineLauncherImplTest {

    @BeforeEach
    void setUp() {
        System.out.println("[BeforeEach]");
    }

    @Test
    void startSearchEngine() {
        System.out.println("startSearchEngine");
    }

    @AfterEach
    void tearDown() {
        System.out.println("[AfterEach]");
    }
}