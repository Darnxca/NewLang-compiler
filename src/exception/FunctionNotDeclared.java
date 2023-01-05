package exception;

public class FunctionNotDeclared extends RuntimeException {

    public FunctionNotDeclared(String message) {
        super(message);
    }
}
