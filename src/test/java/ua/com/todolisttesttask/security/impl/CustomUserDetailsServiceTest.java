package ua.com.todolisttesttask.security.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ua.com.todolisttesttask.model.User;
import ua.com.todolisttesttask.service.UserService;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@MockitoSettings(strictness = Strictness.LENIENT)
public class CustomUserDetailsServiceTest {

    private static final String TEST_EMAIL = "test@example.com";
    private static final String TEST_PASSWORD = "testpassword";
    private static final String NOT_A_NUMBER = "notanumber";

    @Mock
    private UserService userService;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @BeforeEach
    public void setUp() {
    }

    @Test
    void whenLoadUserByValidIdUserExists() {
        Long userId = 1L;
        User user = new User();
        user.setEmail(TEST_EMAIL);
        user.setPassword(TEST_PASSWORD);

        when(userService.get(userId)).thenReturn(user);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userId.toString());

        assertNotNull(userDetails);
        assertEquals(TEST_EMAIL, userDetails.getUsername());
        assertEquals(TEST_PASSWORD, userDetails.getPassword());
    }

    @Test
    void whenLoadUserByInvalidIdFormat() {
        assertThrows(UsernameNotFoundException.class,
                () -> customUserDetailsService.loadUserByUsername(NOT_A_NUMBER),
                "Invalid user ID format: " + NOT_A_NUMBER);
    }

    @Test
    void whenLoadUserByValidIdUserDoesNotExist() {
        Long userId = 2L;

        when(userService.get(userId)).thenReturn(null);

        assertThrows(UsernameNotFoundException.class,
                () -> customUserDetailsService.loadUserByUsername(userId.toString()),
                "User with ID: " + userId + " not found");
    }
}
