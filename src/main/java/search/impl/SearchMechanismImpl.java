package search.impl;

import model.Person;
import search.SearchMechanism;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class SearchMechanismImpl implements SearchMechanism {

    InputStream inputStream = new InputStream() {
        @Override
        public int read() throws IOException {
            return 0;
        }
    };
    Scanner scanner = new Scanner(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
    Map<String, Set<Integer>> invertedIndex = new HashMap<>();
    List<Person> people = new ArrayList<>();



    /**
     * Searches for people based on user input and matching strategy.
     */
    @Override
    public void findPeople() {
        var strategy = getSearchStrategy();
        var query = getSearchQuery();

        if (query == null || query.trim().isEmpty()) {
            System.out.println("Search query cannot be empty");
            return;
        }

        var queryWords = new ArrayList<>(Arrays.asList(query.split(" ")));
        var matchingIndices = findMatchingPeople(strategy, queryWords);

        if (matchingIndices.isEmpty()) {
            System.out.println("No matching people found.");
        } else {
            for (int index : matchingIndices) {
                System.out.println(people.get(index));
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
            System.out.print("Select a matching strategy: ALL, ANY, NONE: ");
            var strategy = scanner.nextLine().toUpperCase().trim();
            if (strategy.equals("ALL") || strategy.equals("ANY") || strategy.equals("NONE")) {
                return strategy;
            } else {
                System.out.println("Invalid strategy. Please choose from ALL, ANY, NONE.");
            }
        }
    }

    /**
     * Gets the search query from the user.
     *
     * @return The search query.
     */
    private String getSearchQuery() {
        System.out.print("Enter a name or email to search all suitable people: ");
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
                System.out.println("Error: Invalid strategy.");
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
        var result = new HashSet<Integer>();
        var firstWordMatches = invertedIndex.getOrDefault(queryWords.get(0), Collections.emptySet());

        for (int index : firstWordMatches) {
            var allMatch = true;
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
        var result = new HashSet<Integer>();
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
        var allIndices = new HashSet<Integer>();
        for (int i = 0; i < people.size(); i++) {
            allIndices.add(i);
        }

        Set<Integer> matchingIndices = new HashSet<>();
        for (String word : queryWords) {
            matchingIndices.addAll(invertedIndex.getOrDefault(word, Collections.emptySet()));
        }

        allIndices.removeAll(matchingIndices);
        return allIndices;
    }
}