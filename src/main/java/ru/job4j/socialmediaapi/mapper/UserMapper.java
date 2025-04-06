package ru.job4j.socialmediaapi.mapper;

import ru.job4j.socialmediaapi.dto.user.UserRequestDto;
import ru.job4j.socialmediaapi.dto.user.UserResponseDto;
import ru.job4j.socialmediaapi.entity.User;

import java.time.LocalDateTime;

public class UserMapper {

    public static UserResponseDto toResponseDto(User user) {
        UserResponseDto userResponseDto = new UserResponseDto();
        userResponseDto.setId(user.getId());
        userResponseDto.setUsername(user.getUsername());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setCreatedAt(user.getCreatedAt());
        return userResponseDto;
    }

    public static User toEntity(UserRequestDto userRequestDto) {
        User user = new User();
        user.setUsername(userRequestDto.getUsername());
        user.setEmail(userRequestDto.getEmail());
        user.setCreatedAt(LocalDateTime.now());
        return user;
    }
}
