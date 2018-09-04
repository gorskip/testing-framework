package pl.pg.exception;

public class CannotCreateNewInstanceException extends RuntimeException {

    public CannotCreateNewInstanceException(String message, Throwable cause) {
        super(message, cause);
    }
}
