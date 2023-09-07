package ua.com.todolisttesttask.security.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.crypto.password.PasswordEncoder;
import ua.com.todolisttesttask.exception.AuthenticationException;
import ua.com.todolisttesttask.model.User;
import ua.com.todolisttesttask.service.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@MockitoSettings(strictness = Strictness.LENIENT)
public class AuthenticationServiceImplTest {

    private static final String EMAIL = "test@example.com";
    private static final String CORRECT_PASSWORD = "password";
    private static final String INCORRECT_PASSWORD = "wrongPassword";
    private static final String INVALID_LOGIN_OR_PASSWORD_MSG = "Invalid login or password.";

    @Mock
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AuthenticationServiceImpl authenticationService;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setEmail(EMAIL);
        user.setPassword(CORRECT_PASSWORD);

        when(userService.findByEmail(EMAIL)).thenReturn(user);
        when(passwordEncoder.matches(CORRECT_PASSWORD, user.getPassword())).thenReturn(true);
        when(passwordEncoder.matches(INCORRECT_PASSWORD, user.getPassword())).thenReturn(false);
    }

    @Test
    void whenLoginSuccess() {
        User loggedInUser = authenticationService.login(EMAIL, CORRECT_PASSWORD);

        assertNotNull(loggedInUser);
        assertEquals(EMAIL, loggedInUser.getEmail());
    }

    @Test
    void whenLoginFailDueToWrongPassword() {
        assertThrows(AuthenticationException.class, () ->
                        authenticationService.login(EMAIL, INCORRECT_PASSWORD),
                INVALID_LOGIN_OR_PASSWORD_MSG
        );
    }

    @Test
    void whenLoginFailDueToUserNotFound() {
        when(userService.findByEmail(EMAIL)).thenReturn(null);

        assertThrows(AuthenticationException.class, () ->
                        authenticationService.login(EMAIL, CORRECT_PASSWORD),
                INVALID_LOGIN_OR_PASSWORD_MSG
        );
    }
}
