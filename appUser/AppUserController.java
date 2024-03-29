package com.example.kursovaya_rabota.appUser;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class AppUserController {
     private final AppUserService appUserService;

    @GetMapping("/login")
    public String login(){
        return "login";
    }

    @GetMapping("/admin/user/create")
    public String createUserWithRoles(){
        return "admin-user-create";
    }

    @PostMapping("/admin/user/create")
    public String crateUserWithRoles(AppUser appUser, @RequestParam("radiobutton") AppUserRole role){
        appUserService.createUserWithRoles(appUser, role);
        return "redirect:/menu";
    }
}
