package mechanism.impl;

import model.Person;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.slf4j.Logger;
import java.util.*;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Coverage 80%
 */
class SearchMechanismImplTest {

    @Mock
    private Logger logger;

    @Mock
    private Scanner scanner;

    private Map<String, Set<Integer>> invertedIndex;
    private List<Person> people;
    private SearchMechanismImpl searchMechanism;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        people = new ArrayList<>();
        people.add(new Person("John", "Doe", "john.doe@example.com"));
        people.add(new Person("Jane", "Smith", "jane.smith@example.com"));

        invertedIndex = new HashMap<>();
        invertedIndex.put("john", new HashSet<>(Arrays.asList(0)));
        invertedIndex.put("doe", new HashSet<>(Arrays.asList(0)));
        invertedIndex.put("jane", new HashSet<>(Arrays.asList(1)));
        invertedIndex.put("smith", new HashSet<>(Arrays.asList(1)));

        searchMechanism = spy(new SearchMechanismImpl(invertedIndex, people));
        searchMechanism.setLogger(logger);

        doNothing().when(logger).info(anyString());

        searchMechanism.scanner = scanner;
    }

    @DisplayName("getSearchQuery for Empty Input Test")
    @Test
    void testGetSearchQuery_EmptyInput() {
        when(scanner.nextLine()).thenReturn("");
        String query = searchMechanism.getSearchQuery();
        assertEquals("", query);
        verify(logger, times(1)).info("Enter a name or email to search all suitable people: ");
    }

    @DisplayName("getSearchQuery for Valid Input Test")
    @Test
    void testGetSearchQuery_ValidInput() {
        when(scanner.nextLine()).thenReturn("john doe");
        String query = searchMechanism.getSearchQuery();
        assertEquals("john doe", query);
        verify(logger, times(1)).info("Enter a name or email to search all suitable people: ");
    }

    @DisplayName("ALL Search Strategy Test")
    @Test
    void testFindMatchingAll() {
        Set<Integer> matchingPeople = searchMechanism.findMatchingAll(new ArrayList<>(Arrays.asList("john", "doe")));
        assertTrue(matchingPeople.contains(0));
        assertFalse(matchingPeople.contains(1));
    }

    @DisplayName("ANY Search Strategy Test")
    @Test
    void testFindMatchingAny() {
        Set<Integer> matchingPeople = searchMechanism.findMatchingAny(new ArrayList<>(Arrays.asList("john", "smith")));
        assertTrue(matchingPeople.contains(0));
        assertTrue(matchingPeople.contains(1));
    }

    @DisplayName("NONE Search Strategy Test")
    @Test
    void testFindMatchingNone() {
        Set<Integer> nonMatchingPeople = searchMechanism.findMatchingNone(new ArrayList<>(Arrays.asList("nonexistent")));
        assertTrue(nonMatchingPeople.contains(0));
        assertTrue(nonMatchingPeople.contains(1));
    }


    @DisplayName("getSearchStrategy for Valid Input")
    @Test
    void testGetSearchStrategy_ValidInput() {
        when(scanner.nextLine()).thenReturn("ANY");
        String strategy = searchMechanism.getSearchStrategy();
        assertEquals("ANY", strategy);
        verify(logger, times(1)).info("Select a matching strategy: ALL, ANY, NONE: ");
    }
}