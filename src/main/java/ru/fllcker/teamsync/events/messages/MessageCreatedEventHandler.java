package ru.fllcker.teamsync.events.messages;

import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import ru.fllcker.teamsync.models.Channel;
import ru.fllcker.teamsync.models.Message;

@Component
@RequiredArgsConstructor
public class MessageCreatedEventHandler {
    private final SimpMessagingTemplate messagingTemplate;

    @EventListener
    public void handleMessageCreatedEvent(MessageCreatedEvent event) {
        Message message = event.getMessage();
        Channel channel = message.getChannel();

        messagingTemplate.convertAndSend("/topic/channel-" + channel.getId(), message);
    }
}
