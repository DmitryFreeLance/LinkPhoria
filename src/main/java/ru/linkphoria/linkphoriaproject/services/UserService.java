package ru.linkphoria.linkphoriaproject.services;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.linkphoria.linkphoriaproject.models.User;
import ru.linkphoria.linkphoriaproject.models.enums.Role;
import ru.linkphoria.linkphoriaproject.repositories.UserRepository;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public boolean createUser(User user) {
        String email= user.getEmail();

        if(userRepository.findByEmail(email) != null) {return false;
        }

        user.setActive(true);
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(Role.ROLE_USER);
        userRepository.save(user);
        log.info("Saving new user: {}", email);
        return true;
    }
}
