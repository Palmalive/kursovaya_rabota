package com.example.kursovaya_rabota.basket.basketItem;

import com.example.kursovaya_rabota.basket.Basket;
import com.example.kursovaya_rabota.menu.dish.Dish;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BasketItemRepository extends JpaRepository<BasketItem, Long> {



    Optional<BasketItem> findByDishAndBasket(Dish dish, Basket basket);

    @Modifying
    @Transactional
    @Query("delete from BasketItem as b where b.dish is null ")
    void deleteByBasketIdIsNull();

}
