package ru.fllcker.teamsync.dto.messages;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NewMessageDto {
    private Long channelId;

    @Size(min = 1, max = 128, message = "Channel title should be greater than 1 letters and less than 128 letters!")
    private String value;
}
