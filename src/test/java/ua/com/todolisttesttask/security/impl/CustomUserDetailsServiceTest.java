package ua.com.todolisttesttask.security.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import ua.com.todolisttesttask.model.User;
import ua.com.todolisttesttask.service.UserService;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

public class CustomUserDetailsServiceTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testLoadUserByValidIdUserExists() {
        Long userId = 1L;
        User user = new User();
        user.setEmail("test@example.com");
        user.setPassword("testpassword");

        when(userService.get(userId)).thenReturn(user);

        UserDetails userDetails = customUserDetailsService.loadUserByUsername(userId.toString());

        assertNotNull(userDetails);
        assertEquals(user.getEmail(), userDetails.getUsername());
        assertEquals(user.getPassword(), userDetails.getPassword());
    }

    @Test
    public void testLoadUserByInvalidIdFormat() {
        String invalidUserId = "notanumber";

        assertThrows(UsernameNotFoundException.class,
                () -> customUserDetailsService.loadUserByUsername(invalidUserId),
                "Invalid user ID format: " + invalidUserId);
    }

    @Test
    public void testLoadUserByValidIdUserDoesNotExist() {
        Long userId = 2L;

        when(userService.get(userId)).thenReturn(null);

        assertThrows(UsernameNotFoundException.class,
                () -> customUserDetailsService.loadUserByUsername(userId.toString()),
                "User with ID: " + userId + " not found");
    }
}
