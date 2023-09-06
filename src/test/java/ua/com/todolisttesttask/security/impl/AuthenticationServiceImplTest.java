package ua.com.todolisttesttask.security.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import ua.com.todolisttesttask.exception.AuthenticationException;
import ua.com.todolisttesttask.model.User;
import ua.com.todolisttesttask.service.UserService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class AuthenticationServiceImplTest {

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    private final String email = "test@example.com";
    private final String correctPassword = "password";
    private final String incorrectPassword = "wrongPassword";
    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        user = new User();
        user.setEmail(email);
        user.setPassword(correctPassword);

        when(userService.findByEmail(email)).thenReturn(user);
        when(passwordEncoder.matches(correctPassword, user.getPassword())).thenReturn(true);
        when(passwordEncoder.matches(incorrectPassword, user.getPassword())).thenReturn(false);
    }

    @Test
    public void testLoginSuccess() {
        User loggedInUser = authenticationService.login(email, correctPassword);

        assertNotNull(loggedInUser);
        assertEquals(email, loggedInUser.getEmail());
    }

    @Test
    public void testLoginFailWrongPassword() {
        assertThrows(AuthenticationException.class, () -> {
            authenticationService.login(email, incorrectPassword);
        }, "Invalid login or password.");
    }

    @Test
    public void testLoginFailUserNotFound() {
        when(userService.findByEmail(email)).thenReturn(null);

        assertThrows(AuthenticationException.class, () -> {
            authenticationService.login(email, correctPassword);
        }, "Invalid login or password.");
    }
}
