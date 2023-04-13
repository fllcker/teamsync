package ru.fllcker.teamsync.dto.categories;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class NewCategoryDto {
    private Long spaceId;
    private String title;
}
