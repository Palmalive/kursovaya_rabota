package com.example.kursovaya_rabota.warehouse.warehouseItem;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface WarehouseItemRepository extends JpaRepository<WarehouseItem, Long> {
    Optional<WarehouseItem> findByName(String name);
}
