package com.example.kursovaya_rabota.basket.basketItem;

import com.example.kursovaya_rabota.basket.Basket;
import com.example.kursovaya_rabota.menu.dish.Dish;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BasketItem {
    @Id
    @SequenceGenerator(name = "basket_item_sequence", sequenceName = "basket_item_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "basket_item_sequence")
    private Long id;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Dish dish;
    private int count;
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Basket basket;

    public BasketItem(Dish dish, int count) {
        this.dish = dish;
        this.count = count;
    }

    public BasketItem(Dish dish, int count, Basket basket) {
        this.dish = dish;
        this.count = count;
        this.basket = basket;
    }

    public void increaseCount(int count){
        this.count +=count;
    }

    public void nullReferences(){
        basket = null;
        dish = null;
    }
}


