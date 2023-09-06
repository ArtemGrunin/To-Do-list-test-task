package ua.com.todolisttesttask.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDateTime;
import lombok.Data;

@Data
public class TaskRequestDto {
    @NotNull(message = "User ID cannot be null.")
    private Long userId;
    private boolean completed;
    @NotBlank(message = "Title cannot be null.")
    @Size(min = 3, max = 255, message = "Title must be between 3 and 255 characters.")
    private String title;
    @Size(max = 500, message = "Description cannot exceed 500 characters.")
    private String description;
    private LocalDateTime completedAt;
}
