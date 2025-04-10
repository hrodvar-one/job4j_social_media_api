package ru.job4j.socialmediaapi.rest.post;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.job4j.socialmediaapi.dto.post.PostDto;
import ru.job4j.socialmediaapi.dto.post.PostRequestDto;
import ru.job4j.socialmediaapi.dto.post.UserPostsDto;
import ru.job4j.socialmediaapi.service.post.PostService;

import java.util.List;

@Tag(name = "PostRestController", description = "REST API для управления постами")
@RestController
@AllArgsConstructor
@RequestMapping("/api/posts")
public class PostRestController {

    private final PostService postService;

    @Operation(summary = "Создать новый пост", description = "Создает новый пост и возвращает его данные")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Пост успешно создан"),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации")
    })
    @PostMapping
    public ResponseEntity<PostDto> savePost(@Valid
                                            @RequestBody PostRequestDto postRequestDto) {
        PostDto postDto = postService.savePost(postRequestDto);
        var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(postDto.getId())
                .toUri();
        return ResponseEntity.ok()
                .location(uri)
                .body(postDto);
    }

    @Operation(
            summary = "Получить все посты",
            description = "Возвращает список всех постов")
    @ApiResponse(
            responseCode = "200",
            description = "Список постов успешно получен")
    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts() {
        List<PostDto> postDtos = postService.getAllPosts();
        if (postDtos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(postDtos);
    }

    @Operation(summary = "Получить пост по id", description = "Возвращает данные публикации по её id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Пост найден"),
            @ApiResponse(responseCode = "404", description = "Пост не найден")
    })
    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id")
                                               @NotNull
                                               @Min(value = 1, message = "номер ресурса должен быть 1 и более")
                                               Long id) {
        PostDto postDto = postService.getPostById(id);
        if (postDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(postDto);
    }

    @Operation(summary = "Обновить пост", description = "Обновляет данные поста по его id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Данные поста успешно обновлены"),
            @ApiResponse(responseCode = "404", description = "Пост не найден")
    })
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable("id")
                                              @NotNull
                                              @Min(value = 1, message = "номер ресурса должен быть 1 и более")
                                              Long id,
                                              @Valid
                                              @RequestBody PostRequestDto postRequestDto) {
        PostDto postDto = postService.updatePost(id, postRequestDto);
        if (postDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(postDto);
    }

    @Operation(summary = "Удалить пост", description = "Удаляет пост по его id")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Пост успешно удален"),
            @ApiResponse(responseCode = "404", description = "Пост не найден")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePostById(@PathVariable("id")
                                               @NotNull
                                               @Min(value = 1, message = "номер ресурса должен быть 1 и более")
                                               Long id) {
        postService.deletePostById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Получить посты по ID пользователей",
            description = "Возвращает список постов для заданных ID пользователей")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Список постов успешно получен"),
            @ApiResponse(responseCode = "400", description = "Ошибка валидации входных данных")
    })
    @PostMapping("/by-users")
    public ResponseEntity<List<UserPostsDto>> getPostsByUserIds(@Valid
                                                                @RequestBody List<Long> userIds) {
        List<UserPostsDto> userPosts = postService.getPostsByUserIds(userIds);
        if (userPosts.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userPosts);
    }
}
