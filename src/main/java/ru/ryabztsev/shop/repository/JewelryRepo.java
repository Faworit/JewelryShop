package ru.ryabztsev.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ryabztsev.shop.entity.Category;
import ru.ryabztsev.shop.entity.Jewelry;

import java.util.List;
import java.util.Optional;

public interface JewelryRepo extends JpaRepository<Jewelry, Long> {
    List<Jewelry> findAll();
    List<Jewelry> findAllByCategory(Category category);

    Optional<Jewelry> findById(Long jewelryId);
}
