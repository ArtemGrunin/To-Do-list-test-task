package ua.com.todolisttesttask.service.mapper.impl;

import org.springframework.stereotype.Component;
import ua.com.todolisttesttask.dto.request.UserRequestDto;
import ua.com.todolisttesttask.dto.response.UserResponseDto;
import ua.com.todolisttesttask.model.User;
import ua.com.todolisttesttask.service.mapper.RequestDtoMapper;
import ua.com.todolisttesttask.service.mapper.ResponseDtoMapper;

@Component
public class UserMapper implements RequestDtoMapper<UserRequestDto, User>,
        ResponseDtoMapper<UserResponseDto, User> {
    @Override
    public User mapToModel(UserRequestDto dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        return user;
    }

    @Override
    public UserResponseDto mapToDto(User user) {
        UserResponseDto dto = new UserResponseDto();
        dto.setId(user.getId());
        dto.setEmail(user.getEmail());
        return dto;
    }
}
