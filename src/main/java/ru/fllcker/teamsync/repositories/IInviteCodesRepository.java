package ru.fllcker.teamsync.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.fllcker.teamsync.models.InviteCode;

import java.util.Optional;

@Repository
public interface IInviteCodesRepository extends JpaRepository<InviteCode, Long> {
    @Transactional
    @Modifying
    @Query("update InviteCode i set i.activationsLeft = ?1 where i.id = ?2")
    void updateActivationsLeftById(Integer activationsLeft, Long id);
    Optional<InviteCode> findByValue(String value);
}
