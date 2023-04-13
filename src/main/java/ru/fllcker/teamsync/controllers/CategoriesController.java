package ru.fllcker.teamsync.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.fllcker.teamsync.dto.categories.NewCategoryDto;
import ru.fllcker.teamsync.models.Category;
import ru.fllcker.teamsync.services.categories.CategoriesService;

@RestController
@RequiredArgsConstructor
@RequestMapping("categories")
public class CategoriesController {
    private final CategoriesService categoriesService;


    public ResponseEntity<Category> create(NewCategoryDto newCategoryDto) {
        Category category = categoriesService.create(newCategoryDto);
        return ResponseEntity.ok(category);
    }
}
