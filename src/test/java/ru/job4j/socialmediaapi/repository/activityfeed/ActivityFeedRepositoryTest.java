package ru.job4j.socialmediaapi.repository.activityfeed;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.socialmediaapi.entity.ActivityFeed;
import ru.job4j.socialmediaapi.entity.Post;
import ru.job4j.socialmediaapi.entity.User;
import ru.job4j.socialmediaapi.repository.post.PostRepository;
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
class ActivityFeedRepositoryTest {

    @Autowired
    private ActivityFeedRepository activityFeedRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        postRepository.deleteAll();
        activityFeedRepository.deleteAll();
    }

    /**
     * Позитивный тест сохранения сущности ActivityFeed в БД.
     */
    @Test
    void whenSaveActivityFeed_ThenActivityFeedIsSavedSuccess() {
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

        ActivityFeed activityFeed = new ActivityFeed();
        activityFeed.setSubscriber(optionalSavedUser.get());
        activityFeed.setPost(optionalSavedPost.get());
        activityFeed.setCreatedAt(LocalDateTime.now());

        activityFeedRepository.save(activityFeed);

        Optional<ActivityFeed> optionalSavedActivityFeed = activityFeedRepository.findById(activityFeed.getId());
        assertTrue(optionalSavedActivityFeed.isPresent());
        assertThat(optionalSavedActivityFeed.get().getSubscriber()).isEqualTo(optionalSavedUser.get());
    }

    /**
     * Негативный тест сохранения сущности ActivityFeed в БД.
     */
    @Test
    void whenSaveActivityFeed_ThenActivityFeedIsSavedFail() {
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

        ActivityFeed activityFeed = new ActivityFeed();
        activityFeed.setSubscriber(optionalSavedUser.get());
        activityFeed.setPost(optionalSavedPost.get());
        activityFeed.setCreatedAt(LocalDateTime.now());

        activityFeedRepository.save(activityFeed);

        Optional<ActivityFeed> optionalSavedActivityFeed = activityFeedRepository.findById(activityFeed.getId());

        assertThat(optionalSavedActivityFeed).isPresent();
        assertNotEquals("Test Post 2", optionalSavedActivityFeed.get().getPost().getTitle(),
                "Title не должен быть Test Post 2");
    }

    /**
     * Позитивный тест получения всех сущностей ActivityFeed из БД.
     */
    @Test
    void whenGetAllActivityFeeds_ThenActivityFeedsAreFoundSuccess() {
        User user = new User();
        user.setUsername("John");
        user.setEmail("john@example.com");
        user.setPasswordHash("12345");
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);

        Optional<User> optionalSavedUser = userRepository.findById(user.getId());

        assertThat(optionalSavedUser).isPresent();

        Post post1 = new Post();
        post1.setTitle("Test Post 1");
        post1.setContent("This is a test post 1.");
        post1.setUser(optionalSavedUser.get());
        post1.setCreatedAt(LocalDateTime.now());
        post1.setUpdatedAt(LocalDateTime.now());

        Post post2 = new Post();
        post2.setTitle("Test Post 2");
        post2.setContent("This is a test post 2.");
        post2.setUser(optionalSavedUser.get());
        post2.setCreatedAt(LocalDateTime.now());
        post2.setUpdatedAt(LocalDateTime.now());

        postRepository.save(post1);
        postRepository.save(post2);

        Optional<Post> optionalSavedPost1 = postRepository.findById(post1.getId());
        Optional<Post> optionalSavedPost2 = postRepository.findById(post2.getId());

        assertThat(optionalSavedPost1).isPresent();
        assertThat(optionalSavedPost2).isPresent();

        ActivityFeed activityFeed1 = new ActivityFeed();
        activityFeed1.setSubscriber(optionalSavedUser.get());
        activityFeed1.setPost(optionalSavedPost1.get());
        activityFeed1.setCreatedAt(LocalDateTime.now());

        ActivityFeed activityFeed2 = new ActivityFeed();
        activityFeed2.setSubscriber(optionalSavedUser.get());
        activityFeed2.setPost(optionalSavedPost2.get());
        activityFeed2.setCreatedAt(LocalDateTime.now());

        activityFeedRepository.save(activityFeed1);
        activityFeedRepository.save(activityFeed2);

        List<ActivityFeed> foundActivityFeeds = activityFeedRepository.findAll();

        assertThat(foundActivityFeeds).hasSize(2);
        assertThat(foundActivityFeeds).containsExactlyInAnyOrder(activityFeed1, activityFeed2);
    }

    /**
     * Негативный тест получения всех сущностей ActivityFeed из БД.
     */
    @Test
    void whenGetAllActivityFeeds_ThenActivityFeedsAreFoundFail() {
        List<ActivityFeed> foundActivityFeeds = activityFeedRepository.findAll();

        assertTrue(foundActivityFeeds.isEmpty(), "Список ActivityFeeds должен быть пустым");
    }

    /**
     * Позитивный тест получения сущности ActivityFeed из БД по ID.
     */
    @Test
    void whenGetActivityFeedById_ThenActivityFeedIsFoundSuccess() {
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

        ActivityFeed activityFeed = new ActivityFeed();
        activityFeed.setSubscriber(optionalSavedUser.get());
        activityFeed.setPost(optionalSavedPost.get());
        activityFeed.setCreatedAt(LocalDateTime.now());

        activityFeedRepository.save(activityFeed);

        Optional<ActivityFeed> optionalSavedActivityFeed = activityFeedRepository.findById(activityFeed.getId());

        assertTrue(optionalSavedActivityFeed.isPresent(), "Сохраненный ActivityFeed не должен быть пустым");
        assertEquals(activityFeed.getSubscriber(), optionalSavedActivityFeed.get().getSubscriber(),
                "Subscriber должны совпадать");
    }

    /**
     * Негативный тест получения сущности ActivityFeed из БД по ID.
     */
    @Test
    void whenGetActivityFeedById_ThenActivityFeedIsFoundFail() {
        Optional<ActivityFeed> optionalActivityFeed = activityFeedRepository.findById(-1L);

        assertFalse(optionalActivityFeed.isPresent(), "ActivityFeed не должен присутствовать в базе данных");
    }

    /**
     * Позитивный тест обновления сущности ActivityFeed.
     */
    @Test
    void whenUpdateActivityFeed_ThenActivityFeedIsUpdatedSuccess() {
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

        ActivityFeed activityFeed = new ActivityFeed();
        activityFeed.setSubscriber(optionalSavedUser.get());
        activityFeed.setPost(optionalSavedPost.get());
        activityFeed.setCreatedAt(LocalDateTime.now());

        activityFeedRepository.save(activityFeed);

        Optional<ActivityFeed> optionalSavedActivityFeed = activityFeedRepository.findById(activityFeed.getId());
        assertTrue(optionalSavedActivityFeed.isPresent(), "Сохраненный ActivityFeed не должен быть пустым");

        user.setUsername("Jane");
        activityFeed.setSubscriber(user);
        activityFeedRepository.save(activityFeed);

        Optional<ActivityFeed> optionalUpdatedActivityFeed = activityFeedRepository.findById(activityFeed.getId());
        assertTrue(optionalUpdatedActivityFeed.isPresent(), "Обновленный ActivityFeed не должен быть пустым");

        assertEquals("Jane", optionalUpdatedActivityFeed.get().getSubscriber().getUsername(),
                "Имя пользователя должно быть Jane");
    }

    /**
     * Негативный тест обновления сущности ActivityFeed.
     */
    @Test
    void whenUpdateActivityFeed_ThenActivityFeedIsUpdatedFail() {
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

        ActivityFeed activityFeed = new ActivityFeed();
        activityFeed.setSubscriber(optionalSavedUser.get());
        activityFeed.setPost(optionalSavedPost.get());
        activityFeed.setCreatedAt(LocalDateTime.now());

        activityFeedRepository.save(activityFeed);

        Optional<ActivityFeed> optionalSavedActivityFeed = activityFeedRepository.findById(activityFeed.getId());
        assertTrue(optionalSavedActivityFeed.isPresent(), "Сохраненный ActivityFeed не должен быть пустым");

        user.setUsername("Jane");
        activityFeed.setSubscriber(user);
        activityFeedRepository.save(activityFeed);

        Optional<ActivityFeed> optionalUpdatedActivityFeed = activityFeedRepository.findById(activityFeed.getId());
        assertTrue(optionalUpdatedActivityFeed.isPresent(), "Обновленный ActivityFeed не должен быть пустым");

        assertNotEquals("John", optionalUpdatedActivityFeed.get().getSubscriber().getUsername(),
                "Имя пользователя не должно быть John");
    }

    /**
     * Позитивный тест удаления сущности ActivityFeed.
     */
    @Test
    void whenDeleteActivityFeed_ThenActivityFeedIsDeletedSuccess() {
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

        ActivityFeed activityFeed = new ActivityFeed();
        activityFeed.setSubscriber(optionalSavedUser.get());
        activityFeed.setPost(optionalSavedPost.get());
        activityFeed.setCreatedAt(LocalDateTime.now());

        activityFeedRepository.save(activityFeed);

        Optional<ActivityFeed> optionalSavedActivityFeed = activityFeedRepository.findById(activityFeed.getId());
        assertTrue(optionalSavedActivityFeed.isPresent(), "Сохраненный ActivityFeed не должен быть пустым");

        activityFeedRepository.delete(activityFeed);

        Optional<ActivityFeed> optionalDeletedActivityFeed = activityFeedRepository.findById(
                optionalSavedActivityFeed.get().getId());

        assertFalse(optionalDeletedActivityFeed.isPresent(),
                "ActivityFeed не должен присутствовать в базе данных");
    }

    /**
     * Негативный тест удаления сущности ActivityFeed.
     */
    @Test
    void whenDeleteActivityFeed_ThenActivityFeedIsDeletedFail() {
        Long nonExistentId = 999L;
        long initialCount = activityFeedRepository.count();

        assertDoesNotThrow(() -> activityFeedRepository.deleteById(nonExistentId));
        assertEquals(initialCount, activityFeedRepository.count());
    }
}