package exceptions;
import java.io.IOException;

public class IllegalArgumentsException extends IOException{
    public IllegalArgumentsException() {
    }

    public IllegalArgumentsException(String message) {
        super(message);
    }

    public IllegalArgumentsException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalArgumentsException(Throwable cause) {
        super(cause);
    }
}
