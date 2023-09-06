package ua.com.todolisttesttask.service;

import java.util.List;
import ua.com.todolisttesttask.model.Task;

public interface TaskService {
    Task add(Task task);

    Task get(Long taskId, Long userId);

    List<Task> getAll(Long userId);

    Task update(Task task);

    void delete(Long taskId, Long userId);
}
