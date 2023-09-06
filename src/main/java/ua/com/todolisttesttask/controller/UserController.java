package ua.com.todolisttesttask.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ua.com.todolisttesttask.dto.request.UserRequestDto;
import ua.com.todolisttesttask.dto.response.UserResponseDto;
import ua.com.todolisttesttask.model.User;
import ua.com.todolisttesttask.service.UserService;
import ua.com.todolisttesttask.service.mapper.RequestDtoMapper;
import ua.com.todolisttesttask.service.mapper.ResponseDtoMapper;

@RestController
@Tag(name = "User Operations",
        description = "Operations related to user registration in the system")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final RequestDtoMapper<UserRequestDto, User> userRequestMapper;
    private final ResponseDtoMapper<UserResponseDto, User> userResponseMapper;

    @PostMapping("/register")
    @Operation(summary = "Register a new user",
            description = "Allows user to register in the system")
    public UserResponseDto register(
            @Valid
            @Parameter(description = "User details for registration", required = true,
                    schema = @Schema(implementation = UserRequestDto.class))
            @RequestBody UserRequestDto dto) {
        User user = userRequestMapper.mapToModel(dto);
        User registeredUser = userService.create(user);
        return userResponseMapper.mapToDto(registeredUser);
    }
}
