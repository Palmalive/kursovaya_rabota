package com.example.kursovaya_rabota.menu.dish.ingredient;

import com.example.kursovaya_rabota.menu.dish.Dish;
import com.example.kursovaya_rabota.menu.dish.DishService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class IngredientService {
    private final IngredientRepository ingredientRepository;
    private final DishService dishService;

    public void saveIngredient(Ingredient ingredient, Long dishId){
        Dish dish = dishService.getDishById(dishId);
        dish.addIngredientToDish(ingredient);
        ingredient.setDish(dish);
        dishService.saveDish(dish);
        log.info("Ingredient with id {} was saved", ingredient.getId());
    }

    public void deleteIngredient(Long id){
        ingredientRepository.deleteById(id);
        log.info("Ingredient with id {} id was deleted", id);
    }
}
