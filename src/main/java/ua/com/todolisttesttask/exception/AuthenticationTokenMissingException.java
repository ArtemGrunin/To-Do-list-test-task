package ua.com.todolisttesttask.exception;

public class AuthenticationTokenMissingException extends RuntimeException {
    public AuthenticationTokenMissingException(String message) {
        super(message);
    }
}
