package ru.job4j.socialmediaapi.dto.post;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserPostsDto {

    private Long userId;
    private String username;
    private List<PostDto> posts;
}
