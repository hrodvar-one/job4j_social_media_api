package ru.job4j.socialmediaapi.repository.friendrequest;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.socialmediaapi.entity.FriendRequest;

import java.util.Optional;

@Repository
public interface FriendRequestRepository extends ListCrudRepository<FriendRequest, Long> {

    Optional<FriendRequest> findBySenderIdAndReceiverId(Long senderId, Long receiverId);
}
