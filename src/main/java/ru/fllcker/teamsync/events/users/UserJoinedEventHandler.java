package ru.fllcker.teamsync.events.users;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import ru.fllcker.teamsync.models.Channel;
import ru.fllcker.teamsync.models.Message;
import ru.fllcker.teamsync.models.Space;
import ru.fllcker.teamsync.models.User;
import ru.fllcker.teamsync.services.channels.ChannelsService;
import ru.fllcker.teamsync.services.messages.MessagesService;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class UserJoinedEventHandler {
    private final ChannelsService channelsService;
    private final MessagesService messagesService;

    @EventListener
    public void handleUserJoinedEvent(UserJoinedEvent event) {
        User user = event.getUser();
        Space space = event.getSpace();

        Optional<Channel> newsChannel = channelsService.getSpecialChannel(space.getId(), "news");
        if (newsChannel.isPresent()) {
            Message message = new Message(String.format("%s %s (%s) joined the space!", user.getFirstName(), user.getLastName(), user.getEmail()));
            message.setChannel(newsChannel.get());
            messagesService.create(message);
        }
    }
}
