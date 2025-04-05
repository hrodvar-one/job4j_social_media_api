package ru.job4j.socialmediaapi.mapper;

import ru.job4j.socialmediaapi.dto.PostDto;
import ru.job4j.socialmediaapi.entity.Post;
import ru.job4j.socialmediaapi.entity.User;

public class PostMapper {

    public static PostDto toDto(Post post) {
        PostDto postDTO = new PostDto();
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
}
