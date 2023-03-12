package ru.fllcker.teamsync.services.channels;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.fllcker.teamsync.dto.channels.NewChannelDto;
import ru.fllcker.teamsync.models.Channel;
import ru.fllcker.teamsync.models.Space;
import ru.fllcker.teamsync.models.User;
import ru.fllcker.teamsync.repositories.IChannelsRepository;
import ru.fllcker.teamsync.services.spaces.SpacesService;
import ru.fllcker.teamsync.services.users.UsersService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ChannelsService {
    private final IChannelsRepository channelsRepository;
    private final UsersService usersService;
    private final SpacesService spacesService;


    public Channel create(String accessEmail, NewChannelDto newChannelDto) {
        User user = usersService.findByEmail(accessEmail);
        Space space = spacesService.findById(newChannelDto.getSpaceId());

        if (!space.getOwner().getId().equals(user.getId()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No access to control this space!");

        if (space.getChannels().stream().anyMatch(c -> c.getTitle().equals(newChannelDto.getTitle())))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Channel with this name already exists!");

        Channel channel = new Channel(newChannelDto.getTitle());
        channel.setSpace(space);
        channelsRepository.save(channel);

        return channel;
    }

    public Channel findById(Long channelId) {
        return channelsRepository.findById(channelId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Channel not found!"));
    }

    public List<Channel> findBySpace(String accessEmail, Long spaceId) {
        User user = usersService.findByEmail(accessEmail);
        Space space = spacesService.findById(spaceId);

        if (!space.getMembers().contains(user))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No access to this space!");

        return channelsRepository.findChannelsBySpace(space);
    }
}
