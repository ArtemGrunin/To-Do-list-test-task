package ua.com.todolisttesttask.controller;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ua.com.todolisttesttask.dto.request.TaskRequestDto;
import ua.com.todolisttesttask.dto.response.TaskResponseDto;
import ua.com.todolisttesttask.exception.AuthenticationTokenMissingException;
import ua.com.todolisttesttask.model.Task;
import ua.com.todolisttesttask.security.jwt.JwtTokenProvider;
import ua.com.todolisttesttask.service.TaskService;
import ua.com.todolisttesttask.service.mapper.impl.TaskMapper;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final TaskMapper taskMapper;
    private final JwtTokenProvider jwtTokenProvider;
    private final HttpServletRequest request;

    @PostMapping
    public ResponseEntity<TaskResponseDto> create(@RequestBody TaskRequestDto taskRequestDto) {
        Long userId = getCurrentUserId();
        TaskResponseDto response = taskMapper
                .mapToDto(taskService.add(taskMapper.mapToModel(taskRequestDto, userId)));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TaskResponseDto>> getAll() {
        Long userId = getCurrentUserId();
        List<TaskResponseDto> tasks = taskService.getAll(userId)
                .stream()
                .map(taskMapper::mapToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskResponseDto> get(@PathVariable Long id) {
        Long userId = getCurrentUserId();
        TaskResponseDto task = taskMapper.mapToDto(taskService.get(id, userId));
        return ResponseEntity.ok(task);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskResponseDto> update(
            @PathVariable Long id,
            @Valid @RequestBody TaskRequestDto taskRequestDto
    ) {
        Task taskToUpdate = taskMapper.mapToModel(taskRequestDto);
        taskToUpdate.setId(id);
        Task updatedTask = taskService.update(taskToUpdate);
        TaskResponseDto updatedTaskResponse = taskMapper.mapToDto(updatedTask);
        return ResponseEntity.ok(updatedTaskResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        Long userId = getCurrentUserId();
        taskService.delete(id, userId);
        return ResponseEntity.noContent().build();
    }

    private Long getCurrentUserId() {
        String token = jwtTokenProvider.resolveToken(request);
        if (token != null) {
            return jwtTokenProvider.getUserId(token);
        }
        throw new AuthenticationTokenMissingException("Authentication token is missing!");
    }
}
