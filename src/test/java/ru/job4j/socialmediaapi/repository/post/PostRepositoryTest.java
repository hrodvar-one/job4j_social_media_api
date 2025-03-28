package ru.job4j.socialmediaapi.repository.post;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.socialmediaapi.entity.Post;
import ru.job4j.socialmediaapi.entity.User;
import ru.job4j.socialmediaapi.repository.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class PostRepositoryTest {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        postRepository.deleteAll();
    }

    /**
     * Позитивный тест сохранения сущности Post в БД.
     */
    @Test
    public void whenSavePost_ThenPostIsSavedSuccess() {
        User user = new User();
        user.setUsername("John");
        user.setEmail("john@example.com");
        user.setPasswordHash("12345");
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);

        Optional<User> optionalSavedUser = userRepository.findById(user.getId());

        assertThat(optionalSavedUser).isPresent();

        Post post = new Post();
        post.setTitle("Test Post");
        post.setContent("This is a test post.");
        post.setUser(optionalSavedUser.get());
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());

        postRepository.save(post);

        Optional<Post> optionalSavedPost = postRepository.findById(post.getId());

        assertThat(optionalSavedPost).isPresent();
        assertThat(optionalSavedPost.get().getTitle()).isEqualTo("Test Post");
    }

    /**
     * Негативный тест сохранения сущности Post в БД.
     */
    @Test
    public void whenSavePost_ThenPostIsSavedFail() {
        User user = new User();
        user.setUsername("John");
        user.setEmail("john@example.com");
        user.setPasswordHash("12345");
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);

        Optional<User> optionalSavedUser = userRepository.findById(user.getId());

        assertThat(optionalSavedUser).isPresent();

        Post post = new Post();
        post.setTitle("Test Post");
        post.setContent("This is a test post.");
        post.setUser(optionalSavedUser.get());
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());

        postRepository.save(post);

        Optional<Post> optionalSavedPost = postRepository.findById(post.getId());

        assertThat(optionalSavedPost).isPresent();
        assertNotEquals("Test Post2", optionalSavedPost.get().getTitle(), "Title should not be 'Test Post2'");
    }

    /**
     * Позитивный тест получения всех сущностей Post из БД.
     */
    @Test
    public void whenGetAllPosts_ThenPostsAreFoundSuccess() {
        User user = new User();
        user.setUsername("John");
        user.setEmail("john@example.com");
        user.setPasswordHash("12345");
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);

        Post post1 = new Post();
        post1.setTitle("Test Post1");
        post1.setContent("This is a test post1.");
        post1.setUser(user);
        post1.setCreatedAt(LocalDateTime.now());
        post1.setUpdatedAt(LocalDateTime.now());

        Post post2 = new Post();
        post2.setTitle("Test Post2");
        post2.setContent("This is a test post2.");
        post2.setUser(user);
        post2.setCreatedAt(LocalDateTime.now());
        post2.setUpdatedAt(LocalDateTime.now());

        postRepository.save(post1);
        postRepository.save(post2);

        List<Post> posts = postRepository.findAll();

        assertThat(posts).hasSize(2);
        assertThat(posts).containsExactlyInAnyOrder(post1, post2);
    }

    /**
     * Негативный тест получения всех сущностей Post из БД.
     */
    @Test
    public void whenGetAllPosts_ThenPostsAreFoundFail() {
        List<Post> posts = postRepository.findAll();

        assertTrue(posts.isEmpty(), "Список пользователей должен быть пустым");
    }

    /**
     * Позитивный тест получения сущности Post по ID.
     */
    @Test
    public void whenGetPostById_ThenPostIsFoundSuccess() {
        User user = new User();
        user.setUsername("John");
        user.setEmail("john@example.com");
        user.setPasswordHash("12345");
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);

        Optional<User> optionalSavedUser = userRepository.findById(user.getId());

        assertThat(optionalSavedUser).isPresent();

        Post post = new Post();
        post.setTitle("Test Post");
        post.setContent("This is a test post.");
        post.setUser(optionalSavedUser.get());
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());

        postRepository.save(post);

        Optional<Post> optionalSavedPost = postRepository.findById(post.getId());
        assertTrue(optionalSavedPost.isPresent(), "Сохранённый Post не должен быть пустым");
        assertEquals(post.getTitle(), optionalSavedPost.get().getTitle(), "Title должен совпадать");
    }

    /**
     * Негативный тест получения сущности Post по ID.
     */
    @Test
    public void whenGetPostById_ThenPostIsFoundFail() {
        Optional<Post> post = postRepository.findById(-1L);

        assertFalse(post.isPresent(), "Post не должен присутствовать в базе данных");
    }

    /**
     * Позитивный тест обновления сущности Post.
     */
    @Test
    public void whenUpdatePost_ThenPostIsUpdatedSuccess() {
        User user = new User();
        user.setUsername("John");
        user.setEmail("john@example.com");
        user.setPasswordHash("12345");
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);

        Optional<User> optionalSavedUser = userRepository.findById(user.getId());

        assertThat(optionalSavedUser).isPresent();

        Post post = new Post();
        post.setTitle("Test Post");
        post.setContent("This is a test post.");
        post.setUser(optionalSavedUser.get());
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());

        postRepository.save(post);

        Optional<Post> optionalSavedPost = postRepository.findById(post.getId());
        assertTrue(optionalSavedPost.isPresent(), "Сохранённый Post не должен быть пустым");

        post.setTitle("Updated Post");
        postRepository.save(post);

        Optional<Post> updatedPost = postRepository.findById(post.getId());
        assertTrue(updatedPost.isPresent(), "Обновлённый Post не должен быть пустым");

        assertEquals("Updated Post", updatedPost.get().getTitle(), "Title у Post должен быть обновлен");
        assertEquals("john@example.com", updatedPost.get().getUser().getEmail(), "Email не должен измениться");
    }

    /**
     * Негативный тест обновления сущности Post.
     */
    @Test
    public void whenUpdatePost_ThenPostIsUpdatedFail() {
        User user = new User();
        user.setUsername("John");
        user.setEmail("john@example.com");
        user.setPasswordHash("12345");
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);

        Optional<User> optionalSavedUser = userRepository.findById(user.getId());

        assertThat(optionalSavedUser).isPresent();

        Post post = new Post();
        post.setTitle("Test Post");
        post.setContent("This is a test post.");
        post.setUser(optionalSavedUser.get());
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());

        postRepository.save(post);

        Optional<Post> optionalSavedPost = postRepository.findById(post.getId());
        assertTrue(optionalSavedPost.isPresent(), "Сохранённый Post не должен быть пустым");

        post.setTitle("Updated Post");
        postRepository.save(post);

        Optional<Post> updatedPost = postRepository.findById(post.getId());
        assertTrue(updatedPost.isPresent(), "Обновлённый Post не должен быть пустым");

        assertNotEquals("Test Post", updatedPost.get().getTitle(), "Title у Post должен быть обновлен");
    }

    /**
     * Позитивный тест удаления сущности Post.
     */
    @Test
    public void whenDeletePost_ThenPostIsDeletedSuccess() {
        User user = new User();
        user.setUsername("John");
        user.setEmail("john@example.com");
        user.setPasswordHash("12345");
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);

        Optional<User> optionalSavedUser = userRepository.findById(user.getId());

        assertThat(optionalSavedUser).isPresent();

        Post post = new Post();
        post.setTitle("Test Post");
        post.setContent("This is a test post.");
        post.setUser(optionalSavedUser.get());
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());

        postRepository.save(post);

        Optional<Post> optionalSavedPost = postRepository.findById(post.getId());
        assertTrue(optionalSavedPost.isPresent(), "Сохранённый Post не должен быть пустым");

        postRepository.delete(optionalSavedPost.get());

        Optional<Post> deletedPost = postRepository.findById(optionalSavedPost.get().getId());

        assertFalse(deletedPost.isPresent(), "Post не должен присутствовать в базе данных.");
    }

    /**
     * Негативный тест удаления сущности Post.
     */
    @Test
    public void whenDeletePost_ThenPostIsDeletedFail() {
        Long nonExistentId = 999L;
        long initialCount = postRepository.count();

        assertDoesNotThrow(() -> postRepository.deleteById(nonExistentId));
        assertEquals(initialCount, postRepository.count());
    }
}