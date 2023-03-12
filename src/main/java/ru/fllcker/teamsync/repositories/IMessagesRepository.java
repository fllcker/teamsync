package ru.fllcker.teamsync.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fllcker.teamsync.models.Message;

@Repository
public interface IMessagesRepository extends JpaRepository<Message, Long> {
}
