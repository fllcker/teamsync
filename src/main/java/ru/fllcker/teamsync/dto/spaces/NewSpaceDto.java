package ru.fllcker.teamsync.dto.spaces;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NewSpaceDto {
    @Size(min = 3, max = 16, message = "Space title should be greater than 3 letters and less than 16 letters!")
    private String title;
}
