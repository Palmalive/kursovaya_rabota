package com.example.kursovaya_rabota.order;

import com.example.kursovaya_rabota.appUser.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;
    private final AppUserService appUserService;

    @GetMapping("/cook")
    public String cook(Model model, Principal principal){
        model.addAttribute("orders", orderService.getOrdersByStatus(OrderStatus.COOKING));
        model.addAllAttributes(appUserService.getAllRoles(principal));
        return "cook-orders";
    }

    @PostMapping("/cook/complete")
    public String changeOrderStatusToCooked(@RequestParam("orderId") Long id){
        orderService.changeOrderStatus(id, OrderStatus.COOKED);
        return "redirect:/cook";
    }

    @GetMapping("/waiter")
    public String waiter(Model model, Principal principal){
        model.addAttribute("orders", orderService.getOrdersByStatus(OrderStatus.COOKED));
        model.addAllAttributes(appUserService.getAllRoles(principal));
        return "waiter-orders";
    }

    @PostMapping("/waiter/complete")
    public String changeOrderStatusToCompleted(@RequestParam("orderId") Long id){
        orderService.changeOrderStatus(id, OrderStatus.COMPLETED);
        return "redirect:/waiter";
    }




}
