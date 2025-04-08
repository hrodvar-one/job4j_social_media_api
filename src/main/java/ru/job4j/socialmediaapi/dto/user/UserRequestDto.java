package ru.job4j.socialmediaapi.dto.user;

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
public class UserRequestDto {

    @NotBlank(message = "username не может быть пустым")
    private String username;

    @NotBlank(message = "email не может быть пустым")
    private String email;

    @NotBlank(message = "password не может быть пустым")
    @Length(min = 8,
            max = 25,
            message = "пароль должен содержать не менее 8 и не более 25 символов")
    private String password;
}
