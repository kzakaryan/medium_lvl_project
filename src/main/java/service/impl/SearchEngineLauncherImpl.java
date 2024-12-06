package service.impl;

import model.Person;
import service.SearchEngine;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * A simple search engine that allows the user to search for people based on their name or email.
 * It loads data from a file, stores it in an inverted index, and offers a menu to search and display data.
 */
public class SearchEngineLauncherImpl implements SearchEngine {

    /**
     * Instance Variables
     */
    InputStream inputStream = new InputStream() {
        @Override
        public int read() throws IOException {
            return 0;
        }
    };
    List<Person> people = new ArrayList<>();
    Map<String, Set<Integer>> invertedIndex = new HashMap<>();
    Scanner scanner = new Scanner(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
    boolean running = true;

    /**
     * Starts the search engine by loading data from the specified file and displaying the menu to the user.
     * @param filename The name of the file containing the data to be loaded.
     * @throws IllegalArgumentException if the filename is null or empty.
     * @throws FileNotFoundException when file is null
     * @throws IOException when file not found
     */
    @Override
    public void startSearchEngine(String filename) throws IllegalArgumentException, IOException {
        validateInputFile(filename);
        loadDataFromFile(filename);
        showMenu();
    }

    /**
     * Validates that the provided filename is not null or empty.
     * @param filename The name of the file containing the data to be loaded.
     * @throws IllegalArgumentException if the filename is null or empty.
     */
    private void validateInputFile(String filename) throws IllegalArgumentException{
        if (filename == null || filename.trim().isEmpty()) {
            throw new IllegalArgumentException("Filename cannot be null or empty");
        }
    }

    /**
     * Loads data from the given file and populates the people ArrayList and inverted index Map.
     * @param filename The name of the file containing the data to be loaded.
     * @throws FileNotFoundException when file is null
     * @throws IOException when file not found
     */
    private void loadDataFromFile(String filename) throws IOException {
        InputStreamReader inputStreamReader = new InputStreamReader(new FileInputStream(filename), StandardCharsets.UTF_8);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        try (BufferedReader _ = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"))) {
            String line;
            int lineIndex = 0;
            while ((line = bufferedReader.readLine()) != null) {
                processLine(line, lineIndex);
                lineIndex++;
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        bufferedReader.close();
    }

    /**
     * Displays the menu to the user.
     */
    private void showMenu() {
        while (running) {
            System.out.println("=== Menu ===");
            System.out.println("1. Find a person");
            System.out.println("2. Print all people");
            System.out.println("0. Exit");

            int input = getUserInput("Choose an option: ");
            switch (input) {
                case 1:
                    findPeople();
                    break;
                case 2:
                    printAllPeople();
                    break;
                case 0:
                    System.out.println("Bye!");
                    running = false;
                    break;
                default:
                    System.out.println("Incorrect option! Try again.");
            }
        }
    }

    /**
     * Processes a single line from the file, extracts information, and populates with it people ArrayList
     * and inverted index Map.
     *
     * @param line The line from the file to process.
     * @param lineIndex The index of the line in the file.
     */
    private void processLine(String line, int lineIndex) {
        ArrayList<String> parts = new ArrayList<>(Arrays.asList(line.split(" ")));
        String firstName = parts.get(0);
        String lastName = parts.get(1);
        String email = parts.size() > 2 ? parts.get(2) : null;

        Person person = new Person(firstName, lastName, email);
        this.people.add(person);

        addToInvertedIndex(firstName, lineIndex);
        addToInvertedIndex(lastName, lineIndex);
        if (email != null) {
            addToInvertedIndex(email, lineIndex);
        }
    }

    /**
     * Adds a word to the inverted index Map.
     *
     * @param word The word to add.
     * @param lineIndex The index of the line in which the word was found.
     */
    private void addToInvertedIndex(String word, int lineIndex) {
        word = word.toLowerCase();
        invertedIndex.putIfAbsent(word, new HashSet<>());
        invertedIndex.get(word).add(lineIndex);
    }

    /**
     * Retrieves an integer input from the user, checking the validity of it.
     *
     * @param prompt The prompt to display.
     * @return The integer input.
     */
    private int getUserInput(String prompt) {
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                return scanner.nextInt();
            } else {
                System.out.println("Invalid input. Please enter a number.");
                scanner.nextLine();
            }
        }
    }

    /**
     * Searches for people based on user input and matching strategy.
     */
    private void findPeople() {
        String strategy = getSearchStrategy();
        String query = getSearchQuery();

        if (query == null || query.trim().isEmpty()) {
            System.out.println("Search query cannot be empty");
            return;
        }

        ArrayList<String> queryWords = new ArrayList<>(Arrays.asList(query.split(" ")));
        Set<Integer> matchingIndices = findMatchingPeople(strategy, queryWords);

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
            String strategy = scanner.nextLine().toUpperCase().trim();
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
     * Finds people who match all of the query words (ALL).
     *
     * @param queryWords A list of words to search for.
     * @return A set of indices of matching people.
     */
    private Set<Integer> findMatchingAll(ArrayList<String> queryWords) {
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
        Set<Integer> allIndices = new HashSet<>();
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

    /**
     * Prints all the people stored in the system.
     */
    private void printAllPeople() {
        System.out.println("All people in the system:");
        System.out.println(people);
    }
}
