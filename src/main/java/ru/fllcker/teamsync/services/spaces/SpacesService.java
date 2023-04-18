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
import ru.fllcker.teamsync.services.auth.AuthService;
import ru.fllcker.teamsync.services.channels.ChannelsService;
import ru.fllcker.teamsync.services.users.UsersService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class SpacesService {
    private final ISpacesRepository spacesRepository;
    private final UsersService usersService;
    private final AuthService authService;

    public Space create(NewSpaceDto newSpaceDto) {
        Space space = new Space(newSpaceDto.getTitle());

        User owner = usersService.findByEmail(authService.getAuthInfo().getEmail());
        space.setOwner(owner);
        space.setMembers(List.of(owner));
        space.setChannels(ChannelsService.generateDefaultChannels());

        spacesRepository.save(space);
        return space;
    }

    public Space findById(Long spaceId) {
        return spacesRepository.findById(spaceId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Space not found!"));
    }

    public List<User> findMembers(Long spaceId) {
        Space space = this.findById(spaceId);

        User user = usersService.findByEmail(authService.getAuthInfo().getEmail());
        if (!space.getMembers().contains(user))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No access to this space!");

        return space.getMembers();
    }

    public List<Space> findByMember() {
        User user = usersService.findByEmail(authService.getAuthInfo().getEmail());
        return spacesRepository.findSpacesByMembersContaining(user);
    }

    public void addMember(Long spaceId, User user) {
        Space space = findById(spaceId);
        space.getMembers().add(user);
        spacesRepository.save(space);
    }
}
