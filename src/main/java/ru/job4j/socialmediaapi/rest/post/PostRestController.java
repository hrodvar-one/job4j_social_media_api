package ru.job4j.socialmediaapi.rest.post;

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

@RestController
@AllArgsConstructor
@RequestMapping("/api/posts")
public class PostRestController {

    private final PostService postService;

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

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts() {
        List<PostDto> postDtos = postService.getAllPosts();
        if (postDtos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(postDtos);
    }

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

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePostById(@PathVariable("id")
                                               @NotNull
                                               @Min(value = 1, message = "номер ресурса должен быть 1 и более")
                                               Long id) {
        postService.deletePostById(id);
        return ResponseEntity.noContent().build();
    }

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
