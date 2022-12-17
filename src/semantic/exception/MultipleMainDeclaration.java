package semantic.exception;

public class MultipleMainDeclaration extends RuntimeException {
    public MultipleMainDeclaration(String message) {
        super(message);
    }

}
