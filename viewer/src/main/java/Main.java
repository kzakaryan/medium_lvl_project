import launcher.impl.SearchEngineLauncherImpl;

/**
 * Main Class to run the application
 */
public class Main {
    /**
     * main method to run the application
     * @param args input (file path for cl input)
     */
    public static void main(String[] args) {

        String filename = "file.txt";
        for (int i = 0; i < args.length; i++) {
            if ("--data".equals(args[i])) {
                filename = args[i + 1];
                break;
            }
        }
        var searchEngine = new SearchEngineLauncherImpl();
        searchEngine.startSearchEngine(filename);
    }
}