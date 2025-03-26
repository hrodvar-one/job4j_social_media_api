package ru.job4j.socialmediaapi.repository.message;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.socialmediaapi.entity.Message;

@Repository
public interface MessageRepository extends ListCrudRepository<Message, Long> {
}
