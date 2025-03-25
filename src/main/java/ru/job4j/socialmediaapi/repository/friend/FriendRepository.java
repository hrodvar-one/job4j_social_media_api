package ru.job4j.socialmediaapi.repository.friend;

import org.springframework.data.repository.ListCrudRepository;
import ru.job4j.socialmediaapi.entity.Friend;

public interface FriendRepository extends ListCrudRepository<Friend, Long> {
}
