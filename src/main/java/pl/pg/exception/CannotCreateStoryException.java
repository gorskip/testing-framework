package pl.pg.exception;

public class CannotCreateStoryException extends RuntimeException {


    public CannotCreateStoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
