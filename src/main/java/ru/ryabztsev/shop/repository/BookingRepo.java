package ru.ryabztsev.shop.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.ryabztsev.shop.entity.Booking;

public interface BookingRepo extends JpaRepository<Booking, Long> {
}
