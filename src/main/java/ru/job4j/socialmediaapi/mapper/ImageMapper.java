package ru.job4j.socialmediaapi.mapper;

import ru.job4j.socialmediaapi.dto.ImageDto;
import ru.job4j.socialmediaapi.entity.Image;

public class ImageMapper {

    public static ImageDto toDto(Image image) {
        ImageDto imageDTO = new ImageDto();
        imageDTO.setName(image.getName());
        imageDTO.setImageUrl(image.getImageUrl());
        imageDTO.setCreatedAt(image.getCreatedAt());
        return imageDTO;
    }

    public static Image toEntity(ImageDto imageDTO) {
        Image image = new Image();
        image.setName(imageDTO.getName());
        image.setImageUrl(imageDTO.getImageUrl());
        image.setCreatedAt(imageDTO.getCreatedAt());
        return image;
    }
}
