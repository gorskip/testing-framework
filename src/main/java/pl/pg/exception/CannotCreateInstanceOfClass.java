package pl.pg.exception;

public class CannotCreateInstanceOfClass extends RuntimeException {

    public CannotCreateInstanceOfClass(String message, Throwable cause) {
        super(message, cause);
    }
}
