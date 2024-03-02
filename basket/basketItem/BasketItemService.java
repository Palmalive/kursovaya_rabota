package com.example.kursovaya_rabota.basket.basketItem;

import com.example.kursovaya_rabota.basket.Basket;
import com.example.kursovaya_rabota.menu.dish.Dish;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class BasketItemService {
    private final BasketItemRepository basketItemRepository;

    public BasketItem findBasketItemByDish(Dish dish, Basket basket){
        return basketItemRepository.findByDishAndBasket(dish, basket).orElse(null);
    }

    public void delete(BasketItem basketItem) {
        basketItemRepository.delete(basketItem);
    }

    public void deleteAllItemsWithNullBasketId() {
        basketItemRepository.deleteByBasketIdIsNull();
    }
}
