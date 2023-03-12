package ru.fllcker.teamsync.services.messages;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.fllcker.teamsync.dto.messages.NewMessageDto;
import ru.fllcker.teamsync.models.Channel;
import ru.fllcker.teamsync.models.Message;
import ru.fllcker.teamsync.models.User;
import ru.fllcker.teamsync.repositories.IMessagesRepository;
import ru.fllcker.teamsync.services.channels.ChannelsService;
import ru.fllcker.teamsync.services.users.UsersService;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class MessagesService {
    private final IMessagesRepository messagesRepository;
    private final UsersService usersService;
    private final ChannelsService channelsService;

    public Message create(String accessEmail, NewMessageDto newMessageDto) {
        User user = usersService.findByEmail(accessEmail);
        Channel channel = channelsService.findById(newMessageDto.getChannelId());

        if (!channel.getSpace().getMembers().contains(user))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No access to this space!");

        Message message = new Message(newMessageDto.getValue());
        message.setChannel(channel);
        message.setOwner(user);
        messagesRepository.save(message);

        return message;
    }

    public Message findById(Long messageId) {
        return messagesRepository.findById(messageId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Message not found!"));
    }

    public List<Message> findByChannel(String accessEmail, Long channelId) {
        User user = usersService.findByEmail(accessEmail);
        Channel channel = channelsService.findById(channelId);

        if (!channel.getSpace().getMembers().contains(user))
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No access to this space!");

        return messagesRepository.findMessagesByChannel(channel);
    }
}