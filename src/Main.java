import logic.SearchEngineLaunch;

public class Main {
    public static void main(String[] args) {
        String filename = null;

        for (int i = 0; i < args.length; i++) {
            if ("--data".equals(args[i])) {
                filename = args[i + 1];
                break;
            }
        }
        SearchEngineLaunch.startSearchEngine(filename);
    }
}