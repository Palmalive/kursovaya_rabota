package com.example.kursovaya_rabota.warehouse.warehouseItem;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@Data
public class WarehouseItem {
    @Id
    @SequenceGenerator(name = "warehouse_item_sequence", sequenceName = "warehouse_item_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "warehouse_item_sequence")
    private Long id;
    private String name;
    private double weight;;

    public void increaseWeight(double weight) {
        this.weight += weight;
    }

    public void decreaseWeight(double weight){
        this.weight -=weight;
    }
}
