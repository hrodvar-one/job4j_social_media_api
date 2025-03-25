package ru.job4j.socialmediaapi.repository.user;

import org.springframework.data.repository.ListCrudRepository;
import ru.job4j.socialmediaapi.entity.User;

public interface UserRepository extends ListCrudRepository<User, Long> {
}
