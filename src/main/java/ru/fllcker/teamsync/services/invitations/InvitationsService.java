package ru.fllcker.teamsync.services.invitations;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.fllcker.teamsync.dto.invitations.NewInviteCode;
import ru.fllcker.teamsync.models.InviteCode;
import ru.fllcker.teamsync.models.Space;
import ru.fllcker.teamsync.models.User;
import ru.fllcker.teamsync.repositories.IInviteCodesRepository;
import ru.fllcker.teamsync.services.auth.AuthService;
import ru.fllcker.teamsync.services.spaces.SpacesService;
import ru.fllcker.teamsync.services.users.UsersService;
import ru.fllcker.teamsync.utils.RandomUtils;

import java.time.LocalDateTime;

@Service
@Transactional
@RequiredArgsConstructor
public class InvitationsService {
    private final IInviteCodesRepository inviteCodesRepository;
    private final AuthService authService;
    private final UsersService usersService;
    private final SpacesService spacesService;

    public InviteCode create(NewInviteCode newInviteCode) {
        User creator = usersService.findByEmail(authService.getAuthInfo().getEmail());
        Space space = spacesService.findById(newInviteCode.getSpaceId());

        if (!space.getMembers().contains(creator))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No access!");

        LocalDateTime expirationTime = LocalDateTime.now().plusSeconds(newInviteCode.getExpirationTimeInSeconds());
        String value = RandomUtils.generate(9);

        InviteCode inviteCode = InviteCode.builder()
                .value(value)
                .activationsLeft(newInviteCode.getActivationsLeft())
                .expirationTime(expirationTime)
                .space(space)
                .creator(creator).build();

        inviteCodesRepository.save(inviteCode);
        return inviteCode;
    }

    public InviteCode activate(String value) {
        InviteCode inviteCode = findByValue(value);

        User user = usersService.findByEmail(authService.getAuthInfo().getEmail());

        if (inviteCode.getActivationsLeft() <= 0)
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Number of activations exceeded!");

        if (inviteCode.getExpirationTime().isBefore(LocalDateTime.now()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Code is not valid!");

        if (inviteCode.getSpace().getMembers().contains(user))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "You are already in this space!");

        inviteCodesRepository.updateActivationsLeftById(inviteCode.getActivationsLeft() - 1, inviteCode.getId());

        spacesService.addMember(inviteCode.getSpace().getId(), user);
        return inviteCode;
    }

    public InviteCode findByValue(String value) {
        return inviteCodesRepository.findByValue(value)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Code not valid or not found"));
    }
}
