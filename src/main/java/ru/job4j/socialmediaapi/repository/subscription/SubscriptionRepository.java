package ru.job4j.socialmediaapi.repository.subscription;

import org.springframework.data.repository.ListCrudRepository;
import ru.job4j.socialmediaapi.entity.Subscription;

public interface SubscriptionRepository extends ListCrudRepository<Subscription, Long> {
}
