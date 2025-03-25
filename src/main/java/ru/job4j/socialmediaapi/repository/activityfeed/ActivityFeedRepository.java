package ru.job4j.socialmediaapi.repository.activityfeed;

import org.springframework.data.repository.ListCrudRepository;
import ru.job4j.socialmediaapi.entity.ActivityFeed;

public interface ActivityFeedRepository extends ListCrudRepository<ActivityFeed, Long> {
}
