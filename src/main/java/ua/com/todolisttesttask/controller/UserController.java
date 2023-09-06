package ua.com.todolisttesttask.controller;

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
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final RequestDtoMapper<UserRequestDto, User> userRequestMapper;
    private final ResponseDtoMapper<UserResponseDto, User> userResponseMapper;

    @PostMapping("/register")
    public UserResponseDto register(@Valid @RequestBody UserRequestDto dto) {
        User user = userRequestMapper.mapToModel(dto);
        User registeredUser = userService.create(user);
        return userResponseMapper.mapToDto(registeredUser);
    }
}
