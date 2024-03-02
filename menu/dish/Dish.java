package com.example.kursovaya_rabota.menu.dish;

import com.example.kursovaya_rabota.basket.basketItem.BasketItem;
import com.example.kursovaya_rabota.menu.dish.ingredient.Ingredient;
import com.example.kursovaya_rabota.menu.image.Image;

import com.example.kursovaya_rabota.menu.segment.Segment;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
//TODO убрать
@Slf4j
public class Dish {

    @Id
    @SequenceGenerator(name = "dish_sequence", sequenceName = "dish_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "dish_sequence")
    private Long id;
    private String name;
    private String description;
    private double price;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "dish")
    private List<Ingredient> ingredients;
    @ManyToOne(cascade = CascadeType.REFRESH, fetch = FetchType.EAGER)
    @JoinColumn
    private Segment segment;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "dish")
    private List<Image> images;
    private Long previewImageId;
    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "dish")
    private List<BasketItem> basketItems;
    private boolean isDeletedFromMenu;

    public void addImageToDish(Image image) {
        if (images == null){
            images = new ArrayList<>();
        }
        image.setDish(this);
        images.add(image);
    }

    public void addIngredientToDish(Ingredient ingredient){
        if(ingredients == null){
            ingredients = new ArrayList<>();
        }
        ingredients.add(ingredient);
    }

    public void setPreviewImageId(){
        previewImageId = images.get(0).getId();
    }

    public String getStringIngredients(){
        return ingredients.stream().map(Ingredient::getName).collect(Collectors.joining(", "));
    }

    @Override
    public String toString() {
        return "Dish{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", price=" + price +
                ", segment=" + segment +
                ", previewImageId=" + previewImageId +
                ", isDeletedFromMenu=" + isDeletedFromMenu +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Dish dish = (Dish) o;
        return Double.compare(price, dish.price) == 0 && isDeletedFromMenu == dish.isDeletedFromMenu && Objects.equals(id, dish.id) && Objects.equals(name, dish.name) && Objects.equals(description, dish.description) && Objects.equals(segment, dish.segment) && Objects.equals(previewImageId, dish.previewImageId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, description, price, segment, previewImageId, isDeletedFromMenu);
    }
}
