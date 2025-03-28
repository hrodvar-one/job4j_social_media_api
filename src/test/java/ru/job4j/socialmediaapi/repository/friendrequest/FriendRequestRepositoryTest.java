package ru.job4j.socialmediaapi.repository.friendrequest;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.socialmediaapi.entity.FriendRequest;
import ru.job4j.socialmediaapi.entity.User;
import ru.job4j.socialmediaapi.repository.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class FriendRequestRepositoryTest {

    @Autowired
    private FriendRequestRepository friendRequestRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        friendRequestRepository.deleteAll();
    }

    /**
     * Позитивный тест сохранения сущности FriendRequest в БД.
     */
    @Test
    public void whenSaveFriendRequest_ThenFriendRequestIsSavedSuccess() {
        User senderUser = new User();
        senderUser.setUsername("Sender");
        senderUser.setEmail("sender@example.com");
        senderUser.setPasswordHash("12345");
        senderUser.setCreatedAt(LocalDateTime.now());

        User receiverUser = new User();
        receiverUser.setUsername("Receiver");
        receiverUser.setEmail("john@example.com");
        receiverUser.setPasswordHash("12345");
        receiverUser.setCreatedAt(LocalDateTime.now());

        userRepository.save(senderUser);
        userRepository.save(receiverUser);

        Optional<User> optionalSavedSenderUser = userRepository.findById(senderUser.getId());

        assertThat(optionalSavedSenderUser).isPresent();

        Optional<User> optionalSavedReceiverUser = userRepository.findById(receiverUser.getId());

        assertThat(optionalSavedReceiverUser).isPresent();

        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setSenderId(optionalSavedSenderUser.get().getId());
        friendRequest.setReceiverId(optionalSavedReceiverUser.get().getId());
        friendRequest.setStatus("pending");
        friendRequest.setCreatedAt(LocalDateTime.now());

        friendRequestRepository.save(friendRequest);

        Optional<FriendRequest> optionalSavedFriendRequest = friendRequestRepository.findById(friendRequest.getId());

        assertThat(optionalSavedFriendRequest).isPresent();
        assertThat(optionalSavedFriendRequest.get().getSenderId()).isEqualTo(senderUser.getId());
    }

    /**
     * Негативный тест сохранения сущности FriendRequest в БД.
     */
    @Test
    public void whenSaveFriendRequest_ThenFriendRequestIsSavedFail() {
        User senderUser = new User();
        senderUser.setUsername("Sender");
        senderUser.setEmail("sender@example.com");
        senderUser.setPasswordHash("12345");
        senderUser.setCreatedAt(LocalDateTime.now());

        User receiverUser = new User();
        receiverUser.setUsername("Receiver");
        receiverUser.setEmail("john@example.com");
        receiverUser.setPasswordHash("12345");
        receiverUser.setCreatedAt(LocalDateTime.now());

        userRepository.save(senderUser);
        userRepository.save(receiverUser);

        Optional<User> optionalSavedSenderUser = userRepository.findById(senderUser.getId());

        assertThat(optionalSavedSenderUser).isPresent();

        Optional<User> optionalSavedReceiverUser = userRepository.findById(receiverUser.getId());

        assertThat(optionalSavedReceiverUser).isPresent();

        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setSenderId(optionalSavedSenderUser.get().getId());
        friendRequest.setReceiverId(optionalSavedReceiverUser.get().getId());
        friendRequest.setStatus("pending");
        friendRequest.setCreatedAt(LocalDateTime.now());

        friendRequestRepository.save(friendRequest);

        Optional<FriendRequest> optionalSavedFriendRequest = friendRequestRepository.findById(friendRequest.getId());

        assertThat(optionalSavedFriendRequest).isPresent();
        assertNotEquals("pending2", optionalSavedFriendRequest.get().getStatus(), "Status не должен быть 'pending2'");
    }

    /**
     * Позитивный тест получения всех сущностей типа FriendRequest из БД.
     */
    @Test
    public void whenGetAllFriendRequests_ThenFriendRequestsAreFoundSuccess() {
        User senderUser1 = new User();
        senderUser1.setUsername("Sender1");
        senderUser1.setEmail("sender1@example.com");
        senderUser1.setPasswordHash("12345");
        senderUser1.setCreatedAt(LocalDateTime.now());

        User senderUser2 = new User();
        senderUser2.setUsername("Sender2");
        senderUser2.setEmail("sender2@example.com");
        senderUser2.setPasswordHash("12345");
        senderUser2.setCreatedAt(LocalDateTime.now());

        User receiverUser = new User();
        receiverUser.setUsername("Receiver");
        receiverUser.setEmail("john@example.com");
        receiverUser.setPasswordHash("12345");
        receiverUser.setCreatedAt(LocalDateTime.now());

        userRepository.save(senderUser1);
        userRepository.save(senderUser2);
        userRepository.save(receiverUser);

        Optional<User> optionalSavedSenderUser1 = userRepository.findById(senderUser1.getId());
        assertThat(optionalSavedSenderUser1).isPresent();

        Optional<User> optionalSavedSenderUser2 = userRepository.findById(senderUser2.getId());
        assertThat(optionalSavedSenderUser2).isPresent();

        Optional<User> optionalSavedReceiverUser = userRepository.findById(receiverUser.getId());
        assertThat(optionalSavedReceiverUser).isPresent();

        FriendRequest friendRequest1 = new FriendRequest();
        friendRequest1.setSenderId(optionalSavedSenderUser1.get().getId());
        friendRequest1.setReceiverId(optionalSavedReceiverUser.get().getId());
        friendRequest1.setStatus("pending");
        friendRequest1.setCreatedAt(LocalDateTime.now());

        FriendRequest friendRequest2 = new FriendRequest();
        friendRequest2.setSenderId(optionalSavedSenderUser2.get().getId());
        friendRequest2.setReceiverId(optionalSavedReceiverUser.get().getId());
        friendRequest2.setStatus("pending");
        friendRequest2.setCreatedAt(LocalDateTime.now());

        friendRequestRepository.save(friendRequest1);
        friendRequestRepository.save(friendRequest2);

        List<FriendRequest> foundFriendRequests = friendRequestRepository.findAll();

        assertThat(foundFriendRequests).hasSize(2);
        assertThat(foundFriendRequests).containsExactlyInAnyOrder(friendRequest1, friendRequest2);
    }

    /**
     * Негативный тест получения всех сущностей типа FriendRequest из БД.
     */
    @Test
    public void whenGetAllFriendRequests_ThenFriendRequestsAreFoundFail() {
        List<FriendRequest> foundFriendRequests = friendRequestRepository.findAll();

        assertTrue(foundFriendRequests.isEmpty(), "Список должен быть пустым");
    }

    /**
     * Позитивный тест получения сущности FriendRequest из БД по ID.
     */
    @Test
    public void whenGetFriendRequestById_ThenFriendRequestIsFoundSuccess() {
        User senderUser = new User();
        senderUser.setUsername("Sender");
        senderUser.setEmail("sender@example.com");
        senderUser.setPasswordHash("12345");
        senderUser.setCreatedAt(LocalDateTime.now());

        User receiverUser = new User();
        receiverUser.setUsername("Receiver");
        receiverUser.setEmail("john@example.com");
        receiverUser.setPasswordHash("12345");
        receiverUser.setCreatedAt(LocalDateTime.now());

        userRepository.save(senderUser);
        userRepository.save(receiverUser);

        Optional<User> optionalSavedSenderUser = userRepository.findById(senderUser.getId());

        assertThat(optionalSavedSenderUser).isPresent();

        Optional<User> optionalSavedReceiverUser = userRepository.findById(receiverUser.getId());

        assertThat(optionalSavedReceiverUser).isPresent();

        FriendRequest friendRequest = new FriendRequest();
        friendRequest.setSenderId(optionalSavedSenderUser.get().getId());
        friendRequest.setReceiverId(optionalSavedReceiverUser.get().getId());
        friendRequest.setStatus("pending");
        friendRequest.setCreatedAt(LocalDateTime.now());

        friendRequestRepository.save(friendRequest);

        Optional<FriendRequest> optionalSavedFriendRequest = friendRequestRepository.findById(friendRequest.getId());

        assertTrue(optionalSavedFriendRequest.isPresent(), "Сохранённый FriendRequest не должен быть пустым");
        assertEquals(friendRequest.getStatus(), optionalSavedFriendRequest.get().getStatus(), "Status должен совпадать");
    }
}