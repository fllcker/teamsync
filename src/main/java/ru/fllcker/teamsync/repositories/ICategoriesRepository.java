package ru.fllcker.teamsync.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.fllcker.teamsync.models.Category;

@Repository
public interface ICategoriesRepository extends JpaRepository<Category, Long> {
}
