package mechanism.impl;

import model.Person;
import org.junit.jupiter.api.*;
import org.mockito.*;
import org.slf4j.*;
import java.util.*;

public class SearchMechanismImplTest {

    private SearchMechanismImpl searchMechanism;

    private List<Person> people;
    private Map<String, Set<Integer>> invertedIndex;

    @Mock
    private Logger logger;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        people = new ArrayList<>();
        people.add(new Person("John", "Doe", "john.doe@example.com"));
        people.add(new Person("Jane", "Smith", "jane.smith@example.com"));

        invertedIndex = new HashMap<>();
        invertedIndex.put("john", new HashSet<>(Collections.singletonList(0)));
        invertedIndex.put("doe", new HashSet<>(Collections.singletonList(0)));
        invertedIndex.put("john.doe@example.com", new HashSet<>(Collections.singletonList(0)));
        invertedIndex.put("jane", new HashSet<>(Collections.singletonList(1)));
        invertedIndex.put("smith", new HashSet<>(Collections.singletonList(1)));
        invertedIndex.put("jane.smith@example.com", new HashSet<>(Collections.singletonList(1)));

        searchMechanism = new SearchMechanismImpl(invertedIndex, people);
        searchMechanism.setLogger(logger); // Use the mock logger

        System.out.println(invertedIndex);
    }

    @AfterEach
    public void restoreSystemInStream() {
        System.setIn(System.in);  // Restore the original System.in after the test
    }
}