package ru.job4j.socialmediaapi.repository.user;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ru.job4j.socialmediaapi.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends ListCrudRepository<User, Long> {

    @Query("""
            SELECT u
            FROM User u
            WHERE u.username = :username
            AND u.passwordHash = :passwordHash
            """)
    Optional<User> findByUsernameAndPasswordHash(String username, String passwordHash);

    @Query("""
            SELECT s.subscriber
            FROM Subscription s
            WHERE s.subscribedTo.id = :userId
            """)
    List<User> findAllSubscribersByUserId(@Param("userId") Long userId);

    @Query("""
            SELECT f.user
            FROM Friend f
            WHERE f.friend.id = :userId
            """)
    List<User> findAllFriendsByUserId(@Param("userId") Long userId);
}
