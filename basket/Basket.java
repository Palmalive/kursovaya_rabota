package com.example.kursovaya_rabota.basket;


import com.example.kursovaya_rabota.basket.basketItem.BasketItem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Basket{

    @Id
    @SequenceGenerator(name = "basket_sequence", sequenceName = "basket_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "basket_sequence")
    private Long id;
    private Long userId;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn
    private List<BasketItem> basketItems;

    public Basket(Long appUserId) {
        this.userId = appUserId;
    }

    public boolean isNotEmpty(){
        return !basketItems.isEmpty();
    }

    public void addBasketItemToBasket(BasketItem basketItem){
        if (basketItems == null){
            basketItems = new ArrayList<>();
        }
        basketItems.add(basketItem);
    }

    public void clearBasketItems(){
        if (basketItems != null) {
            basketItems.clear();
        }
    }

    public void deleteBasketItem(BasketItem item){
        basketItems.remove(item);
    }

}
