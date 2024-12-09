package utility.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import search.impl.SearchMechanismImpl;
import utility.UserPromptUtility;
import validator.SearchEngineValidator;
import java.util.Scanner;

/**
 * Class designed for CLI - User interaction.
 */
public class UserPromptUtilityImpl implements UserPromptUtility {

    boolean running = true;
    SearchEngineValidator searchEngineValidator = new SearchEngineValidator();
    SearchMechanismImpl searchMechanism = new SearchMechanismImpl();
    Scanner scanner = new Scanner(System.in);
    private static final Logger log = LoggerFactory.getLogger(SearchMechanismImpl.class);

    /**
     * Displays the menu to the user.
     */
    @Override
    public void startMenu() {
        while (running) {

            log.info("=== Menu ===");
            log.info("1. Find a person");
            log.info("2. Print all people");
            log.info("0. Exit");

            int input = getUserInput();
            switch (input) {
                case 1:
                    searchMechanism.findPeople();
                    break;
                case 2:
                    searchEngineValidator.printAllPeople();
                    break;
                case 0:
                    log.info("Bye!");
                    running = false;
                    break;
                default:
                    log.info("Incorrect option! Try again.");
            }
        }
    }

    /**
     * Retrieves an integer input from the user, checking the validity of it.
     *
     * @return The integer input.
     */
    private int getUserInput() {
        while (true) {
            log.info("Choose an option: ");
            if (scanner.hasNextInt()) {
                return scanner.nextInt();
            } else {
                log.info("Invalid input. Please enter correct number.");
                scanner.nextLine();
            }
        }
    }
}