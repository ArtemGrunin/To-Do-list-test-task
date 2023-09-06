package ua.com.todolisttesttask.service.impl;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;
import ua.com.todolisttesttask.exception.UserAlreadyExistsException;
import ua.com.todolisttesttask.exception.UserNotFoundException;
import ua.com.todolisttesttask.model.User;
import ua.com.todolisttesttask.repository.UserRepository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);

        user = new User();
        user.setEmail("test@example.com");
        user.setPassword("password");
    }

    @Test
    public void testCreateUserWithExistingEmail() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        assertThrows(UserAlreadyExistsException.class, () -> userService.create(user));
    }

    @Test
    public void testCreateUserWithValidData() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());
        when(passwordEncoder.encode(any())).thenReturn("encodedPassword");
        when(userRepository.save(any())).thenReturn(user);

        User savedUser = userService.create(user);
        assertEquals("encodedPassword", savedUser.getPassword());
    }

    @Test
    public void testGetUserWithValidId() {
        user.setId(1L);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        User foundUser = userService.get(1L);
        assertEquals(1L, foundUser.getId());
    }

    @Test
    public void testGetUserWithInvalidId() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.get(1L));
    }

    @Test
    public void testFindByEmailWithExistingEmail() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(user));

        User foundUser = userService.findByEmail("test@example.com");
        assertEquals("test@example.com", foundUser.getEmail());
    }

    @Test
    public void testFindByEmailWithInvalidEmail() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, ()
                -> userService.findByEmail("test@example.com"));
    }
}
