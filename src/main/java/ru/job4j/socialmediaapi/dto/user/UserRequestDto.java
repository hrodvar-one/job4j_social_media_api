package ru.job4j.socialmediaapi.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO для регистрации или создания пользователя")
public class UserRequestDto {

    @NotBlank(message = "username не может быть пустым")
    @Schema(description = "Имя пользователя (логин)", example = "johndoe")
    private String username;

    @NotBlank(message = "email не может быть пустым")
    @Schema(description = "Электронная почта пользователя", example = "johndoe@example.com", format = "email")
    private String email;

    @NotBlank(message = "password не может быть пустым")
    @Length(min = 8,
            max = 25,
            message = "пароль должен содержать не менее 8 и не более 25 символов")
    @Schema(description = "Пароль (от 8 до 25 символов)", example = "securePass123")
    private String password;
}
