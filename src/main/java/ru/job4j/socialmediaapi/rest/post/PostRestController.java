package ru.job4j.socialmediaapi.rest.post;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
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
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") Long id) {
        PostDto postDto = postService.getPostById(id);
        if (postDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(postDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable("id") Long id,
                                              @RequestBody PostRequestDto postRequestDto) {
        PostDto postDto = postService.updatePost(id, postRequestDto);
        if (postDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(postDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePostById(@PathVariable("id") Long id) {
        postService.deletePostById(id);
        return ResponseEntity.noContent().build();
    }
}
