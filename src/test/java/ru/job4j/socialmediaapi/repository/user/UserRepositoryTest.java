package ru.job4j.socialmediaapi.repository.user;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.socialmediaapi.entity.User;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
    }

    /**
     * Позитивный тест сохранения пользователя.
     */
    @Test
    public void whenSaveUser_ThenUserIsSavedSuccess() {
        User user = new User();
        user.setUsername("John");
        user.setEmail("john@example.com");
        user.setPasswordHash("password");
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);
        Optional<User> optionalSavedUser = userRepository.findById(user.getId());
        assertThat(optionalSavedUser).isPresent();
        assertThat(optionalSavedUser.get().getUsername()).isEqualTo("John");
    }

    /**
     * Негативный тест сохранения пользователя.
     */
    @Test
    public void whenSaveUser_ThenUserIsSavedFail() {
        User user = new User();
        user.setUsername("John");
        user.setEmail("john@example.com");
        user.setPasswordHash("password");
        user.setCreatedAt(LocalDateTime.now());
        userRepository.save(user);
        Optional<User> optionalSavedUser = userRepository.findById(user.getId());
        assertThat(optionalSavedUser).isPresent();
        assertNotEquals("Smith", optionalSavedUser.get().getUsername(), "Username не должен быть 'Smith'");
    }

    /**
     * Позитивный тест вывода всех объектов типа User.
     */
    @Test
    public void whenGetAllUsers_ThenUsersAreFoundSuccess() {
        User user1 = new User();
        user1.setUsername("John");
        user1.setEmail("john@example.com");
        user1.setPasswordHash("password");
        user1.setCreatedAt(LocalDateTime.now());

        User user2 = new User();
        user2.setUsername("Smith");
        user2.setEmail("smith@example.com");
        user2.setPasswordHash("password2");
        user2.setCreatedAt(LocalDateTime.now());

        userRepository.save(user1);
        userRepository.save(user2);

        List<User> users = userRepository.findAll();
        assertThat(users).hasSize(2);
        assertThat(users).containsExactlyInAnyOrder(user1, user2);
    }

    /**
     * Негативный тест вывода всех объектов типа User.
     */
    @Test
    public void whenGetAllUsers_ThenUsersAreFoundFail() {
        List<User> users = userRepository.findAll();

        assertTrue(users.isEmpty(), "Список пользователей должен быть пустым");
    }

    /**
     * Позитивный тест поиска User по ID.
     */
    @Test
    public void whenGetUserById_ThenUserIsFoundSuccess() {
        User user = new User();
        user.setUsername("John");
        user.setEmail("john@example.com");
        user.setPasswordHash("password");
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);

        Optional<User> savedUser = userRepository.findById(user.getId());
        assertTrue(savedUser.isPresent(), "Сохранённый User не должен быть пустым");
        assertEquals(user.getUsername(), savedUser.get().getUsername(), "ID пользователя должен совпадать");
    }

    /**
     * Негативный тест поиска User по ID.
     */
    @Test
    public void whenGetUserById_ThenUserIsFoundFail() {
        Optional<User> optionalUser = userRepository.findById(-1L);

        assertFalse(optionalUser.isPresent(), "Пользователь не должен присутствовать в базе данных.");
    }

    /**
     * Позитивный тест обновления сущности User.
     */
    @Test
    public void whenUpdateUser_ThenUserIsUpdatedSuccess() {
        User user = new User();
        user.setUsername("John");
        user.setEmail("john@example.com");
        user.setPasswordHash("password");
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);

        Optional<User> optionalSavedUser = userRepository.findById(user.getId());
        assertTrue(optionalSavedUser.isPresent(), "Сохранённый User не должен быть пустым");

        user.setUsername("Jane");
        userRepository.save(user);

        Optional<User> optionalUpdatedUser = userRepository.findById(user.getId());
        assertTrue(optionalUpdatedUser.isPresent(), "Обновлённый User не должен быть пустым");

        assertEquals("Jane", optionalUpdatedUser.get().getUsername(), "Имя пользователя должно быть обновлено");
        assertEquals("john@example.com", optionalUpdatedUser.get().getEmail(), "Email не должен измениться");
    }

    /**
     * Негативный тест обновления объекта типа User.
     */
    @Test
    public void whenUpdateUser_ThenUserIsUpdatedFail() {
        User user = new User();
        user.setUsername("John");
        user.setEmail("john@example.com");
        user.setPasswordHash("password");
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);

        Optional<User> optionalSavedUser = userRepository.findById(user.getId());
        assertTrue(optionalSavedUser.isPresent(), "Сохранённый User не должен быть пустым");

        user.setUsername("Jane");
        userRepository.save(user);

        Optional<User> optionalUpdatedUser = userRepository.findById(user.getId());
        assertTrue(optionalUpdatedUser.isPresent(), "Обновлённый User не должен быть пустым");

        assertNotEquals("John", optionalUpdatedUser.get().getUsername(), "Имя пользователя должно быть обновлено");
    }

    /**
     * Позитивный тест удаления сущности User.
     */
    @Test
    public void whenDeleteUser_ThenUserIsDeletedSuccess() {
        User user = new User();
        user.setUsername("John");
        user.setEmail("john@example.com");
        user.setPasswordHash("password");
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);

        Optional<User> optionalSavedUser = userRepository.findById(user.getId());
        assertTrue(optionalSavedUser.isPresent(), "Сохранённый User не должен быть пустым");

        userRepository.delete(optionalSavedUser.get());

        Optional<User> optionalDeletedUser = userRepository.findById(optionalSavedUser.get().getId());

        assertFalse(optionalDeletedUser.isPresent(), "Пользователь не должен присутствовать в базе данных.");
    }

    /**
     * Негативный тест удаления объекта типа User.
     */
    @Test
    public void whenDeleteUser_ThenUserIsDeletedFail() {
        Long nonExistentId = 999L;
        long initialCount = userRepository.count();

        assertDoesNotThrow(() -> userRepository.deleteById(nonExistentId));
        assertEquals(initialCount, userRepository.count());
    }
}