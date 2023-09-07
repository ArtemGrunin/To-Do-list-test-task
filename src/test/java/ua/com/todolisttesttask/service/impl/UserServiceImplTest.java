package ua.com.todolisttesttask.service.impl;

import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.security.crypto.password.PasswordEncoder;
import ua.com.todolisttesttask.exception.UserAlreadyExistsException;
import ua.com.todolisttesttask.exception.UserNotFoundException;
import ua.com.todolisttesttask.model.User;
import ua.com.todolisttesttask.repository.UserRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@MockitoSettings(strictness = Strictness.LENIENT)
public class UserServiceImplTest {

    private static final String TEST_EMAIL = "test@example.com";
    private static final String PASSWORD = "password";
    private static final String ENCODED_PASSWORD = "encodedPassword";

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setEmail(TEST_EMAIL);
        user.setPassword(PASSWORD);
    }

    @Test
    public void createUserWithExistingEmailShouldThrowException() {
        when(userRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.of(user));

        assertThrows(UserAlreadyExistsException.class, () -> userService.create(user));
    }

    @Test
    public void createUserWithValidDataShouldEncodePassword() {
        when(userRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.empty());
        when(passwordEncoder.encode(any())).thenReturn(ENCODED_PASSWORD);
        when(userRepository.save(any())).thenReturn(user);

        User savedUser = userService.create(user);
        assertEquals(ENCODED_PASSWORD, savedUser.getPassword());
    }

    @Test
    public void getUserWithValidIdShouldReturnUser() {
        user.setId(1L);
        when(userRepository.findById(anyLong())).thenReturn(Optional.of(user));

        User foundUser = userService.get(1L);
        assertEquals(1L, foundUser.getId());
    }

    @Test
    public void getUserWithInvalidIdShouldThrowException() {
        when(userRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.get(1L));
    }

    @Test
    public void findByEmailWithExistingEmailShouldReturnUser() {
        when(userRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.of(user));

        User foundUser = userService.findByEmail(TEST_EMAIL);
        assertEquals(TEST_EMAIL, foundUser.getEmail());
    }

    @Test
    public void findByEmailWithInvalidEmailShouldThrowException() {
        when(userRepository.findByEmail(TEST_EMAIL)).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> userService.findByEmail(TEST_EMAIL));
    }
}
