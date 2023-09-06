package ua.com.todolisttesttask.security;

import ua.com.todolisttesttask.exception.AuthenticationException;
import ua.com.todolisttesttask.model.User;

public interface AuthenticationService {
    User login(String email, String password) throws AuthenticationException;
}
