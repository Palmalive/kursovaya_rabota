package com.example.kursovaya_rabota.order.orderItem;


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
public class OrderItem {
    @Id
    @SequenceGenerator(name = "order_item_sequence", sequenceName = "order_item_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_item_sequence")
    private Long id;
    @ManyToOne
    private Dish dish;
    private int count;

    public OrderItem(Dish dish, int count) {
        this.dish = dish;
        this.count = count;
    }
}
