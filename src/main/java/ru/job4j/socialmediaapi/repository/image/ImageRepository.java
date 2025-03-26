package ru.job4j.socialmediaapi.repository.image;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.socialmediaapi.entity.Image;

@Repository
public interface ImageRepository extends ListCrudRepository<Image, Long> {
}
