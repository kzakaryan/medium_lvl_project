package mechanism.impl;

import mechanism.SearchMechanism;
import model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.*;
import java.io.*;

/**
 * Class designed for Implementing people search with given parameters
 */
public class SearchMechanismImpl implements SearchMechanism {

    /**
     * Instance Variables
     */
    Scanner scanner = new Scanner(System.in);
    private final Map<String, Set<Integer>> invertedIndex;
    private final List<Person> people;
    private static final Logger log = LoggerFactory.getLogger(SearchMechanismImpl.class);

    public SearchMechanismImpl(Map<String, Set<Integer>> invertedIndex, List<Person> people) {
        this.invertedIndex = invertedIndex;
        this.people = people;
    }

    /**
     * Searches for people based on user input and matching strategy.
     */
    @Override
    public void findPeople() {
        var strategy = getSearchStrategy();
        var query = getSearchQuery();

        if (query == null || query.trim().isEmpty()) {
            log.info("Search query cannot be empty");
            return;
        }

        var queryWords = new ArrayList<>(Arrays.asList(query.split(" ")));
        var matchingIndices = findMatchingPeople(strategy, queryWords);

        if (matchingIndices.isEmpty()) {
            log.info("No matching people found");
        } else {
            for (int index : matchingIndices) {
                log.info(String.valueOf(people.get(index)));
            }
        }
    }

    /**
     * Gets the search strategy from the user.
     *
     * @return The chosen search strategy (ALL, ANY, NONE).
     */
    private String getSearchStrategy() {
        while (true) {
            log.info("Select a matching strategy: ALL, ANY, NONE: ");
            var strategy = scanner.nextLine().toUpperCase().trim();
            if (strategy.equals("ALL") || strategy.equals("ANY") || strategy.equals("NONE")) {
                return strategy;
            } else {
                log.info("Invalid strategy. Please choose from ALL, ANY, NONE.");
            }
        }
    }

    /**
     * Gets the search query from the user.
     *
     * @return The search query.
     */
    private String getSearchQuery() {
        log.info("Enter a name or email to search all suitable people: ");
        return scanner.nextLine().toLowerCase().trim();
    }

    /**
     * Finds people matching the search query based on the selected strategy.
     *
     * @param strategy The matching strategy (ALL, ANY, NONE).
     * @param queryWords A list of words to search for.
     * @return A set of indices of matching people.
     */
    private Set<Integer> findMatchingPeople(String strategy, ArrayList<String> queryWords) {
        switch (strategy) {
            case "ALL":
                return findMatchingAll(queryWords);
            case "ANY":
                return findMatchingAny(queryWords);
            case "NONE":
                return findMatchingNone(queryWords);
            default:
                log.info("Error: Invalid strategy.");
                return Collections.emptySet();
        }
    }

    /**
     * Finds people who match all the query words (ALL).
     *
     * @param queryWords A list of words to search for.
     * @return A set of indices of matching people.
     */
    private Set<Integer> findMatchingAll(ArrayList<String> queryWords) {
        if (queryWords.isEmpty()) {
            return Collections.emptySet();
        }
        Set<Integer> result = new HashSet<>();
        Set<Integer> firstWordMatches = invertedIndex.getOrDefault(queryWords.get(0), Collections.emptySet());

        for (int index : firstWordMatches) {
            boolean allMatch = true;
            for (String word : queryWords) {
                if (!invertedIndex.getOrDefault(word, Collections.emptySet()).contains(index)) {
                    allMatch = false;
                    break;
                }
            }
            if (allMatch) {
                result.add(index);
            }
        }
        return result;
    }

    /**
     * Finds people who match any of the query words (ANY).
     *
     * @param queryWords A list of words to search for.
     * @return A set of indices of matching people.
     */
    private Set<Integer> findMatchingAny(ArrayList<String> queryWords) {
        Set<Integer> result = new HashSet<>();
        for (String word : queryWords) {
            result.addAll(invertedIndex.getOrDefault(word, Collections.emptySet()));
        }
        return result;
    }

    /**
     * Finds people who do not match any of the query words (NONE).
     *
     * @param queryWords A list of words to search for.
     * @return A set of indices of non-matching people.
     */
    private Set<Integer> findMatchingNone(ArrayList<String> queryWords) {
        Set<Integer> nonMatchingIndices = new HashSet<>();
        for (int i = 0; i < people.size(); i++) {
            boolean isNonMatching = true;
            for (String word : queryWords) {
                if (invertedIndex.getOrDefault(word, Collections.emptySet()).contains(i)) {
                    isNonMatching = false;
                    break;
                }
            }
            if (isNonMatching) {
                nonMatchingIndices.add(i);
            }
        }
        return nonMatchingIndices;
    }
}