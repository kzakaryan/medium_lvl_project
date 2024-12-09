package validator;

public class SearchEngineValidator {

    /**
     * Validates that the provided filename is not null or empty.
     * @param filename The name of the file containing the data to be loaded.
     * @throws IllegalArgumentException if the filename is null or empty.
     */
    public void validateInputFile(String filename) {
        if (filename == null || filename.trim().isEmpty()) {
            throw new IllegalArgumentException("Filename cannot be null or empty");
        }
    }
}
