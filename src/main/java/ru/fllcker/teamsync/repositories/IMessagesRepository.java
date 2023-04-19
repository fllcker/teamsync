package ru.fllcker.teamsync.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fllcker.teamsync.models.Channel;
import ru.fllcker.teamsync.models.Message;

import java.util.List;

@Repository
public interface IMessagesRepository extends JpaRepository<Message, Long> {
    List<Message> findMessagesByChannel(Channel channel);

    Page<Message> findMessagesByChannel(Channel channel,
                                        Pageable pageable);
}
