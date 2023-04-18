package ru.fllcker.teamsync.dto.invitations;

import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NewInviteCode {
    @Min(value = 1, message = "The number of activations must be greater than 0")
    private Integer activationsLeft;

    @Min(value = 1, message = "Action time must be greater than 0")
    private Integer expirationTimeInSeconds;

    private Long spaceId;
}
