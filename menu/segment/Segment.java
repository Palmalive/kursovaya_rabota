package com.example.kursovaya_rabota.menu.segment;

import com.example.kursovaya_rabota.menu.dish.Dish;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Segment {
    @Id
    @SequenceGenerator(name = "segment_sequence", sequenceName = "segment_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "segment_sequence")
    private Long id;
    private String name;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "segment")
    private List<Dish> dishes;

    public void addDish(Dish dish){
        dishes.add(dish);
    }

    public void deleteDish(Dish dish){
        dishes.remove(dish);
    }

    @Override
    public String toString() {
        return "Segment{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
