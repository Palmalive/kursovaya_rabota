package com.example.kursovaya_rabota.menu.dish;

import com.example.kursovaya_rabota.appUser.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class DishController {
    private final DishService dishService;
    private final AppUserService appUserService;


    @GetMapping("/menu/dish/{id}")
    public String dishInfo(@PathVariable Long id, Model model, Principal principal){
        Dish dish = dishService.getDishById(id);
        model.addAttribute("dish", dish);
        model.addAttribute("images", dish.getImages());
        model.addAttribute("isAvailable", dishService.isDishAvailable(id));
        model.addAllAttributes(appUserService.getAllRoles(principal));
        return "dish-info";
    }

    @GetMapping("/menu/dish/create/{segmentId}")
    public String addDish(@PathVariable Long segmentId, Model model, Principal principal) {
        model.addAttribute("segmentId", segmentId);
        model.addAllAttributes(appUserService.getAllRoles(principal));
        return "dish-create";
    }

    @PostMapping("/menu/dish/create")
    public String addDish(@RequestParam("segmentId") Long segmentId
            , @RequestParam("files") List<MultipartFile> files
            , Dish dish){

        dishService.createDish(dish, files, segmentId);
        return "redirect:/menu";
    }

    @PostMapping("/menu/dish/delete/{id}")
    public String deleteDish(@PathVariable Long id){
        dishService.deleteDish(id);
        return "redirect:/menu";
    }



    @GetMapping("/menu/dish/edit/{id}")
    public String editDish(@PathVariable Long id, Model model, Principal principal){
        model.addAttribute("dish", dishService.getDishById(id));
        model.addAllAttributes(appUserService.getAllRoles(principal));
        return "dish-edit";
    }

    @PostMapping("/menu/dish/edit")
    public String editDish(@RequestParam("name") String name
            , @RequestParam("description") String description
            , @RequestParam("dishId") Long id){

        dishService.updateDish(name, description, id);

        return "redirect:/menu/dish/edit/"+id;
    }

}
