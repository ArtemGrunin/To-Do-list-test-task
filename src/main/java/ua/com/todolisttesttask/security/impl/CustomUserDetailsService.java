package ua.com.todolisttesttask.security.impl;

import static org.springframework.security.core.userdetails.User.withUsername;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User.UserBuilder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ua.com.todolisttesttask.model.User;
import ua.com.todolisttesttask.service.UserService;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String userIdAsString) throws UsernameNotFoundException {
        Long userId;
        try {
            userId = Long.parseLong(userIdAsString);
        } catch (NumberFormatException e) {
            throw new UsernameNotFoundException("Invalid user ID format: " + userIdAsString);
        }
        User user = userService.get(userId);

        if (user == null) {
            throw new UsernameNotFoundException("User with ID: " + userId + " not found");
        }

        UserBuilder builder = withUsername(user.getEmail());
        builder.password(user.getPassword());
        return builder.build();
    }
}
