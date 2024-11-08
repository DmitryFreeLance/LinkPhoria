package ru.linkphoria.linkphoriaproject.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.linkphoria.linkphoriaproject.models.User;

public interface UserRepository  extends JpaRepository<User, Long> {
    User findByEmail(String email);
}
