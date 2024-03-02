package com.example.kursovaya_rabota.basket;

import com.example.kursovaya_rabota.appUser.AppUserService;
import com.example.kursovaya_rabota.basket.basketItem.BasketItem;
import com.example.kursovaya_rabota.basket.basketItem.BasketItemService;
import com.example.kursovaya_rabota.menu.dish.Dish;
import com.example.kursovaya_rabota.menu.dish.DishService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class BasketService {
    private final BasketRepository basketRepository;
    private final AppUserService appUserService;
    private final BasketItemService basketItemService;
    private final DishService dishService;

    public void addDishToBasket(Long dishId, int count, Principal principal){
        Long appUserId = appUserService.getUserByPrincipal(principal).getId();
        Basket basket = getBasketByUserId(appUserId);
        if(basket == null){
            basket = new Basket(appUserId);
        }
        Dish dish = dishService.getDishById(dishId);

        BasketItem basketItem = basketItemService.findBasketItemByDish(dish, basket);
        if (basketItem != null) {
            basketItem.increaseCount(count);
        } else{
            basketItem = new BasketItem(dish, count, basket);
            basket.addBasketItemToBasket(basketItem);
        }

        basket.addBasketItemToBasket(basketItem);
        basketRepository.save(basket);
        log.info("BasketItem with id {} was added to basket with id {}", basketItem.getId(), basket.getId());
    }

    public void clearBasket(Long basketId){
        Basket basket = basketRepository.findById(basketId).orElse( null);

        if (basket != null) {
            for (BasketItem item : basket.getBasketItems()) {
                item.nullReferences();
            }
            basket.clearBasketItems();
            basketItemService.deleteAllItemsWithNullBasketId();
            basketRepository.save(basket);
        }
    }

    public Basket getBasketByUserId(Long id){
        return basketRepository.findByUserId(id).orElse(null);
    }


    public Basket getBasketByPrincipal(Principal principal) {
        Long id = appUserService.getUserByPrincipal(principal).getId();
        Basket basket = basketRepository.findByUserId(id).orElse(null);
        return Objects.requireNonNullElseGet(basket, () -> new Basket(id));
    }

    public void saveBasket(Basket basket){
        basketRepository.save(basket);
    }

}
