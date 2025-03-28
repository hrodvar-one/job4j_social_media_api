package ru.job4j.socialmediaapi.repository.postimage;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.socialmediaapi.entity.Image;
import ru.job4j.socialmediaapi.entity.Post;
import ru.job4j.socialmediaapi.entity.PostImage;
import ru.job4j.socialmediaapi.entity.User;
import ru.job4j.socialmediaapi.repository.image.ImageRepository;
import ru.job4j.socialmediaapi.repository.post.PostRepository;
import ru.job4j.socialmediaapi.repository.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class PostImageRepositoryTest {

    @Autowired
    private PostImageRepository postImageRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        imageRepository.deleteAll();
        userRepository.deleteAll();
        postRepository.deleteAll();
        postImageRepository.deleteAll();
    }

    /**
     * Позитивный тест сохранения сущности PostImage.
     */
    @Test
    public void whenSavePostImage_ThenPostImageSavedSuccess() {
        User user = new User();
        user.setUsername("John");
        user.setEmail("john@example.com");
        user.setPasswordHash("password");
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

        Image image = new Image();
        image.setName("test");
        image.setImageUrl("http://localhost:8080/images/1");
        image.setCreatedAt(LocalDateTime.now());
        imageRepository.save(image);

        Optional<Image> optionalSavedImage = imageRepository.findById(image.getId());

        assertThat(optionalSavedImage).isPresent();

        PostImage postImage = new PostImage();
        postImage.setPost(post);
        postImage.setImage(image);

        postImageRepository.save(postImage);

        Optional<PostImage> optionalSavedPostImage = postImageRepository.findById(postImage.getId());

        assertThat(optionalSavedPostImage).isPresent();

        assertThat(optionalSavedPostImage.get().getPost().getTitle()).isEqualTo("Test Post");
    }

    /**
     * Негативный тест сохранения сущности PostImage.
     */
    @Test
    public void whenSavePostImage_ThenPostImageSavedFail() {
        User user = new User();
        user.setUsername("John");
        user.setEmail("john@example.com");
        user.setPasswordHash("password");
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

        Image image = new Image();
        image.setName("test");
        image.setImageUrl("http://localhost:8080/images/1");
        image.setCreatedAt(LocalDateTime.now());
        imageRepository.save(image);

        Optional<Image> optionalSavedImage = imageRepository.findById(image.getId());

        assertThat(optionalSavedImage).isPresent();

        PostImage postImage = new PostImage();
        postImage.setPost(post);
        postImage.setImage(image);

        postImageRepository.save(postImage);

        Optional<PostImage> optionalSavedPostImage = postImageRepository.findById(postImage.getId());

        assertThat(optionalSavedPostImage).isPresent();

        assertNotEquals("Test Post2",
                optionalSavedPostImage.get().getPost().getTitle(),
                "Title не должен быть 'Test Post2'");
    }

    /**
     * Позитивный тест вывода всех сущностей типа PostImage.
     */
    @Test
    public void whenGetAllPostImages_ThenPostImagesFoundSuccess() {
        User user = new User();
        user.setUsername("John");
        user.setEmail("john@example.com");
        user.setPasswordHash("password");
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);

        Post post = new Post();
        post.setTitle("Test Post");
        post.setContent("This is a test post.");
        post.setUser(user);
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());

        postRepository.save(post);

        Image image1 = new Image();
        image1.setName("test1");
        image1.setImageUrl("http://localhost:8080/images/1");
        image1.setCreatedAt(LocalDateTime.now());

        Image image2 = new Image();
        image2.setName("test2");
        image2.setImageUrl("http://localhost:8080/images/2");
        image2.setCreatedAt(LocalDateTime.now());

        imageRepository.save(image1);
        imageRepository.save(image2);

        PostImage postImage1 = new PostImage();
        postImage1.setPost(post);
        postImage1.setImage(image1);

        PostImage postImage2 = new PostImage();
        postImage2.setPost(post);
        postImage2.setImage(image2);

        postImageRepository.save(postImage1);
        postImageRepository.save(postImage2);

        List<PostImage> postImages = postImageRepository.findAll();
        assertThat(postImages).hasSize(2);
        assertThat(postImages).containsExactlyInAnyOrder(postImage1, postImage2);
    }

    /**
     * Негативный тест вывода всех сущностей типа PostImage.
     */
    @Test
    public void whenGetAllPostImages_ThenPostImagesFoundFail() {
        List<PostImage> postImages = postImageRepository.findAll();

        assertTrue(postImages.isEmpty(), "Список PostImages должен быть пустым");
    }

    /**
     * Позитивный тест получения сущности PostImage по id.
     */
    @Test
    public void whenGetPostImageById_ThenPostImageFoundSuccess() {
        User user = new User();
        user.setUsername("John");
        user.setEmail("john@example.com");
        user.setPasswordHash("password");
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);

        Post post = new Post();
        post.setTitle("Test Post");
        post.setContent("This is a test post.");
        post.setUser(user);
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());

        postRepository.save(post);

        Image image = new Image();
        image.setName("test");
        image.setImageUrl("http://localhost:8080/images/1");
        image.setCreatedAt(LocalDateTime.now());

        imageRepository.save(image);

        PostImage postImage = new PostImage();
        postImage.setPost(post);
        postImage.setImage(image);

        postImageRepository.save(postImage);

        Optional<PostImage> optionalSavedPostImage = postImageRepository.findById(postImage.getId());
        assertTrue(optionalSavedPostImage.isPresent(), "Сохраненный PostImage не должен быть пустым");
        assertEquals(postImage.getPost().getTitle(), optionalSavedPostImage.get().getPost().getTitle(), "Title должен совпадать");
    }

    /**
     * Негативный тест получения сущности PostImage по id.
     */
    @Test
    public void whenGetPostImageById_ThenPostImageFoundFail() {
        Optional<PostImage> optionalPostImage = postImageRepository.findById(-1L);

        assertFalse(optionalPostImage.isPresent(), "PostImage не должен присутствовать в базе данных");
    }

    /**
     * Позитивный тест обновления сущности PostImage.
     */
    @Test
    public void whenUpdatePostImage_ThenPostImageUpdatedSuccess() {
        User user = new User();
        user.setUsername("John");
        user.setEmail("john@example.com");
        user.setPasswordHash("password");
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);

        Post post = new Post();
        post.setTitle("Test Post");
        post.setContent("This is a test post.");
        post.setUser(user);
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());

        postRepository.save(post);

        Image image = new Image();
        image.setName("test");
        image.setImageUrl("http://localhost:8080/images/1");
        image.setCreatedAt(LocalDateTime.now());

        imageRepository.save(image);

        PostImage postImage = new PostImage();
        postImage.setPost(post);
        postImage.setImage(image);

        postImageRepository.save(postImage);

        post.setTitle("Updated Post");
        postRepository.save(post);

        postImage.setPost(post);
        postImageRepository.save(postImage);

        Optional<PostImage> optionalUpdatedPostImage = postImageRepository.findById(postImage.getId());
        assertTrue(optionalUpdatedPostImage.isPresent(), "Обновленный PostImage не должен быть пустым");

        assertEquals("Updated Post",
                optionalUpdatedPostImage.get().getPost().getTitle(),
                "Title у Post в PostImage должен быть обновлен");

        assertEquals("test",
                optionalUpdatedPostImage.get().getImage().getName(),
                "Name у Image в PostImage не должен измениться");
    }

    /**
     * Негативный тест обновления сущности PostImage.
     */
    @Test
    public void whenUpdatePostImage_ThenPostImageUpdatedFail() {
        User user = new User();
        user.setUsername("John");
        user.setEmail("john@example.com");
        user.setPasswordHash("password");
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);

        Post post = new Post();
        post.setTitle("Test Post");
        post.setContent("This is a test post.");
        post.setUser(user);
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());

        postRepository.save(post);

        Image image = new Image();
        image.setName("test");
        image.setImageUrl("http://localhost:8080/images/1");
        image.setCreatedAt(LocalDateTime.now());

        imageRepository.save(image);

        PostImage postImage = new PostImage();
        postImage.setPost(post);
        postImage.setImage(image);

        postImageRepository.save(postImage);

        post.setTitle("Updated Post");
        postRepository.save(post);

        postImage.setPost(post);
        postImageRepository.save(postImage);

        Optional<PostImage> optionalUpdatedPostImage = postImageRepository.findById(postImage.getId());
        assertTrue(optionalUpdatedPostImage.isPresent(), "Обновленный PostImage не должен быть пустым");

        assertNotEquals("Test Post",
                optionalUpdatedPostImage.get().getPost().getTitle(),
                "Title у Post в PostImage должен быть обновлен");
    }

    /**
     * Позитивный тест удаления сущности PostImage.
     */
    @Test
    public void whenDeletePostImage_ThenPostImageIsDeletedSuccess() {
        User user = new User();
        user.setUsername("John");
        user.setEmail("john@example.com");
        user.setPasswordHash("password");
        user.setCreatedAt(LocalDateTime.now());

        userRepository.save(user);

        Post post = new Post();
        post.setTitle("Test Post");
        post.setContent("This is a test post.");
        post.setUser(user);
        post.setCreatedAt(LocalDateTime.now());
        post.setUpdatedAt(LocalDateTime.now());

        postRepository.save(post);

        Image image = new Image();
        image.setName("test");
        image.setImageUrl("http://localhost:8080/images/1");
        image.setCreatedAt(LocalDateTime.now());

        imageRepository.save(image);

        PostImage postImage = new PostImage();
        postImage.setPost(post);
        postImage.setImage(image);

        postImageRepository.save(postImage);

        Optional<PostImage> optionalSavedPostImage = postImageRepository.findById(postImage.getId());
        assertTrue(optionalSavedPostImage.isPresent(), "Сохраненный PostImage не должен быть пустым");

        postImageRepository.delete(optionalSavedPostImage.get());

        Optional<PostImage> optionalDeletedPostImage = postImageRepository.findById(optionalSavedPostImage.get().getId());

        assertFalse(optionalDeletedPostImage.isPresent(), "PostImage не должен присутствовать в базе данных.");
    }

    /**
     * Негативный тест удаления сущности PostImage.
     */
    @Test
    public void whenDeletePostImage_ThenPostImageIsDeletedFail() {
        Long nonExistentId = 999L;
        long initialCount = postImageRepository.count();

        assertDoesNotThrow(() -> postImageRepository.deleteById(nonExistentId), "Удаление должно завершиться успешно");
        assertEquals(initialCount, postImageRepository.count());
    }
}