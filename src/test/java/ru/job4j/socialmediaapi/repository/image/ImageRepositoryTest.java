package ru.job4j.socialmediaapi.repository.image;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.socialmediaapi.entity.Image;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class ImageRepositoryTest {

    @Autowired
    private ImageRepository imageRepository;

    @BeforeEach
    public void setUp() {
        imageRepository.deleteAll();
    }

    /**
     * Позитивный тест сохранения сущности Image.
     */
    @Test
    public void whenSaveImageThenImageIsSavedSuccess() {
        Image image = new Image();
        image.setName("test");
        image.setImageUrl("http://localhost:8080/images/1");
        image.setCreatedAt(LocalDateTime.now());
        imageRepository.save(image);
        Optional<Image> optionalSavedImage = imageRepository.findById(image.getId());
        assertThat(optionalSavedImage).isPresent();
        assertThat(optionalSavedImage.get().getName()).isEqualTo("test");
    }

    /**
     * Негативный тест сохранения сущности Image.
     */
    @Test
    public void whenSaveImageThenImageIsNotSavedFail() {
        Image image = new Image();
        image.setName("test");
        image.setImageUrl("http://localhost:8080/images/1");
        image.setCreatedAt(LocalDateTime.now());
        imageRepository.save(image);
        Optional<Image> optionalSavedImage = imageRepository.findById(image.getId());
        assertThat(optionalSavedImage).isPresent();
        assertThat(optionalSavedImage.get().getName()).isEqualTo("test");
    }

    /**
     * Позитивный тест вывода всех объектов типа Image.
     */
    @Test
    public void whenGetAllImagesThenImagesAreFoundSuccess() {
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

        List<Image> images = imageRepository.findAll();
        assertThat(images).hasSize(2);
        assertThat(images).containsExactlyInAnyOrder(image1, image2);
    }

    /**
     * Негативный тест вывода всех объектов типа Image.
     */
    @Test
    public void whenGetAllImagesThenImagesAreFoundFail() {
        List<Image> images = imageRepository.findAll();

        assertTrue(images.isEmpty(), "Список объектов типа Image должен быть пустым");
    }

    /**
     * Позитивный тест поиска сущности типа Image по ID.
     */
    @Test
    public void whenGetImageByIdThenImageIsFoundSuccess() {
        Image image = new Image();
        image.setName("test");
        image.setImageUrl("http://localhost:8080/images/1");
        image.setCreatedAt(LocalDateTime.now());

        imageRepository.save(image);

        Optional<Image> optionalSavedImage = imageRepository.findById(image.getId());
        assertTrue(optionalSavedImage.isPresent(), "Сохранённый Image не должен быть пустым");
        assertEquals(image.getName(), optionalSavedImage.get().getName(), "Name у Image должны совпадать");
    }

    /**
     * Негативный тест поиска сущности типа Image по ID.
     */
    @Test
    public void whenGetImageByIdThenImageIsFoundFail() {
        Optional<Image> optionalImage = imageRepository.findById(-1L);

        assertFalse(optionalImage.isPresent(), "Image не должен присутствовать в базе данных.");
    }

    /**
     * Позитивный тест обновления сущности Image.
     */
    @Test
    public void whenUpdateImageThenImageIsUpdatedSuccess() {
        Image image = new Image();
        image.setName("test");
        image.setImageUrl("http://localhost:8080/images/1");
        image.setCreatedAt(LocalDateTime.now());

        imageRepository.save(image);

        Optional<Image> optionalSavedImage = imageRepository.findById(image.getId());
        assertTrue(optionalSavedImage.isPresent(), "Сохранённый Image не должен быть пустым");

        image.setName("newName");
        imageRepository.save(image);

        Optional<Image> optionalUpdatedImage = imageRepository.findById(image.getId());
        assertTrue(optionalUpdatedImage.isPresent(), "Обновлённый Image не должен быть пустым");

        assertEquals("newName", optionalUpdatedImage.get().getName(), "Name у Image должно быть обновлено");
        assertEquals("http://localhost:8080/images/1", optionalUpdatedImage.get().getImageUrl(), "ImageUrl не должен измениться");
    }

    /**
     * Негативный тест обновления сущности типа Image.
     */
    @Test
    public void whenUpdateImageThenImageIsUpdatedFail() {
        Image image = new Image();
        image.setName("test");
        image.setImageUrl("http://localhost:8080/images/1");
        image.setCreatedAt(LocalDateTime.now());

        imageRepository.save(image);

        Optional<Image> optionalSavedImage = imageRepository.findById(image.getId());
        assertTrue(optionalSavedImage.isPresent(), "Сохранённый Image не должен быть пустым");

        image.setName("newName");
        imageRepository.save(image);

        Optional<Image> optionalUpdatedImage = imageRepository.findById(image.getId());
        assertTrue(optionalUpdatedImage.isPresent(), "Обновлённый Image не должен быть пустым");

        assertNotEquals("test", optionalUpdatedImage.get().getName(), "Name у Image должно быть обновлено");
    }

    /**
     * Позитивный тест удаления сущности Image.
     */
    @Test
    public void whenDeleteImageThenImageIsDeletedSuccess() {
        Image image = new Image();
        image.setName("test");
        image.setImageUrl("http://localhost:8080/images/1");
        image.setCreatedAt(LocalDateTime.now());

        imageRepository.save(image);

        Optional<Image> optionalSavedImage = imageRepository.findById(image.getId());
        assertTrue(optionalSavedImage.isPresent(), "Сохранённый Image не должен быть пустым");

        imageRepository.delete(optionalSavedImage.get());

        Optional<Image> optionalDeletedImage = imageRepository.findById(optionalSavedImage.get().getId());

        assertFalse(optionalDeletedImage.isPresent(), "Image не должен присутствовать в базе данных.");
    }

    /**
     * Негативный тест удаления сущности Image.
     */
    @Test
    public void whenDeleteImageThenImageIsDeletedFail() {
        Long nonExistentId = 999L;
        long initialCount = imageRepository.count();

        assertDoesNotThrow(() -> imageRepository.deleteById(nonExistentId));
        assertEquals(initialCount, imageRepository.count());
    }
}