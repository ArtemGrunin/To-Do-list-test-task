package ua.com.todolisttesttask.service.mapper.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ua.com.todolisttesttask.dto.request.TaskRequestDto;
import ua.com.todolisttesttask.dto.response.TaskResponseDto;
import ua.com.todolisttesttask.model.Task;
import ua.com.todolisttesttask.service.UserService;
import ua.com.todolisttesttask.service.mapper.RequestDtoMapper;
import ua.com.todolisttesttask.service.mapper.ResponseDtoMapper;

@RequiredArgsConstructor
@Component
public class TaskMapper implements RequestDtoMapper<TaskRequestDto, Task>,
        ResponseDtoMapper<TaskResponseDto, Task> {
    private final UserService userService;

    @Override
    public Task mapToModel(TaskRequestDto dto) {
        Task task = new Task();
        task.setUser(userService.get(dto.getUserId()));
        task.setCompleted(dto.isCompleted());
        task.setTitle(dto.getTitle());
        task.setDescription(dto.getDescription());
        task.setCompletedAt(dto.getCompletedAt());
        return task;
    }

    @Override
    public Task mapToModel(TaskRequestDto dto, Long userId) {
        Task task = mapToModel(dto);
        task.setUser(userService.get(userId));
        return task;
    }

    @Override
    public TaskResponseDto mapToDto(Task task) {
        TaskResponseDto dto = new TaskResponseDto();
        dto.setId(task.getId());
        dto.setUserId(task.getUserId());
        dto.setCompleted(task.isCompleted());
        dto.setTitle(task.getTitle());
        dto.setDescription(task.getDescription());
        dto.setCreatedAt(task.getCreatedAt());
        dto.setCompletedAt(task.getCompletedAt());
        return dto;
    }
}
