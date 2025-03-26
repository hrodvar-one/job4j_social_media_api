package ru.job4j.socialmediaapi.repository.user;

import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;
import ru.job4j.socialmediaapi.entity.User;

@Repository
public interface UserRepository extends ListCrudRepository<User, Long> {
}
