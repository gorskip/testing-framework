package pl.pg.exception;

public class CannotFindDbConfigurationException extends RuntimeException {

    public CannotFindDbConfigurationException(String message) {
        super(message);
    }
}
