package ru.fllcker.teamsync.services.spaces;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.fllcker.teamsync.dto.spaces.NewSpaceDto;
import ru.fllcker.teamsync.models.Space;
import ru.fllcker.teamsync.models.User;
import ru.fllcker.teamsync.repositories.ISpacesRepository;
import ru.fllcker.teamsync.services.users.UsersService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SpacesService {
    private final ISpacesRepository spacesRepository;
    private final UsersService usersService;

    public Space create(String accessEmail, NewSpaceDto newSpaceDto) {
        Space space = new Space(newSpaceDto.getTitle());

        User owner = usersService.findByEmail(accessEmail);
        space.setOwner(owner);
        space.setMembers(List.of(owner));

        spacesRepository.save(space);
        return space;
    }

    public Space findById(Long spaceId) {
        return spacesRepository.findById(spaceId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Space not found!"));
    }

    public List<User> findMembers(String accessEmail, Long spaceId) {
        Space space = this.findById(spaceId);

        User user = usersService.findByEmail(accessEmail);
        if (!space.getMembers().contains(user))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No access to this space!");

        return space.getMembers();
    }
}