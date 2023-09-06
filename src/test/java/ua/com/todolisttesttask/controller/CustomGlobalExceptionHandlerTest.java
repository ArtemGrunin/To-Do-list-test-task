package ua.com.todolisttesttask.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import ua.com.todolisttesttask.exception.AuthenticationException;
import ua.com.todolisttesttask.exception.AuthenticationTokenMissingException;
import ua.com.todolisttesttask.exception.InvalidJwtAuthenticationException;
import ua.com.todolisttesttask.exception.TaskNotFoundException;
import ua.com.todolisttesttask.exception.UserAlreadyExistsException;
import ua.com.todolisttesttask.exception.UserNotFoundException;
import static org.junit.jupiter.api.Assertions.assertEquals;

class CustomGlobalExceptionHandlerTest {

    private CustomGlobalExceptionHandler handler;

    @BeforeEach
    void setUp() {
        handler = new CustomGlobalExceptionHandler();
    }

    @Test
    void shouldHandleAuthenticationException() {
        AuthenticationException ex = new AuthenticationException("Unauthorized");

        ResponseEntity<Object> response = handler.handleAuthenticationException(ex);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void shouldHandleAuthenticationTokenMissingException() {
        AuthenticationTokenMissingException ex = new AuthenticationTokenMissingException("Token missing");

        ResponseEntity<Object> response = handler.handleAuthenticationTokenMissingException(ex);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
    }

    @Test
    void shouldHandleInvalidJwtAuthenticationException() {
        InvalidJwtAuthenticationException ex =
                new InvalidJwtAuthenticationException("Invalid token", new Throwable("Some cause"));

        ResponseEntity<Object> response = handler.handleInvalidJwtAuthenticationException(ex);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }

    @Test
    void shouldHandleTaskNotFoundException() {
        TaskNotFoundException ex = new TaskNotFoundException("Task not found");

        ResponseEntity<Object> response = handler.handleTaskNotFoundException(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void shouldHandleUserNotFoundException() {
        UserNotFoundException ex = new UserNotFoundException("User not found");

        ResponseEntity<Object> response = handler.handleUserNotFoundException(ex);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void shouldHandleUserAlreadyExistsException() {
        UserAlreadyExistsException ex = new UserAlreadyExistsException("User already exists");

        ResponseEntity<String> response = handler.handleUserAlreadyExistsException(ex);

        assertEquals(HttpStatus.FORBIDDEN, response.getStatusCode());
    }
}
