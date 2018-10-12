package pl.pg.exception;

public class ValidatorNotFoundException extends RuntimeException {

    public ValidatorNotFoundException(String clazz) {
        super("Cannot find @Validator annotation for class: " + clazz);
    }
}
