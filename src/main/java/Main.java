import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

import service.SearchEngine;
import service.impl.SearchEngineLauncherImpl;

/**
 * Main Class to run the application
 */
public class Main {
    /**
     * main method to run the application
     * @param args input (file path for cl input)
     * @throws FileNotFoundException when file is null
     * @throws IOException when file not found
     */
    public static void main(String[] args) throws IOException {
        String filename = null;
        for (int i = 0; i < args.length; i++) {
            if ("--data".equals(args[i])) {
                filename = args[i + 1];
                break;
            }
        }
        SearchEngine searchEngine = new SearchEngineLauncherImpl();
        searchEngine.startSearchEngine(filename);
    }
}