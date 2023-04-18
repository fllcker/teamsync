package ru.fllcker.teamsync.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fllcker.teamsync.models.InviteCode;

import java.util.Optional;

@Repository
public interface IInviteCodesRepository extends JpaRepository<InviteCode, Long> {
    Optional<InviteCode> findByValue(String value);
}
