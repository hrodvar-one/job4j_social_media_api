package ru.job4j.socialmediaapi.service.friendrequest;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.socialmediaapi.entity.Friend;
import ru.job4j.socialmediaapi.entity.FriendRequest;
import ru.job4j.socialmediaapi.entity.Subscription;
import ru.job4j.socialmediaapi.entity.User;
import ru.job4j.socialmediaapi.repository.friend.FriendRepository;
import ru.job4j.socialmediaapi.repository.friendrequest.FriendRequestRepository;
import ru.job4j.socialmediaapi.repository.subscription.SubscriptionRepository;
import ru.job4j.socialmediaapi.repository.user.UserRepository;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class FriendRequestService {

    private final FriendRequestRepository friendRequestRepository;
    private final FriendRepository friendRepository;
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    /**
     * Отправка заявки в друзья
     */
    @Transactional
    public void sendFriendRequest(Long senderId, Long receiverId) {

        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        if (friendRequestRepository.findBySenderIdAndReceiverId(senderId, receiverId).isPresent()) {
            throw new RuntimeException("Friend request already exists");
        }

        FriendRequest request = new FriendRequest();
        request.setSenderId(senderId);
        request.setReceiverId(receiverId);
        request.setStatus("pending");
        request.setCreatedAt(LocalDateTime.now());
        friendRequestRepository.save(request);

        if (subscriptionRepository.findBySubscriberIdAndSubscribedToId(senderId, receiverId).isEmpty()) {
            Subscription sub = new Subscription();
            sub.setSubscriber(sender);
            sub.setSubscribedTo(receiver);
            sub.setCreatedAt(LocalDateTime.now());
            subscriptionRepository.save(sub);
        }
    }

    /**
     * Принятие заявки в друзья
     */
    @Transactional
    public void acceptFriendRequest(Long receiverId, Long senderId) {
        FriendRequest request = friendRequestRepository.findBySenderIdAndReceiverId(senderId, receiverId)
                .orElseThrow(() -> new RuntimeException("Friend request not found"));

        if (!"pending".equals(request.getStatus())) {
            throw new RuntimeException("Friend request is not pending");
        }

        request.setStatus("accepted");
        friendRequestRepository.save(request);

        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("Sender not found"));
        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        if (!friendRepository.existsByUserIdAndFriendId(senderId, receiverId)) {
            Friend f1 = new Friend();
            f1.setUser(sender);
            f1.setFriend(receiver);
            f1.setCreatedAt(LocalDateTime.now());
            friendRepository.save(f1);
        }

        if (!friendRepository.existsByUserIdAndFriendId(receiverId, senderId)) {
            Friend f2 = new Friend();
            f2.setUser(receiver);
            f2.setFriend(sender);
            f2.setCreatedAt(LocalDateTime.now());
            friendRepository.save(f2);
        }

        if (subscriptionRepository.findBySubscriberIdAndSubscribedToId(senderId, receiverId).isEmpty()) {
            Subscription sub1 = new Subscription();
            sub1.setSubscriber(sender);
            sub1.setSubscribedTo(receiver);
            sub1.setCreatedAt(LocalDateTime.now());
            subscriptionRepository.save(sub1);
        }

        if (subscriptionRepository.findBySubscriberIdAndSubscribedToId(receiverId, senderId).isEmpty()) {
            Subscription sub2 = new Subscription();
            sub2.setSubscriber(receiver);
            sub2.setSubscribedTo(sender);
            sub2.setCreatedAt(LocalDateTime.now());
            subscriptionRepository.save(sub2);
        }
    }

    /**
     * Отклонение заявки в друзья
     */
    @Transactional
    public void rejectFriendRequest(Long receiverId, Long senderId) {
        FriendRequest request = friendRequestRepository.findBySenderIdAndReceiverId(senderId, receiverId)
                .orElseThrow(() -> new RuntimeException("Friend request not found"));

        if (!"pending".equals(request.getStatus())) {
            throw new RuntimeException("Friend request is not pending");
        }

        request.setStatus("rejected");
        friendRequestRepository.save(request);
    }
}
