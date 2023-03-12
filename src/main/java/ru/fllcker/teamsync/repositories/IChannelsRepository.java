package ru.fllcker.teamsync.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fllcker.teamsync.models.Channel;

@Repository
public interface IChannelsRepository extends JpaRepository<Channel, Long> {
}
