package com.example.kursovaya_rabota.warehouse.warehouseItem;

import com.example.kursovaya_rabota.menu.dish.Dish;
import com.example.kursovaya_rabota.menu.dish.ingredient.Ingredient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Slf4j
@RequiredArgsConstructor
public class WarehouseItemService {
    private final WarehouseItemRepository warehouseItemRepository;

    public List<WarehouseItem> getAllWarehouseItems() {
        return warehouseItemRepository.findAll();
    }

    public void addWarehouseItem(WarehouseItem warehouseItem) {
        WarehouseItem warehouseItemFromDB =
                warehouseItemRepository.findByName(warehouseItem.getName()).orElse(null);
        if(warehouseItemFromDB == null){
            warehouseItemRepository.save(warehouseItem);
            log.info("WarehouseItem with id {} was added", warehouseItem.getId());
        } else {
            warehouseItemFromDB.increaseWeight(warehouseItem.getWeight());
            warehouseItemRepository.save(warehouseItemFromDB);
            log.info("WarehouseItem with id {} weight was increased on {}"
                    , warehouseItemFromDB.getId(), warehouseItem.getWeight());

        }
    }

    public void deleteWarehouseItem(Long id) {
        warehouseItemRepository.deleteById(id);
        log.info("Warehouse with id {} was deleted", id);
    }

    public boolean isDishHasAllIngredients(Dish dish) {
        List<Ingredient> ingredients = dish.getIngredients();
        for (Ingredient ingredient: ingredients){
            String ingredientName = ingredient.getName();
            double ingredientWeight = ingredient.getWeight();
            WarehouseItem item = warehouseItemRepository.findByName(ingredientName).orElse(null);
            if (item == null){
                return false;
            } else {
                if (item.getWeight() < ingredientWeight){
                    return false;
                }
            }
        }
        return true;
    }

    public void decreaseIngredients(String name, double weight) {

        WarehouseItem item = getWarehouseItemByName(name);
        item.decreaseWeight(weight);

    }

    public WarehouseItem getWarehouseItemByName(String name){
        return warehouseItemRepository.findByName(name).orElse(null);
    }
}
