package ru.fllcker.teamsync.events.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.fllcker.teamsync.models.Space;
import ru.fllcker.teamsync.models.User;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserJoinedEvent {
    private User user;
    private Space space;
}
