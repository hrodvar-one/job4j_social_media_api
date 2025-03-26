package ru.job4j.socialmediaapi.repository.post;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.socialmediaapi.entity.Post;

@Repository
public interface PostRepository extends ListCrudRepository<Post, Long> {
}
