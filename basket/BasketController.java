package com.example.kursovaya_rabota.basket;

import com.example.kursovaya_rabota.appUser.AppUserService;
import com.example.kursovaya_rabota.order.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class BasketController {
    private final BasketService basketService;
    private final OrderService orderService;
    private final AppUserService appUserService;


    @PostMapping("/menu/basket/add")
    public String addDishToBasket(@RequestParam("count") int count
            , @RequestParam("dishId") Long dishId, Principal principal){
        basketService.addDishToBasket(dishId, count, principal);
        return "redirect:/menu";
    }


    @GetMapping("/basket")
    public String basket(Model model, Principal principal, @ModelAttribute(name = "message") String  message){
        model.addAttribute("basket", basketService.getBasketByPrincipal(principal));
        model.addAllAttributes(appUserService.getAllRoles(principal));
        model.addAllAttributes(appUserService.getAllRoles(principal));
        model.addAttribute("message", message);
        return "basket";
    }

    @PostMapping("/basket/order")
    public String createOrder(Principal principal, RedirectAttributes redirectAttributes){
        String message = orderService.createOrder(principal);
        if (message != null){
            redirectAttributes.addAttribute("message", message);

            return "redirect:/basket";
        }
        return "redirect:/menu";
    }

}
