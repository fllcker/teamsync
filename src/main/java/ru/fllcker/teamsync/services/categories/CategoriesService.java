package ru.fllcker.teamsync.services.categories;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.fllcker.teamsync.dto.categories.NewCategoryDto;
import ru.fllcker.teamsync.models.Category;
import ru.fllcker.teamsync.models.Space;
import ru.fllcker.teamsync.models.User;
import ru.fllcker.teamsync.repositories.ICategoriesRepository;
import ru.fllcker.teamsync.services.auth.AuthService;
import ru.fllcker.teamsync.services.spaces.SpacesService;
import ru.fllcker.teamsync.services.users.UsersService;

@Service
@Transactional
@RequiredArgsConstructor
public class CategoriesService {
    private final ICategoriesRepository categoriesRepository;
    private final AuthService authService;
    private final UsersService usersService;
    private final SpacesService spacesService;

    public Category create(NewCategoryDto newCategoryDto) {
        User user = usersService.findByEmail(authService.getAuthInfo().getEmail());
        Space space = spacesService.findById(newCategoryDto.getSpaceId());

        if (!space.getMembers().contains(user))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No access!");

        Category category = new Category(newCategoryDto.getTitle());
        category.setSpace(space);
        return categoriesRepository.save(category);
    }

    public Category findById(Long id) {
        return categoriesRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Category not found!"));
    }
}
