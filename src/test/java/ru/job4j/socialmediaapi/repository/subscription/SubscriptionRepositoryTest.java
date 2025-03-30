package ru.job4j.socialmediaapi.repository.subscription;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.socialmediaapi.entity.Subscription;
import ru.job4j.socialmediaapi.entity.User;
import ru.job4j.socialmediaapi.repository.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class SubscriptionRepositoryTest {

    @Autowired
    private SubscriptionRepository subscriptionRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        subscriptionRepository.deleteAll();
    }

    /**
     * Позитивный тест сохранения сущности Subscription в БД.
     */
    @Test
    public void whenSaveSubscriptionThenSubscriptionIsSavedSuccess() {
        User subscriberUser = new User();
        subscriberUser.setUsername("Subscriber");
        subscriberUser.setEmail("subscriber@example.com");
        subscriberUser.setPasswordHash("12345");
        subscriberUser.setCreatedAt(LocalDateTime.now());

        User subscribedToUser = new User();
        subscribedToUser.setUsername("subscribedTo");
        subscribedToUser.setEmail("subscribedTo@example.com");
        subscribedToUser.setPasswordHash("12345");
        subscribedToUser.setCreatedAt(LocalDateTime.now());

        userRepository.save(subscriberUser);
        userRepository.save(subscribedToUser);

        Optional<User> optionalSavedSubscriberUser = userRepository.findById(subscriberUser.getId());

        assertThat(optionalSavedSubscriberUser).isPresent();

        Optional<User> optionalSavedSubscribedToUser = userRepository.findById(subscribedToUser.getId());

        assertThat(optionalSavedSubscribedToUser).isPresent();

        Subscription subscription = new Subscription();
        subscription.setSubscriber(optionalSavedSubscriberUser.get());
        subscription.setSubscribedTo(optionalSavedSubscribedToUser.get());
        subscription.setCreatedAt(LocalDateTime.now());

        subscriptionRepository.save(subscription);

        Optional<Subscription> optionalSavedSubscription = subscriptionRepository.findById(subscription.getId());

        assertThat(optionalSavedSubscription).isPresent();
        assertThat(optionalSavedSubscription.get().getSubscriber().getUsername()).isEqualTo("Subscriber");
    }

    /**
     * Негативный тест сохранения сущности Subscription в БД.
     */
    @Test
    public void whenSaveSubscriptionThenSubscriptionIsSavedFail() {
        User subscriberUser = new User();
        subscriberUser.setUsername("Subscriber");
        subscriberUser.setEmail("subscriber@example.com");
        subscriberUser.setPasswordHash("12345");
        subscriberUser.setCreatedAt(LocalDateTime.now());

        User subscribedToUser = new User();
        subscribedToUser.setUsername("subscribedTo");
        subscribedToUser.setEmail("subscribedTo@example.com");
        subscribedToUser.setPasswordHash("12345");
        subscribedToUser.setCreatedAt(LocalDateTime.now());

        userRepository.save(subscriberUser);
        userRepository.save(subscribedToUser);

        Optional<User> optionalSavedSubscriberUser = userRepository.findById(subscriberUser.getId());

        assertThat(optionalSavedSubscriberUser).isPresent();

        Optional<User> optionalSavedSubscribedToUser = userRepository.findById(subscribedToUser.getId());

        assertThat(optionalSavedSubscribedToUser).isPresent();

        Subscription subscription = new Subscription();
        subscription.setSubscriber(optionalSavedSubscriberUser.get());
        subscription.setSubscribedTo(optionalSavedSubscribedToUser.get());
        subscription.setCreatedAt(LocalDateTime.now());

        subscriptionRepository.save(subscription);

        Optional<Subscription> optionalSavedSubscription = subscriptionRepository.findById(subscription.getId());

        assertThat(optionalSavedSubscription).isPresent();
        assertNotEquals("Subscriber2", optionalSavedSubscription.get().getSubscriber().getUsername(),
                "Subscriber не должен быть 'Subscriber2'");
    }

    /**
     * Позитивный тест получения всех сущностей типа Subscription из БД.
     */
    @Test
    public void whenGetAllSubscriptionsThenSubscriptionsAreFoundSuccess() {
        User subscriberUser1 = new User();
        subscriberUser1.setUsername("Sender1");
        subscriberUser1.setEmail("sender1@example.com");
        subscriberUser1.setPasswordHash("12345");
        subscriberUser1.setCreatedAt(LocalDateTime.now());

        User subscriberUser2 = new User();
        subscriberUser2.setUsername("Sender2");
        subscriberUser2.setEmail("sender2@example.com");
        subscriberUser2.setPasswordHash("12345");
        subscriberUser2.setCreatedAt(LocalDateTime.now());

        User subscribedToUser = new User();
        subscribedToUser.setUsername("Receiver");
        subscribedToUser.setEmail("john@example.com");
        subscribedToUser.setPasswordHash("12345");
        subscribedToUser.setCreatedAt(LocalDateTime.now());

        userRepository.save(subscriberUser1);
        userRepository.save(subscriberUser2);
        userRepository.save(subscribedToUser);

        Optional<User> optionalSavedSubscriberUser1 = userRepository.findById(subscriberUser1.getId());
        assertThat(optionalSavedSubscriberUser1).isPresent();

        Optional<User> optionalSavedSubscriberUser2 = userRepository.findById(subscriberUser2.getId());
        assertThat(optionalSavedSubscriberUser2).isPresent();

        Optional<User> optionalSavedSubscribedToUser = userRepository.findById(subscribedToUser.getId());
        assertThat(optionalSavedSubscribedToUser).isPresent();

        Subscription subscription1 = new Subscription();
        subscription1.setSubscriber(optionalSavedSubscriberUser1.get());
        subscription1.setSubscribedTo(optionalSavedSubscribedToUser.get());
        subscription1.setCreatedAt(LocalDateTime.now());

        Subscription subscription2 = new Subscription();
        subscription2.setSubscriber(optionalSavedSubscriberUser2.get());
        subscription2.setSubscribedTo(optionalSavedSubscribedToUser.get());
        subscription2.setCreatedAt(LocalDateTime.now());

        subscriptionRepository.save(subscription1);
        subscriptionRepository.save(subscription2);

        List<Subscription> foundSubscriptions = subscriptionRepository.findAll();

        assertThat(foundSubscriptions).hasSize(2);
        assertThat(foundSubscriptions).containsExactlyInAnyOrder(subscription1, subscription2);
    }

    /**
     * Негативный тест получения всех сущностей типа Subscription из БД.
     */
    @Test
    public void whenGetAllSubscriptionsThenSubscriptionsAreFoundFail() {
        List<Subscription> foundSubscriptions = subscriptionRepository.findAll();

        assertTrue(foundSubscriptions.isEmpty(), "Список подписок должен быть пустым");
    }

    /**
     * Позитивный тест получения сущности Subscription по ID.
     */
    @Test
    public void whenGetSubscriptionByIdThenSubscriptionIsFoundSuccess() {
        User subscriberUser = new User();
        subscriberUser.setUsername("Subscriber");
        subscriberUser.setEmail("subscriber@example.com");
        subscriberUser.setPasswordHash("12345");
        subscriberUser.setCreatedAt(LocalDateTime.now());

        User subscribedToUser = new User();
        subscribedToUser.setUsername("subscribedTo");
        subscribedToUser.setEmail("subscribedTo@example.com");
        subscribedToUser.setPasswordHash("12345");
        subscribedToUser.setCreatedAt(LocalDateTime.now());

        userRepository.save(subscriberUser);
        userRepository.save(subscribedToUser);

        Optional<User> optionalSavedSubscriberUser = userRepository.findById(subscriberUser.getId());

        assertThat(optionalSavedSubscriberUser).isPresent();

        Optional<User> optionalSavedSubscribedToUser = userRepository.findById(subscribedToUser.getId());

        assertThat(optionalSavedSubscribedToUser).isPresent();

        Subscription subscription = new Subscription();
        subscription.setSubscriber(optionalSavedSubscriberUser.get());
        subscription.setSubscribedTo(optionalSavedSubscribedToUser.get());
        subscription.setCreatedAt(LocalDateTime.now());

        subscriptionRepository.save(subscription);

        Optional<Subscription> optionalSavedSubscription = subscriptionRepository.findById(subscription.getId());

        assertTrue(optionalSavedSubscription.isPresent(), "Сохранённый Subscription не должен быть пустым");
        assertEquals(subscription.getSubscriber().getUsername(), optionalSavedSubscription.get().getSubscriber().getUsername(),
                "Username должен совпадать");
    }

    /**
     * Негативный тест получения сущности Subscription по ID.
     */
    @Test
    public void whenGetSubscriptionByIdThenSubscriptionIsFoundFail() {
        Optional<Subscription> optionalSavedSubscription = subscriptionRepository.findById(-1L);

        assertFalse(optionalSavedSubscription.isPresent(), "Subscription не должен присутствовать в базе данных");
    }

    /**
     * Позитивный тест обновления сущности Subscription.
     */
    @Test
    public void whenUpdateSubscriptionThenSubscriptionIsUpdatedSuccess() {
        User subscriberUser = new User();
        subscriberUser.setUsername("Subscriber");
        subscriberUser.setEmail("subscriber@example.com");
        subscriberUser.setPasswordHash("12345");
        subscriberUser.setCreatedAt(LocalDateTime.now());

        User subscribedToUser = new User();
        subscribedToUser.setUsername("SubscribedTo");
        subscribedToUser.setEmail("subscribedTo@example.com");
        subscribedToUser.setPasswordHash("12345");
        subscribedToUser.setCreatedAt(LocalDateTime.now());

        userRepository.save(subscriberUser);
        userRepository.save(subscribedToUser);

        Optional<User> optionalSavedSubscriberUser = userRepository.findById(subscriberUser.getId());

        assertThat(optionalSavedSubscriberUser).isPresent();

        Optional<User> optionalSavedSubscribedToUser = userRepository.findById(subscribedToUser.getId());

        assertThat(optionalSavedSubscribedToUser).isPresent();

        Subscription subscription = new Subscription();
        subscription.setSubscriber(optionalSavedSubscriberUser.get());
        subscription.setSubscribedTo(optionalSavedSubscribedToUser.get());
        subscription.setCreatedAt(LocalDateTime.now());

        subscriptionRepository.save(subscription);

        Optional<Subscription> optionalSavedSubscription = subscriptionRepository.findById(subscription.getId());
        assertTrue(optionalSavedSubscription.isPresent(), "Сохранённый Subscription не должен быть пустым");

        subscriberUser.setUsername("UpdatedSubscriber");
        subscription.setSubscriber(subscriberUser);
        subscriptionRepository.save(subscription);

        Optional<Subscription> optionalUpdatedSavedSubscription = subscriptionRepository.findById(subscription.getId());

        assertTrue(optionalUpdatedSavedSubscription.isPresent(), "Обновлённый Subscription не должен быть пустым");

        assertEquals("UpdatedSubscriber", optionalUpdatedSavedSubscription.get().getSubscriber().getUsername(),
                "Username должен быть обновлён");
    }

    /**
     * Негативный тест обновления сущности Subscription.
     */
    @Test
    public void whenUpdateSubscriptionThenSubscriptionIsUpdatedFail() {
        User subscriberUser = new User();
        subscriberUser.setUsername("Subscriber");
        subscriberUser.setEmail("subscriber@example.com");
        subscriberUser.setPasswordHash("12345");
        subscriberUser.setCreatedAt(LocalDateTime.now());

        User subscribedToUser = new User();
        subscribedToUser.setUsername("SubscribedTo");
        subscribedToUser.setEmail("subscribedTo@example.com");
        subscribedToUser.setPasswordHash("12345");
        subscribedToUser.setCreatedAt(LocalDateTime.now());

        userRepository.save(subscriberUser);
        userRepository.save(subscribedToUser);

        Optional<User> optionalSavedSubscriberUser = userRepository.findById(subscriberUser.getId());

        assertThat(optionalSavedSubscriberUser).isPresent();

        Optional<User> optionalSavedSubscribedToUser = userRepository.findById(subscribedToUser.getId());

        assertThat(optionalSavedSubscribedToUser).isPresent();

        Subscription subscription = new Subscription();
        subscription.setSubscriber(optionalSavedSubscriberUser.get());
        subscription.setSubscribedTo(optionalSavedSubscribedToUser.get());
        subscription.setCreatedAt(LocalDateTime.now());

        subscriptionRepository.save(subscription);

        Optional<Subscription> optionalSavedSubscription = subscriptionRepository.findById(subscription.getId());
        assertTrue(optionalSavedSubscription.isPresent(), "Сохранённый Subscription не должен быть пустым");

        subscriberUser.setUsername("UpdatedSubscriber");
        subscription.setSubscriber(subscriberUser);
        subscriptionRepository.save(subscription);

        Optional<Subscription> optionalUpdatedSavedSubscription = subscriptionRepository.findById(subscription.getId());

        assertTrue(optionalUpdatedSavedSubscription.isPresent(), "Обновлённый Subscription не должен быть пустым");

        assertNotEquals("Subscriber", optionalUpdatedSavedSubscription.get().getSubscriber().getUsername(),
                "Username должен быть обновлён");
    }

    /**
     * Позитивный тест удаления сущности Subscription.
     */
    @Test
    public void whenDeleteSubscriptionByIdThenSubscriptionIsDeletedSuccess() {
        User subscriberUser = new User();
        subscriberUser.setUsername("Subscriber");
        subscriberUser.setEmail("subscriber@example.com");
        subscriberUser.setPasswordHash("12345");
        subscriberUser.setCreatedAt(LocalDateTime.now());

        User subscribedToUser = new User();
        subscribedToUser.setUsername("SubscribedTo");
        subscribedToUser.setEmail("subscribedTo@example.com");
        subscribedToUser.setPasswordHash("12345");
        subscribedToUser.setCreatedAt(LocalDateTime.now());

        userRepository.save(subscriberUser);
        userRepository.save(subscribedToUser);

        Optional<User> optionalSavedSubscriberUser = userRepository.findById(subscriberUser.getId());

        assertThat(optionalSavedSubscriberUser).isPresent();

        Optional<User> optionalSavedSubscribedToUser = userRepository.findById(subscribedToUser.getId());

        assertThat(optionalSavedSubscribedToUser).isPresent();

        Subscription subscription = new Subscription();
        subscription.setSubscriber(optionalSavedSubscriberUser.get());
        subscription.setSubscribedTo(optionalSavedSubscribedToUser.get());
        subscription.setCreatedAt(LocalDateTime.now());

        subscriptionRepository.save(subscription);

        Optional<Subscription> optionalSavedSubscription = subscriptionRepository.findById(subscription.getId());
        assertTrue(optionalSavedSubscription.isPresent(), "Сохранённый Subscription не должен быть пустым");

        subscriptionRepository.delete(optionalSavedSubscription.get());

        Optional<Subscription> optionalDeletedSubscription = subscriptionRepository.findById(
                optionalSavedSubscription.get().getId());

        assertFalse(optionalDeletedSubscription.isPresent(),
                "Subscription не должен присутствовать в базе данных.");
    }

    /**
     * Негативный тест удаления сущности Subscription.
     */
    @Test
    public void whenDeleteSubscriptionByIdThenSubscriptionIsDeletedFail() {
        Long nonExistentId = 999L;
        long initialCount = subscriptionRepository.count();

        assertDoesNotThrow(() -> subscriptionRepository.deleteById(nonExistentId));
        assertEquals(initialCount, subscriptionRepository.count());
    }
}