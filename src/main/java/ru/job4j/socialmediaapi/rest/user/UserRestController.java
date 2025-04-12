package ru.job4j.socialmediaapi.rest.user;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import ru.job4j.socialmediaapi.dto.user.UserRequestDto;
import ru.job4j.socialmediaapi.dto.user.UserResponseDto;
import ru.job4j.socialmediaapi.service.user.UserService;

import java.util.List;

@Tag(name = "UserRestController", description = "REST API для управления пользователями")
@RestController
@AllArgsConstructor
@RequestMapping("/api/users")
public class UserRestController {

    private final UserService userService;

    @Operation(
            summary = "Сохранение пользователя",
            description = "Эндпоинт сохраняет пользователя")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Пользователь успешно сохранен"),
            @ApiResponse(responseCode = "400", description = "Ошибка валидиции")
    })
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserResponseDto> saveUser(@Valid
                                                    @RequestBody UserRequestDto userRequestDto) {
        UserResponseDto userResponseDto = userService.saveUser(userRequestDto);
        var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(userResponseDto.getId())
                .toUri();
        return ResponseEntity.ok()
                .location(uri)
                .body(userResponseDto);
    }

    @Operation(
            summary = "Получение списка всех пользователей",
            description = "Эндпоинт возвращает список всех пользователей")
    @ApiResponse(
            responseCode = "200",
            description = "Список пользователей")
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        List<UserResponseDto> users = userService.getAllUsers();
        if (users.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(users);
    }

    @Operation(
            summary = "Получение пользователя по id",
            description = "Эндпоинт возвращает данные пользователя по его id"
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Пользователь найден"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    @GetMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserResponseDto> getUserById(@PathVariable("id")
                                                       @NotNull
                                                       @Min(value = 1, message = "номер ресурса должен быть 1 и более")
                                                       Long id) {
        UserResponseDto userResponseDto = userService.getUserById(id);
        if (userResponseDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userResponseDto);
    }

    @Operation(summary = "Обновить данные пользователя", description = "Обновляет информацию о пользователе по id")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Данные пользователя успешно обновлены"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<UserResponseDto> updateUser(@PathVariable("id")
                                                      @NotNull
                                                      @Min(value = 1, message = "номер ресурса должен быть 1 и более")
                                                      Long id,
                                                      @Valid
                                                      @RequestBody UserRequestDto userRequestDto) {
        UserResponseDto userResponseDto = userService.updateUser(id, userRequestDto);
        if (userResponseDto == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(userResponseDto);
    }

    @Operation(summary = "Удалить пользователя", description = "Удаляет пользователя по id")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Пользователь успешно удален"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('MODERATOR') or hasRole('ADMIN')")
    public ResponseEntity<Void> deleteUserById(@PathVariable("id")
                                               @NotNull
                                               @Min(value = 1, message = "номер ресурса должен быть 1 и более")
                                               Long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}
