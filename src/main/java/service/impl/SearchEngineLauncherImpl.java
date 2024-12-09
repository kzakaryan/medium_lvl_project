package service.impl;

import lombok.extern.slf4j.Slf4j;
import model.Person;
import service.SearchEngine;
import utility.impl.UserPromptUtilityImpl;
import validator.SearchEngineValidator;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * A simple search engine that allows the user to search for people based on their name or email.
 * It loads data from a file, stores it in an inverted index, and offers a menu to search and display data.
 */
@Slf4j
public class SearchEngineLauncherImpl implements SearchEngine {

    /**
     * Instance Variables
     */
    Map<String, Set<Integer>> invertedIndex = new HashMap<>();
    List<Person> people = new ArrayList<>();
    UserPromptUtilityImpl userPromptUtility = new UserPromptUtilityImpl();
    SearchEngineValidator searchEngineValidator = new SearchEngineValidator();

    /**
     * Starts the search engine by loading data from the specified file and displaying the menu to the user.
     * @param filename The name of the file containing the data to be loaded.
     */
    @Override
    public void startSearchEngine(String filename) {
        try {
            searchEngineValidator.validateInputFile(filename);
            loadDataFromFile(filename);
            userPromptUtility.startMenu();
        } catch (IllegalArgumentException exception) {

        }
    }





    /**
     * Loads data from the given file and populates the people ArrayList and inverted index Map.
     * @param filename The name of the file containing the data to be loaded.
     */
    private void loadDataFromFile(String filename) {
        InputStreamReader inputStreamReader = null;
        try {
            inputStreamReader = new InputStreamReader(new FileInputStream(filename), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        var bufferedReader = new BufferedReader(inputStreamReader);
        try (BufferedReader _ = new BufferedReader(new InputStreamReader(new FileInputStream(filename), "UTF-8"))) {
            var line = "";
            var lineIndex = 0;
            while ((line = bufferedReader.readLine()) != null) {
                processLine(line, lineIndex);
                lineIndex++;
            }
        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
        try {
            bufferedReader.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
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
        var parts = new ArrayList<>(Arrays.asList(line.split(" ")));
        var firstName = parts.get(0);
        var lastName = parts.get(1);
        var email = parts.size() > 2 ? parts.get(2) : null;

        var person = new Person(firstName, lastName, email);
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
     * Prints all the people stored in the system.
     */
    public void printAllPeople() {
        System.out.println("All people in the system:");
        System.out.println(people);
    }
}