package ru.job4j.socialmediaapi.repository.postimage;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.socialmediaapi.entity.PostImage;

@Repository
public interface PostImageRepository extends ListCrudRepository<PostImage, Long> {
}
