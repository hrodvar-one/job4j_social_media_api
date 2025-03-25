package ru.job4j.socialmediaapi.repository.postimage;

import org.springframework.data.repository.ListCrudRepository;
import ru.job4j.socialmediaapi.entity.PostImage;

public interface PostImageRepository extends ListCrudRepository<PostImage, Long> {
}
