package com.example.kursovaya_rabota.menu;

import com.example.kursovaya_rabota.appUser.AppUserService;
import com.example.kursovaya_rabota.menu.segment.SegmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class MenuController {
    private final SegmentService segmentService;
    private final AppUserService appUserService;
    @GetMapping("/menu")
    public String menu(Model model, Principal principal){
        model.addAllAttributes(appUserService.getAllRoles(principal));
        model.addAttribute("segments", segmentService.getAllSegments());
        return "menu";
    }

    @GetMapping("/")
    public String redirectToMenu(){
        return "redirect:/menu";
    }
}
