package ru.fllcker.teamsync.services.users;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.fllcker.teamsync.models.User;
import ru.fllcker.teamsync.repositories.IUsersRepository;

@Service
@Transactional
@RequiredArgsConstructor
public class UsersService {
    private final IUsersRepository usersRepository;

    public User findByEmail(String email) {
        return usersRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found!"));
    }

    public boolean existsByEmail(String email) {
        return usersRepository.existsByEmail(email);
    }

    public User create(User user) {
        usersRepository.save(user);
        return user;
    }
}
