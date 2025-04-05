package ru.job4j.socialmediaapi.repository.subscription;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.socialmediaapi.entity.Subscription;

import java.util.Optional;

@Repository
public interface SubscriptionRepository extends ListCrudRepository<Subscription, Long> {

    Optional<Subscription> findBySubscriberIdAndSubscribedToId(Long subscriberId, Long subscribedToId);
}
