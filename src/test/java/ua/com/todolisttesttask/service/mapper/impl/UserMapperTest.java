package ua.com.todolisttesttask.service.mapper.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ua.com.todolisttesttask.dto.request.UserRequestDto;
import ua.com.todolisttesttask.dto.response.UserResponseDto;
import ua.com.todolisttesttask.model.User;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserMapperTest {

    private UserMapper userMapper;
    private final String email = "test@example.com";
    private final String password = "password";
    private final Long userId = 1L;

    @BeforeEach
    public void setUp() {
        userMapper = new UserMapper();
    }

    @Test
    public void mapToModelTest() {
        UserRequestDto dto = new UserRequestDto();
        dto.setEmail(email);
        dto.setPassword(password);
        User user = userMapper.mapToModel(dto);
        assertEquals(email, user.getEmail());
        assertEquals(password, user.getPassword());
    }

    @Test
    public void mapToDtoTest() {
        User user = new User();
        user.setId(userId);
        user.setEmail(email);
        UserResponseDto dto = userMapper.mapToDto(user);
        assertEquals(userId, dto.getId());
        assertEquals(email, dto.getEmail());
    }
}
