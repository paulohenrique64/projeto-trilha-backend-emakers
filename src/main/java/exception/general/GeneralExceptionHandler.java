package exception.general;

public class GeneralExceptionHandler extends RuntimeException {
    public GeneralExceptionHandler(String message) {
        super(message);
    }
}
