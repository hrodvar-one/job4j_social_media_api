package ru.job4j.socialmediaapi.dto.post;

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
public class PostRequestDto {

    @NotBlank(message = "title не может быть пустым")
    private String title;

    @NotBlank(message = "content не может быть пустым")
    private String content;

    @NotNull(message = "User ID не должен быть null")
    @Positive(message = "User ID должен быть положительным")
    private Long userId;
}
