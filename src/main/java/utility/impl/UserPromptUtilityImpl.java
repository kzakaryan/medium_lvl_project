package utility.impl;

import search.impl.SearchMechanismImpl;
import service.impl.SearchEngineLauncherImpl;
import utility.UserPromptUtility;

import java.util.Scanner;

public class UserPromptUtilityImpl implements UserPromptUtility {

    boolean running = true;
    SearchEngineLauncherImpl searchEngineLauncher = new SearchEngineLauncherImpl();
    SearchMechanismImpl searchMechanism = new SearchMechanismImpl();
    Scanner scanner = new Scanner(System.in);

    /**
     * Displays the menu to the user.
     */
    @Override
    public void startMenu() {
        while (running) {
            System.out.println("=== Menu ===");
            System.out.println("1. Find a person");
            System.out.println("2. Print all people");
            System.out.println("0. Exit");

            int input = getUserInput();
            switch (input) {
                case 1:
                    searchMechanism.findPeople();
                    break;
                case 2:
                    searchEngineLauncher.printAllPeople();
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
     * Retrieves an integer input from the user, checking the validity of it.
     *
     * @return The integer input.
     */
    private int getUserInput() {
        while (true) {
            System.out.print("Choose an option: ");
            if (scanner.hasNextInt()) {
                return scanner.nextInt();
            } else {
                System.out.println("Invalid input. Please enter correct number.");
                scanner.nextLine();
            }
        }
    }
}