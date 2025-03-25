package ru.job4j.socialmediaapi.repository.image;

import org.springframework.data.repository.ListCrudRepository;
import ru.job4j.socialmediaapi.entity.Image;

public interface ImageRepository extends ListCrudRepository<Image, Long> {
}
