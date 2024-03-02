package com.example.kursovaya_rabota.menu.dish;

import com.example.kursovaya_rabota.menu.image.Image;
import com.example.kursovaya_rabota.menu.segment.SegmentService;
import com.example.kursovaya_rabota.warehouse.warehouseItem.WarehouseItemService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class DishService {
    private final DishRepository dishRepository;
    private final SegmentService segmentService;
    private final WarehouseItemService warehouseItemService;

    public Dish getDishById(Long id){
        return dishRepository.findById(id).orElse(null);
    }

    public void deleteDish(Long id) {
        Dish dish = getDishById(id);
        segmentService.deleteDishFromSegment(dish);
        saveDish(dish);
        log.info("Dish with id {} was deleted", id);
    }

    public void addImageToDish(Long dishId, MultipartFile multipartFile) {
        Dish dish = getDishById(dishId);
        addImageToDish(multipartFile, dish);
        saveDish(dish);
    }

    private void setPreviewImageId(Dish dish) {
        dish.setPreviewImageId();
    }

    public void saveDish(Dish dish){
        dishRepository.save(dish);
    }


    public void createDish(Dish dish, List<MultipartFile> files, Long segmentId) {

        addImagesToDish(files, dish);
        setPreviewImageId(dish);
        segmentService.addDishToSegment(dish, segmentId);
        log.info("Dish with id {} was saved", dish.getId());
    }

    private void addImagesToDish(List<MultipartFile> files, Dish dish) {
        for (MultipartFile file: files){
            addImageToDish(file, dish);
        }
        saveDish(dish);
    }

    private void addImageToDish(MultipartFile file, Dish dish){
        Image image;
        if(file.getSize() != 0){
            image = toImageEntity(file);
            dish.addImageToDish(image);
        }
    }

    private Image toImageEntity(MultipartFile file) {
        Image image = new Image();
        image.setName(file.getName());
        image.setName(file.getName());
        image.setOriginalFileName(file.getOriginalFilename());
        image.setContentType(file.getContentType());
        image.setSize(file.getSize());
        try {
            image.setBytes(file.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return image;
    }

    public boolean isDishAvailable(Long id){
        Dish dish = getDishById(id);
        return warehouseItemService.isDishHasAllIngredients(dish);
    }

    public void updateDish(String name, String description, Long id) {
        Dish dish = getDishById(id);
        dish.setName(name);
        dish.setDescription(description);
        saveDish(dish);
        log.info("dish with id {} was updated", id);
    }
}
