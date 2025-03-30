package ru.job4j.socialmediaapi.repository.message;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;
import ru.job4j.socialmediaapi.entity.FriendRequest;
import ru.job4j.socialmediaapi.entity.Message;
import ru.job4j.socialmediaapi.entity.User;
import ru.job4j.socialmediaapi.repository.user.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@Transactional
class MessageRepositoryTest {

    @Autowired
    private MessageRepository messageRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userRepository.deleteAll();
        messageRepository.deleteAll();
    }

    /**
     * Позитивный тест сохранения сущности Message в БД.
     */
    @Test
    public void whenSaveMessage_ThenMessageIsSavedSuccess() {
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

        Message message = new Message();
        message.setSender(optionalSavedSenderUser.get());
        message.setReceiver(optionalSavedReceiverUser.get());
        message.setMessageText("Hello, World!");
        message.setCreatedAt(LocalDateTime.now());

        messageRepository.save(message);

        Optional<Message> optionalSavedMessage = messageRepository.findById(message.getId());

        assertThat(optionalSavedMessage).isPresent();
        assertThat(optionalSavedMessage.get().getSender()).isEqualTo(senderUser);
    }

    /**
     * Негативный тест сохранения сущности Message в БД.
     */
    @Test
    public void whenSaveMessage_ThenMessageIsSavedFail() {
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

        Message message = new Message();
        message.setSender(optionalSavedSenderUser.get());
        message.setReceiver(optionalSavedReceiverUser.get());
        message.setMessageText("Hello, World!");
        message.setCreatedAt(LocalDateTime.now());

        messageRepository.save(message);

        Optional<Message> optionalSavedMessage = messageRepository.findById(message.getId());

        assertThat(optionalSavedMessage).isPresent();
        assertNotEquals("Hello, World2!", optionalSavedMessage.get().getMessageText());
    }

    /**
     * Позитивный тест получения всех сущностей типа Message из БД.
     */
    @Test
    public void whenGetAllMessages_ThenMessagesAreFoundSuccess() {
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

        Message message1 = new Message();
        message1.setSender(optionalSavedSenderUser1.get());
        message1.setReceiver(optionalSavedReceiverUser.get());
        message1.setMessageText("Hello, World!");
        message1.setCreatedAt(LocalDateTime.now());

        Message message2 = new Message();
        message2.setSender(optionalSavedSenderUser2.get());
        message2.setReceiver(optionalSavedReceiverUser.get());
        message2.setMessageText("Hello, World 2!");
        message2.setCreatedAt(LocalDateTime.now());

        messageRepository.save(message1);
        messageRepository.save(message2);

        List<Message> foundMessages = messageRepository.findAll();

        assertThat(foundMessages).hasSize(2);
        assertThat(foundMessages).containsExactlyInAnyOrder(message1, message2);
    }

    /**
     * Негативный тест получения всех сущностей типа Message из БД.
     */
    @Test
    public void whenGetAllMessages_ThenMessagesAreFoundFail() {
        List<Message> foundMessages = messageRepository.findAll();

        assertTrue(foundMessages.isEmpty(), "Список сообщений должен быть пустым");
    }

    /**
     * Позитивный тест получения сущности типа Message по ID.
     */
    @Test
    public void whenGetMessageById_ThenMessageIsFoundSuccess() {
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

        Message message = new Message();
        message.setSender(optionalSavedSenderUser.get());
        message.setReceiver(optionalSavedReceiverUser.get());
        message.setMessageText("Hello, World!");
        message.setCreatedAt(LocalDateTime.now());

        messageRepository.save(message);

        Optional<Message> optionalSavedMessage = messageRepository.findById(message.getId());

        assertTrue(optionalSavedMessage.isPresent(), "Сохранённый Message не должен быть пустым");
        assertEquals(message.getMessageText(), optionalSavedMessage.get().getMessageText(), "MessageText должен совпадать");
    }

    /**
     * Негативный тест получения сущности типа Message по ID.
     */
    @Test
    public void whenGetMessageById_ThenMessageIsFoundFail() {
        Optional<Message> optionalMessage = messageRepository.findById(-1L);

        assertFalse(optionalMessage.isPresent(), "Message не должен быть пустым");
    }

    /**
     * Позитивный тест обновления сущности типа Message.
     */
    @Test
    public void whenUpdateMessage_ThenMessageIsUpdatedSuccess() {
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

        Message message = new Message();
        message.setSender(optionalSavedSenderUser.get());
        message.setReceiver(optionalSavedReceiverUser.get());
        message.setMessageText("Hello, World!");
        message.setCreatedAt(LocalDateTime.now());

        messageRepository.save(message);

        Optional<Message> optionalSavedMessage = messageRepository.findById(message.getId());
        assertTrue(optionalSavedMessage.isPresent(), "Сохранённый Message не должен быть пустым");

        message.setMessageText("Hello, World 2!");
        messageRepository.save(message);

        Optional<Message> updatedMessage = messageRepository.findById(message.getId());
        assertTrue(updatedMessage.isPresent(), "Обновлённый Message не должен быть пустым");

        assertEquals("Hello, World 2!", updatedMessage.get().getMessageText(),
                "MessageText должен быть обновлен");
    }

    /**
     * Негативный тест обновления сущности типа Message.
     */
    @Test
    public void whenUpdateMessage_ThenMessageIsUpdatedFail() {
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

        Message message = new Message();
        message.setSender(optionalSavedSenderUser.get());
        message.setReceiver(optionalSavedReceiverUser.get());
        message.setMessageText("Hello, World!");
        message.setCreatedAt(LocalDateTime.now());

        messageRepository.save(message);

        Optional<Message> optionalSavedMessage = messageRepository.findById(message.getId());
        assertTrue(optionalSavedMessage.isPresent(), "Сохранённый Message не должен быть пустым");

        message.setMessageText("Hello, World 2!");
        messageRepository.save(message);

        Optional<Message> updatedMessage = messageRepository.findById(message.getId());
        assertTrue(updatedMessage.isPresent(), "Обновлённый Message не должен быть пустым");

        assertNotEquals("Hello, World!", updatedMessage.get().getMessageText(),
                "MessageText должен быть обновлен");
    }

    /**
     * Позитивный тест удаления сущности типа Message.
     */
    @Test
    public void whenDeleteMessage_ThenMessageIsDeletedSuccess() {
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

        Message message = new Message();
        message.setSender(optionalSavedSenderUser.get());
        message.setReceiver(optionalSavedReceiverUser.get());
        message.setMessageText("Hello, World!");
        message.setCreatedAt(LocalDateTime.now());

        messageRepository.save(message);

        Optional<Message> optionalSavedMessage = messageRepository.findById(message.getId());
        assertTrue(optionalSavedMessage.isPresent(), "Сохранённый Message не должен быть пустым");

        messageRepository.delete(optionalSavedMessage.get());

        Optional<Message> optionalDeletedMessage = messageRepository.findById(
                optionalSavedMessage.get().getId());

        assertFalse(optionalDeletedMessage.isPresent(),
                "Message не должен присутствовать в базе данных");
    }

    /**
     * Негативный тест удаления сущности типа Message.
     */
    @Test
    public void whenDeleteMessage_ThenMessageIsDeletedFail() {
        Long nonExistentId = 999L;
        long initialCount = messageRepository.count();

        assertDoesNotThrow(() -> messageRepository.deleteById(nonExistentId));
        assertEquals(initialCount, messageRepository.count());
    }
}