package ru.fllcker.teamsync.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fllcker.teamsync.models.Space;

@Repository
public interface ISpacesRepository extends JpaRepository<Space, Long> {
}
