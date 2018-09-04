package pl.pg.exception;

public class CannotBuildSSLContextException extends RuntimeException {

    public CannotBuildSSLContextException(Throwable cause) {
        super(cause);
    }
}
