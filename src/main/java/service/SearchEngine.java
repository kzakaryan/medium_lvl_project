package service;

import java.io.FileNotFoundException;
import java.io.IOException;
/**
 * This interface defines the contract for a basic search engine system that can
 * load data from a file and provide an interactive search experience.
 */
public interface SearchEngine {

    /**
     * Starts the search engine by loading data from the specified file and providing
     * an interactive menu for the user to search for people based on names or emails.
     *
     * @param filename The name of the file containing the data to be loaded.
     * @throws IllegalArgumentException if the filename is null, empty, or invalid.
     * @throws IOException if there is an error reading from the file.
     */
    void startSearchEngine(String filename) throws IOException;

}