package ru.fllcker.teamsync.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.fllcker.teamsync.models.Category;
import ru.fllcker.teamsync.models.Channel;
import ru.fllcker.teamsync.models.Space;

import java.util.List;

@Repository
public interface IChannelsRepository extends JpaRepository<Channel, Long> {
    @Transactional
    @Modifying
    @Query("update Channel c set c.category = ?1 where c.id = ?2")
    void updateCategoryById(Category category, Long id);
    List<Channel> findChannelsBySpace(Space space);
}
