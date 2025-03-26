package ru.job4j.socialmediaapi.repository.friend;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.socialmediaapi.entity.Friend;

@Repository
public interface FriendRepository extends ListCrudRepository<Friend, Long> {
}
