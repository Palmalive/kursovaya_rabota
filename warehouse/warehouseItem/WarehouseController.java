package com.example.kursovaya_rabota.warehouse.warehouseItem;

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
public class WarehouseController {
    private final WarehouseItemService warehouseItemService;
    private final AppUserService appUserService;

    @GetMapping("/warehouse")
    public String warehouse(Model model, Principal principal){
        model.addAttribute("warehouseItems", warehouseItemService.getAllWarehouseItems());
        model.addAllAttributes(appUserService.getAllRoles(principal));
        return "warehouse";
    }

    @GetMapping("/warehouse/add")
    public String addWarehouseItem(Model model, Principal principal){
        model.addAllAttributes(appUserService.getAllRoles(principal));
        return "warehouse-add";
    }

    @PostMapping("/warehouse/add")
    public String addWarehouseItem(WarehouseItem warehouseItem){
        warehouseItemService.addWarehouseItem(warehouseItem);
        return "redirect:/warehouse";
    }

    @PostMapping("warehouse/delete")
    public String deleteWareHouseItem(@RequestParam("itemId") Long id){
        warehouseItemService.deleteWarehouseItem(id);
        return "redirect:/warehouse";
    }
}
