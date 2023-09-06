package ua.com.todolisttesttask.service;

import ua.com.todolisttesttask.model.User;

public interface UserService {
    User create(User user);

    User get(Long id);

    User findByEmail(String email);
}
