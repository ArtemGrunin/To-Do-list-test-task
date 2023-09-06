package ua.com.todolisttesttask.controller;

import jakarta.validation.Valid;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ua.com.todolisttesttask.dto.request.UserRequestDto;
import ua.com.todolisttesttask.exception.AuthenticationException;
import ua.com.todolisttesttask.model.User;
import ua.com.todolisttesttask.security.AuthenticationService;
import ua.com.todolisttesttask.security.jwt.JwtTokenProvider;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid UserRequestDto requestDto)
            throws AuthenticationException {
        User user = authenticationService.login(
                requestDto.getEmail(),
                requestDto.getPassword());
        String token = jwtTokenProvider.createToken(user.getId());
        return new ResponseEntity<>(Map.of("token", token), HttpStatus.OK);
    }
}
