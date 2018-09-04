package exception;

public class StoryFileNotFoundException extends RuntimeException {

    public StoryFileNotFoundException(String message) {
        super(message);
    }
}
