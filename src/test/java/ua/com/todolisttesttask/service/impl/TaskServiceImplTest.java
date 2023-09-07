package ua.com.todolisttesttask.service.impl;

import java.util.Optional;
import java.util.Collections;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.springframework.data.domain.PageRequest;
import ua.com.todolisttesttask.exception.TaskNotFoundException;
import ua.com.todolisttesttask.model.Task;
import ua.com.todolisttesttask.model.User;
import ua.com.todolisttesttask.repository.TaskRepository;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@MockitoSettings(strictness = Strictness.LENIENT)
public class TaskServiceImplTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private User user;

    @InjectMocks
    private TaskServiceImpl taskService;

    private Long taskId;
    private Long userId;
    private Task task;

    @BeforeEach
    public void setup() {
        taskId = 1L;
        userId = 1L;
        when(user.getId()).thenReturn(userId);

        task = new Task();
        task.setUser(user);
    }

    @Test
    public void add() {
        when(taskRepository.save(task)).thenReturn(task);

        Task returnedTask = taskService.add(task);
        assertNotNull(returnedTask);
        assertEquals(task, returnedTask);
    }

    @Test
    public void getTaskFound() {
        when(taskRepository.findByIdAndUser_Id(taskId, userId)).thenReturn(Optional.of(task));

        Task returnedTask = taskService.get(taskId, userId);
        assertNotNull(returnedTask);
        assertEquals(task, returnedTask);
    }

    @Test
    public void getTaskNotFound() {
        when(taskRepository.findByIdAndUser_Id(taskId, userId)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.get(taskId, userId));
    }

    @Test
    public void getAll() {
        PageRequest pageRequest = PageRequest.of(0, 10);

        List<Task> tasks = Collections.singletonList(task);
        when(taskRepository.findAllByUser_Id(userId, pageRequest)).thenReturn(tasks);

        List<Task> returnedTasks = taskService.getAll(userId, pageRequest);
        assertNotNull(returnedTasks);
        assertEquals(tasks, returnedTasks);
    }

    @Test
    public void updateTaskFound() {
        when(taskRepository.findByIdAndUser_Id(task.getId(),
                task.getUser().getId())).thenReturn(Optional.of(task));
        when(taskRepository.save(task)).thenReturn(task);

        Task returnedTask = taskService.update(task);
        assertNotNull(returnedTask);
        assertEquals(task, returnedTask);
    }

    @Test
    public void updateTaskNotFound() {
        when(taskRepository.findByIdAndUser_Id(task.getId(),
                task.getUser().getId())).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.update(task));
    }

    @Test
    public void deleteTaskFound() {
        when(taskRepository.findByIdAndUser_Id(taskId, userId)).thenReturn(Optional.of(task));

        taskService.delete(taskId, userId);
        verify(taskRepository, times(1)).deleteByIdAndUser_Id(taskId, userId);
    }

    @Test
    public void deleteTaskNotFound() {
        when(taskRepository.findByIdAndUser_Id(taskId, userId)).thenReturn(Optional.empty());

        assertThrows(TaskNotFoundException.class, () -> taskService.delete(taskId, userId));
        verify(taskRepository, never()).deleteByIdAndUser_Id(taskId, userId);
    }
}
