package ua.com.todolisttesttask.security.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.com.todolisttesttask.exception.AuthenticationException;
import ua.com.todolisttesttask.model.User;
import ua.com.todolisttesttask.security.AuthenticationService;
import ua.com.todolisttesttask.service.UserService;

@Service
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public User login(String email, String password) throws AuthenticationException {
        User user = userService.findByEmail(email);
        if (user == null
                || !passwordEncoder.matches(password, user.getPassword())) {
            throw new AuthenticationException("Invalid login or password.");
        }
        return user;
    }
}
