package exception;

/**
 * Exception designed for cases when file access is denied.
 */
public class FileNotFoundException extends RuntimeException {

    /**
     * Constructor for the exception
     * @param message Error message to be shown to the user.
     */
    public FileNotFoundException(String message) {
        super(message);
    }
}
