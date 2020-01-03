package ru.ryabztsev.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ryabztsev.shop.entity.Category;

import java.util.Optional;

public interface CategoryRepo extends JpaRepository<Category, Long> {
    Category findByTitle(String title);
    Optional<Category> findById(Long id);
}
