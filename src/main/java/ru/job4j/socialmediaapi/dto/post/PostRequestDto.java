package ru.job4j.socialmediaapi.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO для создания нового поста")
public class PostRequestDto {

    @NotBlank(message = "title не может быть пустым")
    @Schema(description = "Заголовок поста", example = "Новая статья о Java")
    private String title;

    @NotBlank(message = "content не может быть пустым")
    @Schema(description = "Содержимое поста", example = "Сегодня я узнал про аннотацию @Schema...")
    private String content;

    @NotNull(message = "User ID не должен быть null")
    @Positive(message = "User ID должен быть положительным")
    @Schema(description = "ID пользователя, создающего пост", example = "42")
    private Long userId;
}
