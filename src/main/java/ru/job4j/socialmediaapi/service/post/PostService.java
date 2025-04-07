package ru.job4j.socialmediaapi.service.post;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.socialmediaapi.dto.post.PostDto;
import ru.job4j.socialmediaapi.dto.post.PostRequestDto;
import ru.job4j.socialmediaapi.entity.Post;
import ru.job4j.socialmediaapi.entity.User;
import ru.job4j.socialmediaapi.exeption.ResourceNotFoundException;
import ru.job4j.socialmediaapi.mapper.PostMapper;
import ru.job4j.socialmediaapi.repository.post.PostRepository;
import ru.job4j.socialmediaapi.repository.user.UserRepository;
import ru.job4j.socialmediaapi.service.image.ImageService;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PostService {

    private final PostRepository postRepository;
    private final UserRepository userRepository;
    private final ImageService imageService;

    public PostDto savePost(PostRequestDto postRequestDto) {
        User user = userRepository.findById(postRequestDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден с id: " + postRequestDto.getUserId()));

        Post post = PostMapper.toEntity(postRequestDto, user);
        Post savedPost = postRepository.save(post);
        return PostMapper.toDto(savedPost);
    }

    public List<PostDto> getAllPosts() {
        return postRepository.findAll()
                .stream()
                .map(PostMapper::toDto)
                .toList();
    }

    public PostDto getPostById(Long id) {
        return postRepository.findById(id)
                .map(PostMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Пост не найден с id: " + id));
    }

    public PostDto updatePost(Long id, PostRequestDto postRequestDto) {
        User user = userRepository.findById(postRequestDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Пользователь не найден с id: " + postRequestDto.getUserId()));

        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Пост не найден с id: " + id));

        post.setTitle(postRequestDto.getTitle());
        post.setContent(postRequestDto.getContent());
        post.setUser(user);
        post.setUpdatedAt(LocalDateTime.now());
        Post updatedPost = postRepository.save(post);

        return PostMapper.toDto(updatedPost);
    }

    public void deletePostById(Long id) {
        Post post = postRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Пост не найден с id: " + id));
        postRepository.delete(post);
    }

    @Transactional
    public PostDto savePostWithImage(PostDto postDto, MultipartFile imageFile) {

        imageService.saveImage(imageFile);

        User user = userRepository.findById(postDto.getUserId())
                .orElseThrow(() -> new RuntimeException("User не найден"));

        Post post = PostMapper.toEntity(postDto, user);

        return PostMapper.toDto(postRepository.save(post));
    }

    @Transactional
    public PostDto updateOwnPost(PostDto postDto) {

        Post existingPost = validateUserOwnsPost(postDto.getId(), postDto.getUserId());

        existingPost.setTitle(postDto.getTitle());
        existingPost.setContent(postDto.getContent());
        existingPost.setUpdatedAt(LocalDateTime.now());

        return PostMapper.toDto(postRepository.save(existingPost));
    }

    @Transactional
    public void deleteOwnPost(PostDto postDto) {

        Post existingPost = validateUserOwnsPost(postDto.getId(), postDto.getUserId());

        postRepository.delete(existingPost);
    }

    private Post validateUserOwnsPost(Long postId, Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User не найден"));

        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post не найден"));

        if (!post.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("User не владелец данного поста");
        }

        return post;
    }
}
