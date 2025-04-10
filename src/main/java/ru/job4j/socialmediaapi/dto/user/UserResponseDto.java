package ru.job4j.socialmediaapi.dto.user;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO для ответа с данными пользователя")
public class UserResponseDto {

    @Schema(description = "Уникальный идентификатор пользователя", example = "1")
    private Long id;

    @Schema(description = "Имя пользователя (логин)", example = "johndoe")
    private String username;

    @Schema(description = "Электронная почта пользователя", example = "johndoe@example.com", format = "email")
    private String email;

    @Schema(description = "Дата и время создания аккаунта",
            example = "2025-04-10T10:30:00", type = "string", format = "date-time")
    private LocalDateTime createdAt;
}
