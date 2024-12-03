import java.io.*;
import java.util.*;

public class Main {
    static class Person {

        private String firstName;
        private String lastName;
        private String email;

        public Person(String firstName, String lastName, String email) {
            this.firstName = firstName;
            this.lastName = lastName;
            this.email = email;
        }

        public String getFirstName() {
            return firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public String getEmail() {
            return email;
        }

        @Override
        public String toString() {
            return firstName + " " + lastName + " " + email;
        }

        public boolean matches(String query) {
            String lowerCaseQuery = query.toLowerCase().trim();
            return firstName.toLowerCase().contains(lowerCaseQuery) ||
                    lastName.toLowerCase().contains(lowerCaseQuery) ||
                    (email != null && email.toLowerCase().contains(lowerCaseQuery));
        }
    }

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

        if (filename == null) {
            System.out.println("Error");
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(filename))) {
            String line;
            int lineIndex = 0;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(" ");
                String firstName = parts[0];
                String lastName = parts[1];
                String email = parts.length > 2 ? parts[2] : null;
                Person person = new Person(firstName, lastName, email);
                people.add(person);

                addToInvertedIndex(invertedIndex, firstName, lineIndex);
                addToInvertedIndex(invertedIndex, lastName, lineIndex);
                if (email != null) {
                    addToInvertedIndex(invertedIndex, email, lineIndex);
                }
                lineIndex++;
            }
        } catch (IOException e) {
            System.out.println("Error.");
            return;
        }

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("=== Menu ===");
            System.out.println("1. Find a person");
            System.out.println("2. Print all people");
            System.out.println("0. Exit");

            int input = scanner.nextInt();
            scanner.nextLine();

            switch (input) {
                case 1:
                    findPeople(scanner, invertedIndex, people);
                    break;
                case 2:
                    printPeople(people);
                    break;
                case 0:
                    System.out.println("Bye!");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Incorrect option! Try again.");
            }
        }
    }

    private static void addToInvertedIndex(Map<String, Set<Integer>> invertedIndex, String word, int lineIndex) {
        word = word.toLowerCase();
        invertedIndex.putIfAbsent(word, new HashSet<>());
        invertedIndex.get(word).add(lineIndex);
    }

    private static void findPeople(Scanner scanner, Map<String, Set<Integer>> invertedIndex, List<Person> people) {
        System.out.print("Select a matching strategy: ALL, ANY, NONE: ");
        String strategy = scanner.nextLine().toUpperCase().trim();

        System.out.print("Enter a name or email to search all suitable people: ");
        String query = scanner.nextLine().toLowerCase().trim();

        String[] queryWords = query.split(" ");

        Set<Integer> matchingIndices;
        switch (strategy) {
            case "ALL":
                matchingIndices = findMatchingAll(invertedIndex, queryWords, people.size());
                break;
            case "ANY":
                matchingIndices = findMatchingAny(invertedIndex, queryWords, people.size());
                break;
            case "NONE":
                matchingIndices = findMatchingNone(invertedIndex, queryWords, people.size());
                break;
            default:
                System.out.println("Error.");
                return;
        }

        if (matchingIndices.isEmpty()) {
            System.out.println("No matching people found.");
        } else {
            for (int index : matchingIndices) {
                System.out.println(people.get(index));
            }
        }
    }

    private static Set<Integer> findMatchingAll(Map<String, Set<Integer>> invertedIndex, String[] queryWords, int totalPeople) {
        Set<Integer> result = new HashSet<>();
        Set<Integer> firstWordMatches = invertedIndex.getOrDefault(queryWords[0], Collections.emptySet());

        for (int index : firstWordMatches) {
            boolean allMatch = true;
            for (String word : queryWords) {
                if (!invertedIndex.getOrDefault(word, Collections.emptySet()).contains(index)) {
                    allMatch = false;
                    break;
                }
            }
            if (allMatch) {
                result.add(index);
            }
        }
        return result;
    }

    private static Set<Integer> findMatchingAny(Map<String, Set<Integer>> invertedIndex, String[] queryWords, int totalPeople) {
        Set<Integer> result = new HashSet<>();
        for (String word : queryWords) {
            result.addAll(invertedIndex.getOrDefault(word, Collections.emptySet()));
        }
        return result;
    }

    private static Set<Integer> findMatchingNone(Map<String, Set<Integer>> invertedIndex, String[] queryWords, int totalPeople) {
        Set<Integer> result = new HashSet<>();
        Set<Integer> allIndices = new HashSet<>();
        for (int i = 0; i < totalPeople; i++) {
            allIndices.add(i);
        }
        Set<Integer> matchingIndices = new HashSet<>();
        for (String word : queryWords) {
            matchingIndices.addAll(invertedIndex.getOrDefault(word, Collections.emptySet()));
        }
        allIndices.removeAll(matchingIndices);
        return allIndices;
    }

    private static void printPeople(List<Person> people) {
        for (Person person : people) {
            System.out.println(person);
        }
    }
}