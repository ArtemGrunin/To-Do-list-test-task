package ua.com.todolisttesttask.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.com.todolisttesttask.exception.UserAlreadyExistsException;
import ua.com.todolisttesttask.exception.UserNotFoundException;
import ua.com.todolisttesttask.model.User;
import ua.com.todolisttesttask.repository.UserRepository;
import ua.com.todolisttesttask.service.UserService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User create(User user) {
        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new UserAlreadyExistsException("User with email: "
                    + user.getEmail() + " already exists");
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User get(Long id) {
        return userRepository.findById(id)
                .orElseThrow(()
                        -> new UserNotFoundException("User with id " + id + " was not found"));
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException("User with email: "
                        + email + " not found"));
    }
}
