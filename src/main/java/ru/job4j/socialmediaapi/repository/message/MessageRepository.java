package ru.job4j.socialmediaapi.repository.message;

import org.springframework.data.repository.ListCrudRepository;
import ru.job4j.socialmediaapi.entity.Message;

public interface MessageRepository extends ListCrudRepository<Message, Long> {
}
