package ru.fllcker.teamsync.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fllcker.teamsync.models.Space;
import ru.fllcker.teamsync.models.User;

import java.util.Collection;
import java.util.List;

@Repository
public interface ISpacesRepository extends JpaRepository<Space, Long> {
    List<Space> findSpacesByMembersIn(Collection<List<User>> members);

    List<Space> findSpacesByMembersContaining(User user);


}
