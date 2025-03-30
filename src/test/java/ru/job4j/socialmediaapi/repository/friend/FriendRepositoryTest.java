package ru.job4j.socialmediaapi.repository.friend;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.socialmediaapi.entity.Friend;
import ru.job4j.socialmediaapi.entity.User;
import ru.job4j.socialmediaapi.repository.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class FriendRepositoryTest {

    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        friendRepository.deleteAll();
    }

    /**
     * Позитивный тест сохранения сущности Friend в БД.
     */
    @Test
    public void whenSaveFriendThenFriendIsSavedSuccess() {
        User user1 = new User();
        user1.setUsername("John");
        user1.setEmail("john@example.com");
        user1.setPasswordHash("12345");
        user1.setCreatedAt(LocalDateTime.now());

        User user2 = new User();
        user2.setUsername("Jane");
        user2.setEmail("jane@example.com");
        user2.setPasswordHash("67890");
        user2.setCreatedAt(LocalDateTime.now());

        userRepository.save(user1);
        userRepository.save(user2);

        Friend friend = new Friend();
        friend.setUser(user1);
        friend.setFriend(user2);
        friend.setCreatedAt(LocalDateTime.now());

        friendRepository.save(friend);

        Optional<Friend> optionalSavedFriend = friendRepository.findById(friend.getId());

        assertThat(optionalSavedFriend).isPresent();
        assertThat(optionalSavedFriend.get().getUser().getUsername()).isEqualTo("John");
    }

    /**
     * Негативный тест сохранения сущности Friend в БД.
     */
    @Test
    public void whenSaveFriendThenFriendIsSavedFail() {
        User user1 = new User();
        user1.setUsername("John");
        user1.setEmail("john@example.com");
        user1.setPasswordHash("12345");
        user1.setCreatedAt(LocalDateTime.now());

        User user2 = new User();
        user2.setUsername("Jane");
        user2.setEmail("jane@example.com");
        user2.setPasswordHash("67890");
        user2.setCreatedAt(LocalDateTime.now());

        userRepository.save(user1);
        userRepository.save(user2);

        Friend friend = new Friend();
        friend.setUser(user1);
        friend.setFriend(user2);
        friend.setCreatedAt(LocalDateTime.now());

        friendRepository.save(friend);

        Optional<Friend> optionalSavedFriend = friendRepository.findById(friend.getId());

        assertThat(optionalSavedFriend).isPresent();
        assertNotEquals("Fred", optionalSavedFriend.get().getUser().getUsername(),
                "Username не должен быть 'Fred'");
    }

    /**
     * Позитивный тест получения всех сущностей Friend из БД.
     */
    @Test
    public void whenGetAllFriendsThenFriendsAreFoundSuccess() {
        User user1 = new User();
        user1.setUsername("John");
        user1.setEmail("john@example.com");
        user1.setPasswordHash("12345");
        user1.setCreatedAt(LocalDateTime.now());

        User user2 = new User();
        user2.setUsername("Jane");
        user2.setEmail("jane@example.com");
        user2.setPasswordHash("67890");
        user2.setCreatedAt(LocalDateTime.now());

        userRepository.save(user1);
        userRepository.save(user2);

        Friend friend1 = new Friend();
        friend1.setUser(user1);
        friend1.setFriend(user2);
        friend1.setCreatedAt(LocalDateTime.now());

        Friend friend2 = new Friend();
        friend2.setUser(user2);
        friend2.setFriend(user1);
        friend2.setCreatedAt(LocalDateTime.now());

        friendRepository.save(friend1);
        friendRepository.save(friend2);

        List<Friend> friends = friendRepository.findAll();

        assertThat(friends).hasSize(2);
        assertThat(friends).containsExactlyInAnyOrder(friend1, friend2);
    }

    /**
     * Негативный тест получения всех сущностей Friend из БД.
     */
    @Test
    public void whenGetAllFriendsThenFriendsAreFoundFail() {
        List<Friend> friends = friendRepository.findAll();

        assertTrue(friends.isEmpty(), "Список friends должен быть пустым");
    }

    /**
     * Позитивный тест получения сущности Friend по id из БД.
     */
    @Test
    public void whenGetFriendByIdThenFriendIsFoundSuccess() {
        User user1 = new User();
        user1.setUsername("John");
        user1.setEmail("john@example.com");
        user1.setPasswordHash("12345");
        user1.setCreatedAt(LocalDateTime.now());

        User user2 = new User();
        user2.setUsername("Jane");
        user2.setEmail("jane@example.com");
        user2.setPasswordHash("67890");
        user2.setCreatedAt(LocalDateTime.now());

        userRepository.save(user1);
        userRepository.save(user2);

        Friend friend = new Friend();
        friend.setUser(user1);
        friend.setFriend(user2);
        friend.setCreatedAt(LocalDateTime.now());

        friendRepository.save(friend);

        Optional<Friend> optionalSavedFriend = friendRepository.findById(friend.getId());

        assertThat(optionalSavedFriend).isPresent();
        assertThat(optionalSavedFriend.get().getUser().getUsername()).isEqualTo("John");
    }

    /**
     * Негативный тест получения сущности Friend по id из БД.
     */
    @Test
    public void whenGetFriendByIdThenFriendIsFoundFail() {
        Optional<Friend> friend = friendRepository.findById(-1L);

        assertFalse(friend.isPresent(), "Friend не должен присутствовать в базе данных");
    }

    /**
     * Позитивный тест обновления сущности Friend.
     */
    @Test
    public void whenUpdateFriendThenFriendIsUpdatedSuccess() {
        User user1 = new User();
        user1.setUsername("John");
        user1.setEmail("john@example.com");
        user1.setPasswordHash("12345");
        user1.setCreatedAt(LocalDateTime.now());

        User user2 = new User();
        user2.setUsername("Jane");
        user2.setEmail("jane@example.com");
        user2.setPasswordHash("67890");
        user2.setCreatedAt(LocalDateTime.now());

        userRepository.save(user1);
        userRepository.save(user2);

        Friend friend = new Friend();
        friend.setUser(user1);
        friend.setFriend(user2);
        friend.setCreatedAt(LocalDateTime.now());

        friendRepository.save(friend);

        Optional<Friend> optionalSavedFriend = friendRepository.findById(friend.getId());
        assertTrue(optionalSavedFriend.isPresent(), "Сохранённый Friend не должен быть пустым");

        user2.setUsername("Updated Jane");
        friend.setFriend(user2);

        friendRepository.save(friend);

        Optional<Friend> optionalUpdatedFriend = friendRepository.findById(friend.getId());
        assertTrue(optionalUpdatedFriend.isPresent(), "Обновлённый Friend не должен быть пустым");

        assertEquals("Updated Jane", optionalUpdatedFriend.get().getFriend().getUsername(),
                "Username у Friend должен быть обновлен");

        assertEquals("john@example.com", optionalUpdatedFriend.get().getUser().getEmail(),
                "Email не должен измениться");
    }

    /**
     * Негативный тест обновления сущности Friend.
     */
    @Test
    public void whenUpdateFriendThenFriendIsUpdatedFail() {
        User user1 = new User();
        user1.setUsername("John");
        user1.setEmail("john@example.com");
        user1.setPasswordHash("12345");
        user1.setCreatedAt(LocalDateTime.now());

        User user2 = new User();
        user2.setUsername("Jane");
        user2.setEmail("jane@example.com");
        user2.setPasswordHash("67890");
        user2.setCreatedAt(LocalDateTime.now());

        userRepository.save(user1);
        userRepository.save(user2);

        Friend friend = new Friend();
        friend.setUser(user1);
        friend.setFriend(user2);
        friend.setCreatedAt(LocalDateTime.now());

        friendRepository.save(friend);

        Optional<Friend> optionalSavedFriend = friendRepository.findById(friend.getId());
        assertTrue(optionalSavedFriend.isPresent(), "Сохранённый Friend не должен быть пустым");

        user2.setUsername("Updated Jane");
        friend.setFriend(user2);

        friendRepository.save(friend);

        Optional<Friend> optionalUpdatedFriend = friendRepository.findById(friend.getId());
        assertTrue(optionalUpdatedFriend.isPresent(), "Обновлённый Friend не должен быть пустым");

        assertNotEquals("Test Jane", optionalUpdatedFriend.get().getFriend().getUsername(),
                "Username у Friend должен быть обновлен");
    }

    /**
     * Позитивный тест удаления сущности Friend из БД.
     */
    @Test
    public void whenDeleteFriendThenFriendIsDeletedSuccess() {
        User user1 = new User();
        user1.setUsername("John");
        user1.setEmail("john@example.com");
        user1.setPasswordHash("12345");
        user1.setCreatedAt(LocalDateTime.now());

        User user2 = new User();
        user2.setUsername("Jane");
        user2.setEmail("jane@example.com");
        user2.setPasswordHash("67890");
        user2.setCreatedAt(LocalDateTime.now());

        userRepository.save(user1);
        userRepository.save(user2);

        Friend friend = new Friend();
        friend.setUser(user1);
        friend.setFriend(user2);
        friend.setCreatedAt(LocalDateTime.now());

        friendRepository.save(friend);

        Optional<Friend> optionalSavedFriend = friendRepository.findById(friend.getId());
        assertTrue(optionalSavedFriend.isPresent(), "Сохранённый Friend не должен быть пустым");

        friendRepository.delete(optionalSavedFriend.get());

        Optional<Friend> optionalDeletedFriend = friendRepository.findById(optionalSavedFriend.get().getId());

        assertFalse(optionalDeletedFriend.isPresent(), "Friend не должен присутствовать в базе данных.");
    }

    /**
     * Негативный тест удаления сущности Friend.
     */
    @Test
    public void whenDeleteFriendThenFriendIsDeletedFail() {
        Long nonExistentId = 999L;
        long initialCount = friendRepository.count();

        assertDoesNotThrow(() -> friendRepository.deleteById(nonExistentId));
        assertEquals(initialCount, friendRepository.count());
    }
}