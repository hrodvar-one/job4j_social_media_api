package ru.job4j.socialmediaapi.repository.friendrequest;

import org.springframework.data.repository.ListCrudRepository;
import ru.job4j.socialmediaapi.entity.FriendRequest;

public interface FriendRequestRepository extends ListCrudRepository<FriendRequest, Long> {
}
