import logic.SearchEngineLaunch;
import model.Person;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<Person> people = new ArrayList<>();
        Map<String, Set<Integer>> invertedIndex = new HashMap<>();
        String filename = null;

        for (int i = 0; i < args.length; i++) {
            if ("--data".equals(args[i])) {
                filename = args[i + 1];
                break;
            }
        }
        SearchEngineLaunch.startSearchEngine(people, invertedIndex, filename);
    }
}