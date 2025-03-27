package ru.job4j.socialmediaapi.repository.image;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.socialmediaapi.entity.Image;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
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
    public void whenSaveImage_ThenImageIsSavedSuccess() {
        Image image = new Image();
        image.setName("test");
        image.setImageUrl("http://localhost:8080/images/1");
        image.setCreatedAt(LocalDateTime.now());
        imageRepository.save(image);
        Optional<Image> foundImage = imageRepository.findById(image.getId());
        assertThat(foundImage).isPresent();
        assertThat(foundImage.get().getName()).isEqualTo("test");
    }

    /**
     * Негативный тест сохранения сущности Image.
     */
    @Test
    public void whenSaveImage_ThenImageIsNotSavedFail() {
        Image image = new Image();
        image.setName("test");
        image.setImageUrl("http://localhost:8080/images/1");
        image.setCreatedAt(LocalDateTime.now());
        imageRepository.save(image);
        Optional<Image> foundImage = imageRepository.findById(image.getId());
        assertThat(foundImage).isPresent();
        assertThat(foundImage.get().getName()).isEqualTo("test");
    }

    /**
     * Позитивный тест вывода всех объектов типа Image.
     */
    @Test
    public void whenGetAllImages_ThenImagesAreFoundSuccess() {
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
}