package ru.job4j.socialmediaapi.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.job4j.socialmediaapi.dto.user.UserRequestDto;
import ru.job4j.socialmediaapi.dto.user.UserResponseDto;
import ru.job4j.socialmediaapi.entity.User;
import ru.job4j.socialmediaapi.exeption.ResourceNotFoundException;
import ru.job4j.socialmediaapi.mapper.UserMapper;
import ru.job4j.socialmediaapi.repository.user.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDto saveUser(UserRequestDto userRequestDto) {
        User user = UserMapper.toEntity(userRequestDto);
        user.setPasswordHash(hashPassword(userRequestDto.getPassword()));
        userRepository.save(user);
        return UserMapper.toResponseDto(user);
    }

    public List<UserResponseDto> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(UserMapper::toResponseDto)
                .toList();
    }

    public UserResponseDto getUserById(Long id) {
        return userRepository.findById(id)
                .map(UserMapper::toResponseDto)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден с id: " + id));
    }

    public UserResponseDto updateUser(Long id, UserRequestDto userRequestDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Пользователь не найден с id: " + id));
        user.setUsername(userRequestDto.getUsername());
        user.setEmail(userRequestDto.getEmail());
        user.setPasswordHash(hashPassword(userRequestDto.getPassword()));
        userRepository.save(user);
        return UserMapper.toResponseDto(user);
    }

    public void deleteUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Пользователь не найден с id: " + id));
        userRepository.delete(user);
    }

    /**
     * Временный простой метод для хэширования пароля
     * @param password пароль
     * @return хэшированный пароль
     */
    private static String hashPassword(String password) {
        return "HASHED_" + password;
    }
}
