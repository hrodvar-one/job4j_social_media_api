package ru.job4j.socialmediaapi.repository.post;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
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
@ActiveProfiles("test")
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
    public void whenSavePostThenPostIsSavedSuccess() {
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
    public void whenSavePostThenPostIsSavedFail() {
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
    public void whenGetAllPostsThenPostsAreFoundSuccess() {
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
    public void whenGetAllPostsThenPostsAreFoundFail() {
        List<Post> posts = postRepository.findAll();

        assertTrue(posts.isEmpty(), "Список пользователей должен быть пустым");
    }

    /**
     * Позитивный тест получения сущности Post по ID.
     */
    @Test
    public void whenGetPostByIdThenPostIsFoundSuccess() {
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
    public void whenGetPostByIdThenPostIsFoundFail() {
        Optional<Post> post = postRepository.findById(-1L);

        assertFalse(post.isPresent(), "Post не должен присутствовать в базе данных");
    }

    /**
     * Позитивный тест обновления сущности Post.
     */
    @Test
    public void whenUpdatePostThenPostIsUpdatedSuccess() {
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
    public void whenUpdatePostThenPostIsUpdatedFail() {
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
    public void whenDeletePostThenPostIsDeletedSuccess() {
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

        Optional<Post> optionalDeletedPost = postRepository.findById(optionalSavedPost.get().getId());

        assertFalse(optionalDeletedPost.isPresent(), "Post не должен присутствовать в базе данных.");
    }

    /**
     * Негативный тест удаления сущности Post.
     */
    @Test
    public void whenDeletePostThenPostIsDeletedFail() {
        Long nonExistentId = 999L;
        long initialCount = postRepository.count();

        assertDoesNotThrow(() -> postRepository.deleteById(nonExistentId));
        assertEquals(initialCount, postRepository.count());
    }

    /**
     * Позитивный тест получения всех сущностей Post по пользователю.
     */
    @Test
    public void whenGetPostsByUserIdThenPostsAreFoundSuccess() {
        User user = new User();
        user.setUsername("John");
        user.setEmail("john@example.com");
        user.setPasswordHash("12345");
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);

        Optional<User> optionalSavedUser = userRepository.findById(user.getId());

        assertThat(optionalSavedUser).isPresent();

        Post post1 = new Post();
        post1.setTitle("Post 1");
        post1.setContent("This is post 1.");
        post1.setUser(optionalSavedUser.get());
        post1.setCreatedAt(LocalDateTime.now());
        post1.setUpdatedAt(LocalDateTime.now());

        Post post2 = new Post();
        post2.setTitle("Post 2");
        post2.setContent("This is post 2.");
        post2.setUser(optionalSavedUser.get());
        post2.setCreatedAt(LocalDateTime.now());
        post2.setUpdatedAt(LocalDateTime.now());

        postRepository.save(post1);
        postRepository.save(post2);

        List<Post> posts = postRepository.findByUserId(user.getId());

        assertThat(posts).hasSize(2);
        assertThat(posts).containsExactlyInAnyOrder(post1, post2);
    }

    /**
     * Негативный тест получения всех сущностей Post по пользователю.
     */
    @Test
    public void whenGetPostsByUserIdThenPostsAreFoundFail() {
        List<Post> posts = postRepository.findByUserId(-1L);

        assertTrue(posts.isEmpty(), "Список пользователей должен быть пустым");
    }

    /**
     * Позитивный тест получения всех сущностей Post в диапазоне дат.
     */
    @Test
    public void whenGetPostsByCreatedAtBetweenThenPostsAreFoundSuccess() {
        User user = new User();
        user.setUsername("John");
        user.setEmail("john@example.com");
        user.setPasswordHash("12345");
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);

        Optional<User> optionalSavedUser = userRepository.findById(user.getId());

        assertThat(optionalSavedUser).isPresent();

        Post post1 = new Post();
        post1.setTitle("Post 1");
        post1.setContent("This is post 1.");
        post1.setUser(optionalSavedUser.get());
        post1.setCreatedAt(LocalDateTime.parse("2025-03-20T00:00:00"));
        post1.setUpdatedAt(LocalDateTime.parse("2025-03-21T00:00:00"));

        Post post2 = new Post();
        post2.setTitle("Post 2");
        post2.setContent("This is post 2.");
        post2.setUser(optionalSavedUser.get());
        post2.setCreatedAt(LocalDateTime.parse("2025-03-25T00:00:00"));
        post2.setUpdatedAt(LocalDateTime.parse("2025-03-26T00:00:00"));

        postRepository.save(post1);
        postRepository.save(post2);

        List<Post> posts = postRepository.findByCreatedAtBetween(
                LocalDateTime.parse("2025-03-19T00:00:00"),
                LocalDateTime.parse("2025-03-27T23:59:59"));

        assertThat(posts).hasSize(2);
        assertThat(posts).containsExactlyInAnyOrder(post1, post2);
    }

    /**
     * Негативный тест получения всех сущностей Post в диапазоне дат.
     */
    @Test
    public void whenGetPostsByCreatedAtBetweenThenPostsAreFoundFail() {
        List<Post> posts = postRepository.findByCreatedAtBetween(
                LocalDateTime.parse("2025-03-19T00:00:00"),
                LocalDateTime.parse("2025-03-27T23:59:59"));

        assertTrue(posts.isEmpty(), "Список пользователей должен быть пустым");
    }

    /**
     * Позитивный тест получения всех сущностей Post отсортированных по дате с пагинацией.
     */
    @Test
    public void whenGetPostsByCreatedAtOrderByCreatedAtDescPagedThenPostsAreFoundSuccess() {
        User user1 = new User();
        user1.setUsername("John");
        user1.setEmail("john@example.com");
        user1.setPasswordHash("12345");
        user1.setCreatedAt(LocalDateTime.now());

        User user2 = new User();
        user2.setUsername("Jane");
        user2.setEmail("jane@example.com");
        user2.setPasswordHash("54321");
        user2.setCreatedAt(LocalDateTime.now());

        userRepository.save(user1);
        userRepository.save(user2);

        Optional<User> optionalSavedUser1 = userRepository.findById(user1.getId());
        Optional<User> optionalSavedUser2 = userRepository.findById(user2.getId());

        assertThat(optionalSavedUser1).isPresent();
        assertThat(optionalSavedUser2).isPresent();

        Post post1 = new Post();
        post1.setTitle("Post 1");
        post1.setContent("This is post 1.");
        post1.setUser(optionalSavedUser1.get());
        post1.setCreatedAt(LocalDateTime.parse("2025-03-20T00:00:00"));
        post1.setUpdatedAt(LocalDateTime.parse("2025-03-21T00:00:00"));

        Post post2 = new Post();
        post2.setTitle("Post 2");
        post2.setContent("This is post 2.");
        post2.setUser(optionalSavedUser2.get());
        post2.setCreatedAt(LocalDateTime.parse("2025-03-25T00:00:00"));
        post2.setUpdatedAt(LocalDateTime.parse("2025-03-26T00:00:00"));

        postRepository.save(post1);
        postRepository.save(post2);

        Page<Post> pagedPosts = postRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(0, 1));

        assertThat(pagedPosts).hasSize(1);
    }

    /**
     * Негативный тест получения всех сущностей Post отсортированных по дате с пагинацией.
     */
    @Test
    public void whenGetPostsByCreatedAtOrderByCreatedAtDescPagedThenPostsAreFoundFail() {
        Page<Post> pagedPosts = postRepository.findAllByOrderByCreatedAtDesc(PageRequest.of(0, 1));

        assertTrue(pagedPosts.isEmpty(), "Страница с постами должна быть пустой");
    }
}