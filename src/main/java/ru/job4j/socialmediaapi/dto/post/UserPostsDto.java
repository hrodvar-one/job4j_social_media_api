package ru.job4j.socialmediaapi.dto.post;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO для представления постов пользователя")
public class UserPostsDto {

    @Schema(description = "ID пользователя", example = "42")
    private Long userId;

    @Schema(description = "Имя пользователя", example = "johndoe")
    private String username;

    @Schema(description = "Список постов пользователя")
    private List<PostDto> posts;
}
