package semantic.exception;

public class MultipleIdentifierDeclaration extends RuntimeException {

    public MultipleIdentifierDeclaration(String message) {
        super(message);
    }
}
