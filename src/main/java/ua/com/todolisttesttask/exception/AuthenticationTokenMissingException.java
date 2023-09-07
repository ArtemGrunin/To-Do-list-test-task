package ua.com.todolisttesttask.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
public class AuthenticationTokenMissingException extends RuntimeException {
    public AuthenticationTokenMissingException(String message) {
        super(message);
    }
}
