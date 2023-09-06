package ua.com.todolisttesttask.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class UserRequestDto {
    @NotBlank(message = "Email can't be null or blank!")
    @Email(message = "Invalid email format!")
    private String email;
    @NotBlank(message = "Password can't be null or blank!")
    @Size(min = 3, max = 16,
            message = "Password length must be between 3 and 16 characters!")
    @Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{3,}$",
            message = "Password must contain at least one letter and one digit!")
    private String password;
}
