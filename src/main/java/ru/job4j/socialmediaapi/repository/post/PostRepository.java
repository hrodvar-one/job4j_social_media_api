package ru.job4j.socialmediaapi.repository.post;

import org.springframework.data.repository.ListCrudRepository;
import ru.job4j.socialmediaapi.entity.Post;

public interface PostRepository extends ListCrudRepository<Post, Long> {
}
