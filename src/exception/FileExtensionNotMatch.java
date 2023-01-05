package exception;

public class FileExtensionNotMatch extends RuntimeException {

    public FileExtensionNotMatch(String message) {
        super(message);
    }
}
