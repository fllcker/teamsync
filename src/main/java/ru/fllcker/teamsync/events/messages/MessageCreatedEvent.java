package ru.fllcker.teamsync.events.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.fllcker.teamsync.models.Message;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageCreatedEvent {
    private Message message;
}
