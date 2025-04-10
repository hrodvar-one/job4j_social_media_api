package ru.job4j.socialmediaapi.dto.post;

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
@Schema(description = "DTO для представления поста")
public class PostDto {

    @Schema(description = "Уникальный идентификатор поста", example = "1")
    private Long id;

    @Schema(description = "Заголовок поста", example = "Мой первый пост")
    private String title;

    @Schema(description = "Содержимое поста", example = "Сегодня я начал пользоваться этим API.")
    private String content;

    @Schema(description = "ID пользователя, который создал пост", example = "42")
    private Long userId;

    @Schema(description = "Дата и время создания поста",
            example = "2025-04-10T10:15:30", type = "string", format = "date-time")
    private LocalDateTime createdAt;

    @Schema(description = "Дата и время последнего обновления поста",
            example = "2025-04-10T11:00:00", type = "string", format = "date-time")
    private LocalDateTime updatedAt;
}
