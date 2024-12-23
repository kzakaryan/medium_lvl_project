package utility.impl;

import mechanism.impl.SearchMechanismImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import utility.UserPromptUtility;
import validator.SearchEngineValidator;
import java.util.Scanner;

/**
 * Class designed for CLI - User interaction.
 */
public class UserPromptUtilityImpl implements UserPromptUtility {

    private boolean running = true;
    private final SearchEngineValidator searchEngineValidator;
    private final SearchMechanismImpl searchMechanism;
    private final Scanner scanner = new Scanner(System.in);
    private static final Logger log = LoggerFactory.getLogger(UserPromptUtilityImpl.class);

    public UserPromptUtilityImpl(SearchEngineValidator searchEngineValidator) {
        this.searchEngineValidator = searchEngineValidator;
        this.searchMechanism = new SearchMechanismImpl(
                searchEngineValidator.getInvertedIndex(),
                searchEngineValidator.getPeople()
        );
    }

    /**
     * Starts the main interaction loop.
     */
    @Override
    public void startMenu() {
        while (running) {
            displayMenu();
            handleUserInput();
        }
        close();
    }

    /**
     * Displays the menu to the user.
     */
    private void displayMenu() {
        log.info("\nMenu:");
        log.info("1. Search for people");
        log.info("2. Print all people");
        log.info("0. Exit");
    }

    /**
     * Handles user input based on menu selection.
     */
    private void handleUserInput() {
        log.info("Enter your choice: ");
        String choice = scanner.nextLine().trim();

        switch (choice) {
            case "1":
                searchMechanism.findPeople();
                break;
            case "2":
                searchEngineValidator.printAllPeople();
                break;
            case "0":
                stop();
                break;
            default:
                log.info("Invalid choice. Please try again.");
        }
    }

    /**
     * Stops the main interaction loop.
     */
    public void stop() {
        running = false;
        log.info("Exiting program. Goodbye!");
    }

    /**
     * Closes resources.
     */
    public void close() {
        if (scanner != null) {
            scanner.close();
        }
    }
}