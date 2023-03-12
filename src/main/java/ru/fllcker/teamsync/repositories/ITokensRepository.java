package ru.fllcker.teamsync.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fllcker.teamsync.models.Token;

import java.util.Optional;

@Repository
public interface ITokensRepository extends JpaRepository<Token, Long> {
    Optional<Token> findByToken(String token);
}
