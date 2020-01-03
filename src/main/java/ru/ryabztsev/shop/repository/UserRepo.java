package ru.ryabztsev.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ryabztsev.shop.entity.User;

import java.util.Optional;

public interface UserRepo extends JpaRepository<User, Long> {
    User findByUsername(String username);
    Optional<User> findById(Long userID);
}
