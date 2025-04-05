package ru.job4j.socialmediaapi.service.image;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.job4j.socialmediaapi.dto.ImageDto;
import ru.job4j.socialmediaapi.entity.Image;
import ru.job4j.socialmediaapi.mapper.ImageMapper;
import ru.job4j.socialmediaapi.repository.image.ImageRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    /**
     * Сохранение изображения в БД.
     * @param multipartImageFile МultipartFile с изображением
     * @return ImageDTO с информацией о сохраненном изображении
     */
    public ImageDto saveImage(MultipartFile multipartImageFile) {
        Image image = new Image();
        image.setName(multipartImageFile.getOriginalFilename());
        image.setImageUrl(uploadImageToFakeStorage(multipartImageFile));
        imageRepository.save(image);
        return ImageMapper.toDto(image);
    }

    /**
     * Загрузка MultipartFile из POST запроса в фейковое хранилище.
     * @param imageFile МultipartFile с изображением
     * @return Возвращает ссылку на изображение в фейковом хранилище.
     */
    public String uploadImageToFakeStorage(MultipartFile imageFile) {

        String fileName = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"))
                + "_" + imageFile.getOriginalFilename();
        return "https://fake-storage.local/uploads/" + fileName;
    }
}
