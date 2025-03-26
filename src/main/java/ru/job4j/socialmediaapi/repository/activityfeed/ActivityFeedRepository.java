package ru.job4j.socialmediaapi.repository.activityfeed;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.socialmediaapi.entity.ActivityFeed;

@Repository
public interface ActivityFeedRepository extends ListCrudRepository<ActivityFeed, Long> {
}
