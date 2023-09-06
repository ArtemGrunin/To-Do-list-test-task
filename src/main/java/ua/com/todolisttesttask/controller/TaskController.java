package ua.com.todolisttesttask.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ua.com.todolisttesttask.dto.request.TaskRequestDto;
import ua.com.todolisttesttask.dto.response.TaskResponseDto;
import ua.com.todolisttesttask.exception.AuthenticationTokenMissingException;
import ua.com.todolisttesttask.model.Task;
import ua.com.todolisttesttask.security.jwt.JwtTokenProvider;
import ua.com.todolisttesttask.service.TaskService;
import ua.com.todolisttesttask.service.mapper.impl.TaskMapper;
import ua.com.todolisttesttask.util.SortService;

@RestController
@RequestMapping("/tasks")
@Tag(name = "Task Operations",
        description = "Operations related to managing tasks in the system")
@RequiredArgsConstructor
public class TaskController {
    private final TaskService taskService;
    private final SortService sortService;
    private final TaskMapper taskMapper;
    private final JwtTokenProvider jwtTokenProvider;
    private final HttpServletRequest request;

    @PostMapping
    @Operation(summary = "Add a task to DB", description = "Allows user to add a new task")
    public ResponseEntity<TaskResponseDto> create(
            @Parameter(description = "Task to add", required = true,
                    schema = @Schema(implementation = TaskRequestDto.class))
            @RequestBody TaskRequestDto taskRequestDto) {
        Long userId = getCurrentUserId();
        TaskResponseDto response = taskMapper
                .mapToDto(taskService.add(taskMapper.mapToModel(taskRequestDto, userId)));
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping
    @Operation(summary = "Retrieve all tasks with pagination",
            description = "List all tasks with optional pagination")
    public ResponseEntity<List<TaskResponseDto>> getAll(
            @RequestParam(defaultValue = "10") Integer amount,
            @RequestParam(defaultValue = "0") Integer page,
            @RequestParam(defaultValue = "id") String sortBy) {
        Long userId = getCurrentUserId();
        Sort sort = Sort.by(sortService.getSortOrders(sortBy));
        PageRequest pageRequest = PageRequest.of(page, amount, sort);
        List<TaskResponseDto> tasks = taskService.getAll(userId, pageRequest)
                .stream()
                .map(taskMapper::mapToDto)
                .collect(Collectors.toList());
        return ResponseEntity.ok(tasks);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Retrieve a task by id", description = "Get task details by task ID")
    public ResponseEntity<TaskResponseDto> get(@PathVariable Long id) {
        Long userId = getCurrentUserId();
        TaskResponseDto task = taskMapper.mapToDto(taskService.get(id, userId));
        return ResponseEntity.ok(task);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update a task by id",
            description = "Allows user to update an existing task by ID")
    public ResponseEntity<TaskResponseDto> update(
            @PathVariable Long id,
            @Parameter(description = "Task to update", required = true,
                    schema = @Schema(implementation = TaskRequestDto.class))
            @Valid @RequestBody TaskRequestDto taskRequestDto) {
        Task taskToUpdate = taskMapper.mapToModel(taskRequestDto);
        taskToUpdate.setId(id);
        Task updatedTask = taskService.update(taskToUpdate);
        TaskResponseDto updatedTaskResponse = taskMapper.mapToDto(updatedTask);
        return ResponseEntity.ok(updatedTaskResponse);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a task by id", description = "Allows user to delete a task by ID")
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
