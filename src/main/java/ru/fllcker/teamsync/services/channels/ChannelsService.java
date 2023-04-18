package ru.fllcker.teamsync.services.channels;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.fllcker.teamsync.dto.channels.NewChannelDto;
import ru.fllcker.teamsync.models.Category;
import ru.fllcker.teamsync.models.Channel;
import ru.fllcker.teamsync.models.Space;
import ru.fllcker.teamsync.models.User;
import ru.fllcker.teamsync.repositories.IChannelsRepository;
import ru.fllcker.teamsync.services.auth.AuthService;
import ru.fllcker.teamsync.services.categories.CategoriesService;
import ru.fllcker.teamsync.services.spaces.SpacesService;
import ru.fllcker.teamsync.services.users.UsersService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class ChannelsService {
    private final IChannelsRepository channelsRepository;
    private final UsersService usersService;
    private final SpacesService spacesService;
    private final CategoriesService categoriesService;
    private final AuthService authService;


    public Channel create(NewChannelDto newChannelDto) {
        User user = usersService.findByEmail(authService.getAuthInfo().getEmail());
        Space space = spacesService.findById(newChannelDto.getSpaceId());

        if (!space.getOwner().getId().equals(user.getId()))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No access to control this space!");

        Channel channel = new Channel(newChannelDto.getTitle());
        channel.setSpace(space);
        channelsRepository.save(channel);

        return channel;
    }

    public Channel findById(Long channelId) {
        return channelsRepository.findById(channelId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Channel not found!"));
    }

    public List<Channel> findBySpace(Long spaceId) {
        User user = usersService.findByEmail(authService.getAuthInfo().getEmail());
        Space space = spacesService.findById(spaceId);

        if (!space.getMembers().contains(user))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No access to this space!");

        return channelsRepository.findChannelsBySpace(space);
    }

    public Map<Long, List<Channel>> findBySpaceAndGrouped(Long spaceId) {
        List<Channel> channels = findBySpace(spaceId);

        return channels.stream()
                .collect(Collectors.groupingBy(
                        channel -> channel.getCategory() != null ? channel.getCategory().getId() : -1L,
                        Collectors.toList()));
    }

    public Channel updateCategory(Long channelId, Long categoryId) {
        Channel channel = findById(channelId);
        Category category = categoriesService.findById(categoryId);
        User user = usersService.findByEmail(authService.getAuthInfo().getEmail());

        if (!channel.getSpace().getMembers().contains(user))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No access!");

        channelsRepository.updateCategoryById(category, channel.getId());
        channel.setCategory(category);
        return channel;
    }
}
