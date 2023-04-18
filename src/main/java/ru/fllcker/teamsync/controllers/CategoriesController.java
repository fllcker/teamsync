package ru.fllcker.teamsync.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.fllcker.teamsync.dto.categories.NewCategoryDto;
import ru.fllcker.teamsync.models.Category;
import ru.fllcker.teamsync.services.categories.CategoriesService;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/categories")
public class CategoriesController {
    private final CategoriesService categoriesService;


    @PostMapping
    public ResponseEntity<Category> create(NewCategoryDto newCategoryDto) {
        Category category = categoriesService.create(newCategoryDto);
        return ResponseEntity.ok(category);
    }

    @GetMapping("space/{spaceId}")
    public ResponseEntity<List<Category>> findBySpace(@PathVariable Long spaceId) {
        List<Category> categories = categoriesService.findBySpace(spaceId);
        return ResponseEntity.ok(categories);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        categoriesService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}
