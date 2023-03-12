package ru.fllcker.teamsync.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fllcker.teamsync.models.Channel;
import ru.fllcker.teamsync.models.Space;

import java.util.List;

@Repository
public interface IChannelsRepository extends JpaRepository<Channel, Long> {
    List<Channel> findChannelsBySpace(Space space);
}
