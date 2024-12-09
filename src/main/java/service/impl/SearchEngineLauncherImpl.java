package service.impl;

import exception.FileNotFoundException;
import lombok.extern.slf4j.Slf4j;
import service.SearchEngine;
import utility.impl.UserPromptUtilityImpl;
import validator.SearchEngineValidator;

/**
 * A simple search engine that allows the user to search for people based on their name or email.
 * It loads data from a file, stores it in an inverted index, and offers a menu to search and display data.
 */
@Slf4j
public class SearchEngineLauncherImpl implements SearchEngine {

    /**
     * Instance Variables
     */
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
            searchEngineValidator.loadDataFromFile(filename);
            userPromptUtility.startMenu();
        } catch (FileNotFoundException exception) {
            log.error("Error: {}", exception.getMessage());
        }
    }
}