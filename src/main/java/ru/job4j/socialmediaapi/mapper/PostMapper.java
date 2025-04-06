package ru.job4j.socialmediaapi.mapper;

import ru.job4j.socialmediaapi.dto.post.PostDto;
import ru.job4j.socialmediaapi.dto.post.PostRequestDto;
import ru.job4j.socialmediaapi.entity.Post;
import ru.job4j.socialmediaapi.entity.User;

import java.time.LocalDateTime;

public class PostMapper {

    public static PostDto toDto(Post post) {
        PostDto postDTO = new PostDto();
        postDTO.setId(post.getId());
        postDTO.setTitle(post.getTitle());
        postDTO.setContent(post.getContent());
        postDTO.setUserId(post.getUser().getId());
        postDTO.setCreatedAt(post.getCreatedAt());
        postDTO.setUpdatedAt(post.getUpdatedAt());
        return postDTO;
    }

    public static Post toEntity(PostDto postDTO, User user) {
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        post.setUser(user);
        post.setCreatedAt(postDTO.getCreatedAt());
        post.setUpdatedAt(postDTO.getUpdatedAt());
        return post;
    }

    public static Post toEntity(PostRequestDto postRequestDto, User user) {
        Post post = new Post();
        post.setTitle(postRequestDto.getTitle());
        post.setContent(postRequestDto.getContent());
        post.setUser(user);
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());
        return post;
    }
}
