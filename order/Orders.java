package com.example.kursovaya_rabota.order;

import com.example.kursovaya_rabota.order.orderItem.OrderItem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Orders {
    @Id
    @SequenceGenerator(name = "order_sequence", sequenceName = "order_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_sequence")
    private Long id;
    private LocalDateTime localDateTime;
    @Enumerated(EnumType.STRING)
    private OrderStatus status;
    @OneToMany
    @JoinColumn
    private List<OrderItem> orderItems;


    public Orders(LocalDateTime localDateTime, OrderStatus status, List<OrderItem> orderItems) {
        this.localDateTime = localDateTime;
        this.status = status;
        this.orderItems = orderItems;
    }

    public double getPrice(){
        return orderItems.stream().
                mapToDouble(orderItem -> orderItem.getDish().getPrice() * orderItem.getCount()).reduce(0.0, Double::sum);
    }
}
