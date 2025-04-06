package ru.job4j.socialmediaapi.rest.post;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.job4j.socialmediaapi.dto.post.PostDto;
import ru.job4j.socialmediaapi.dto.post.PostRequestDto;
import ru.job4j.socialmediaapi.service.post.PostService;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/posts")
public class PostRestController {

    private final PostService postService;

    @PostMapping
    public ResponseEntity<PostDto> savePost(@RequestBody PostRequestDto postRequestDto) {
        PostDto postDto = postService.savePost(postRequestDto);
        return ResponseEntity.ok(postDto);
    }

    @GetMapping
    public ResponseEntity<List<PostDto>> getAllPosts() {
        List<PostDto> posDtos = postService.getAllPosts();
        return ResponseEntity.ok(posDtos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") Long id) {
        PostDto postDto = postService.getPostById(id);
        return ResponseEntity.ok(postDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable("id") Long id,
                                              @RequestBody PostRequestDto postRequestDto) {
        PostDto postDto = postService.updatePost(id, postRequestDto);
        return ResponseEntity.ok(postDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePostById(@PathVariable("id") Long id) {
        postService.deletePostById(id);
        return ResponseEntity.noContent().build();
    }
}
