package ua.com.todolisttesttask.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Authentication Operations",
        description = "Operations related to user authentication")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/login")
    @Operation(summary = "User login",
            description = "Allows user to log in and get a JWT token")
    public ResponseEntity<Object> login(
            @RequestBody
            @Valid
            @Parameter(description = "User login details", required = true,
                    schema = @Schema(implementation = UserRequestDto.class))
            UserRequestDto requestDto) throws AuthenticationException {
        User user = authenticationService.login(
                requestDto.getEmail(),
                requestDto.getPassword());
        String token = jwtTokenProvider.createToken(user.getId());
        return new ResponseEntity<>(Map.of("token", token), HttpStatus.OK);
    }
}
