package validator;

import exception.FileNotFoundException;
import lombok.Getter;
import model.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.io.*;

/**
 * Class designed for validations, file checks and user input correctness checks.
 */
public class SearchEngineValidator {

    /**
     * Instance Variables
     */
    Map<String, Set<Integer>> invertedIndex = new HashMap<>();
    List<Person> people = new ArrayList<>();
    private static final Logger log = LoggerFactory.getLogger(SearchEngineValidator.class);

    public Map<String, Set<Integer>> getInvertedIndex() {
        return invertedIndex;
    }

    public List<Person> getPeople() {
        return people;
    }

    /**
     * Validates that the provided filename is not null or empty.
     * @param filename The name of the file containing the data to be loaded.
     * @throws FileNotFoundException if the filename is null or empty.
     */
    public void validateInputFile(String filename) {
        if (filename == null || filename.trim().isEmpty()) {
            throw new FileNotFoundException("Filename cannot be null or empty");
        }
    }

    /**
     * Loads data from the given file and populates the people ArrayList and inverted index Map.
     * @param filename The name of the file containing the data to be loaded.
     */
    public void loadDataFromFile(String filename) {
        File file = new File(filename);
        if (!file.exists() || !file.isFile()) {
            System.out.println("File does not exist or is not a valid file: " + filename);
            return;
        }

        try (var bufferedReader = new BufferedReader(
                new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8))) {

            String line;
            int lineIndex = 0;

            while ((line = bufferedReader.readLine()) != null) {
                System.out.println("Reading line " + lineIndex + ": " + line);
                processLine(line, lineIndex);
                lineIndex++;
            }

        } catch (IOException e) {
            System.out.println("Error reading file. Path: " + filename + ". Error: " + e.getMessage());
            e.printStackTrace();
            log.error("Error reading file: {}", e.getMessage());
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
        people.add(person);

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
        log.info("All people in the system:");
//        log.atInfo().log(people.get(1).toString());
        System.out.println(this.people.toString());
    }
}