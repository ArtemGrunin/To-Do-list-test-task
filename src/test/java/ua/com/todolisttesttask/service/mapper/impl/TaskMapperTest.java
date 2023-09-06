package ua.com.todolisttesttask.service.mapper.impl;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import ua.com.todolisttesttask.dto.request.TaskRequestDto;
import ua.com.todolisttesttask.dto.response.TaskResponseDto;
import ua.com.todolisttesttask.model.Task;
import ua.com.todolisttesttask.model.User;
import ua.com.todolisttesttask.service.UserService;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

public class TaskMapperTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private TaskMapper taskMapper;

    private TaskRequestDto dto;
    private Task task;
    private final Long userId = 1L;
    private final String title = "Test Task";
    private final String description = "Test Description";
    private final LocalDateTime now = LocalDateTime.now();

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        User user = new User();
        user.setId(userId);
        when(userService.get(userId)).thenReturn(user);

        dto = new TaskRequestDto();
        dto.setUserId(userId);
        dto.setTitle(title);
        dto.setDescription(description);
        dto.setCompletedAt(now);

        task = new Task();
        task.setId(1L);
        task.setUser(user);
        task.setTitle(title);
        task.setDescription(description);
        task.setCompletedAt(now);
    }

    @Test
    public void mapToModelTest() {
        Task mappedTask = taskMapper.mapToModel(dto);

        assertEquals(userId, mappedTask.getUser().getId());
        assertEquals(title, mappedTask.getTitle());
        assertEquals(description, mappedTask.getDescription());
        assertEquals(now, mappedTask.getCompletedAt());
    }

    @Test
    public void mapToModelWithUserIdTest() {
        Task mappedTask = taskMapper.mapToModel(dto, userId);

        assertEquals(userId, mappedTask.getUser().getId());
        assertEquals(title, mappedTask.getTitle());
        assertEquals(description, mappedTask.getDescription());
        assertEquals(now, mappedTask.getCompletedAt());
    }

    @Test
    public void mapToDtoTest() {
        TaskResponseDto mappedDto = taskMapper.mapToDto(task);

        assertEquals(1L, mappedDto.getId());
        assertEquals(userId, mappedDto.getUserId());
        assertEquals(title, mappedDto.getTitle());
        assertEquals(description, mappedDto.getDescription());
        assertEquals(now, mappedDto.getCompletedAt());
    }
}
