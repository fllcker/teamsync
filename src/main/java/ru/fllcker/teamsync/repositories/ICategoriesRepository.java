package ru.fllcker.teamsync.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fllcker.teamsync.models.Category;
import ru.fllcker.teamsync.models.Space;

import java.util.List;

@Repository
public interface ICategoriesRepository extends JpaRepository<Category, Long> {
    List<Category> findCategoriesBySpace(Space space);
}
