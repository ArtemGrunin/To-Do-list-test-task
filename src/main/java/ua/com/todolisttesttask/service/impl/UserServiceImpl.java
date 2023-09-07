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
            throw new UserAlreadyExistsException(
                    String.format("User with email: %s already exists", user.getEmail())
            );
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User get(Long id) {
        return userRepository.findById(id)
                .orElseThrow(()
                                -> new UserNotFoundException(
                                String.format("User with id %d was not found", id)
                        )
                );
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(()
                                -> new UserNotFoundException(
                                String.format("User with email: %s not found", email)
                        )
                );
    }
}
