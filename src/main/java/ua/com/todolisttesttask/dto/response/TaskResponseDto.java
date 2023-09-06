package ua.com.todolisttesttask.dto.response;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class TaskResponseDto {
    private Long id;
    private Long userId;
    private boolean completed;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime completedAt;
}
