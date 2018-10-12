package pl.pg.exception;

public class TestCaseNotFoundException extends RuntimeException {

    public TestCaseNotFoundException(String message) {
        super(message);
    }

    public TestCaseNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
