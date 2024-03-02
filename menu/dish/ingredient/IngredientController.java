package com.example.kursovaya_rabota.menu.dish.ingredient;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class IngredientController {
    private final IngredientService ingredientService;

    @PostMapping("/menu/ingredient/add")
    public String addIngredientToDish(Ingredient ingredient, @RequestParam("dishId") Long dishId){
        ingredientService.saveIngredient(ingredient, dishId);
        return "redirect:/menu/dish/edit/"+ dishId;
    }

    @PostMapping("/menu/ingredient/delete")
    public String deleteIngredientFromDish(@RequestParam("dishId") Long dishId, @RequestParam("ingredientId") Long ingredientId){
        ingredientService.deleteIngredient(ingredientId);
        return "redirect:/menu/dish/edit/"+ dishId;

    }

}
