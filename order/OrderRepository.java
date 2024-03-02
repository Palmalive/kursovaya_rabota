package com.example.kursovaya_rabota.order;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface OrderRepository extends JpaRepository<Orders, Long> {

    List<Orders> findAllByStatus(OrderStatus orderStatus);

    List<Orders> findAllByLocalDateTimeGreaterThan(LocalDateTime localDateTime);
}
