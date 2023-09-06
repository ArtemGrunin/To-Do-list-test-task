package ua.com.todolisttesttask.service.impl;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.todolisttesttask.exception.TaskNotFoundException;
import ua.com.todolisttesttask.model.Task;
import ua.com.todolisttesttask.repository.TaskRepository;
import ua.com.todolisttesttask.service.TaskService;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;

    @Override
    public Task add(Task task) {
        return taskRepository.save(task);
    }

    @Override
    public Task get(Long taskId, Long userId) {
        return taskRepository.findByIdAndUser_Id(taskId, userId)
                .orElseThrow(() -> new TaskNotFoundException("Task with ID " + taskId
                        + " not found or not owned by user with ID " + userId));
    }

    @Override
    public List<Task> getAll(Long userId, PageRequest pageRequest) {
        return taskRepository.findAllByUser_Id(userId, pageRequest);
    }

    @Override
    public Task update(Task task) {
        if (!taskRepository.findByIdAndUser_Id(task.getId(), task.getUserId()).isPresent()) {
            throw new TaskNotFoundException("Task with ID " + task.getId()
                    + " not found or not owned by user with ID " + task.getUserId());
        }
        return taskRepository.save(task);
    }

    @Transactional
    @Override
    public void delete(Long taskId, Long userId) {
        if (!taskRepository.findByIdAndUser_Id(taskId, userId).isPresent()) {
            throw new TaskNotFoundException("Task with ID " + taskId
                    + " not found or not owned by user with ID " + userId);
        }
        taskRepository.deleteByIdAndUser_Id(taskId, userId);
    }
}
