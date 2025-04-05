package ru.job4j.socialmediaapi.service.subscription;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.socialmediaapi.repository.friend.FriendRepository;
import ru.job4j.socialmediaapi.repository.subscription.SubscriptionRepository;

@Service
@RequiredArgsConstructor
public class SubscriptionService {

    private final SubscriptionRepository subscriptionRepository;
    private final FriendRepository friendRepository;

    @Transactional
    public void removeFriendAndUnfollow(Long userId, Long friendId) {

        friendRepository.findByUserIdAndFriendId(userId, friendId)
                .ifPresent(friendRepository::delete);

        subscriptionRepository.findBySubscriberIdAndSubscribedToId(userId, friendId)
                .ifPresent(subscriptionRepository::delete);
    }
}
