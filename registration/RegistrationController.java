package com.example.kursovaya_rabota.registration;

import com.example.kursovaya_rabota.appUser.AppUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class RegistrationController {

    private final RegistrationService registrationService;

    @PostMapping("/registration")
    public String register(AppUser appUser, Model model){
        if(!registrationService.register(appUser)) {
            model.addAttribute("errMassage", "Эта почта уже занята");
            return "redirect:/registration";
        }
        return "redirect:/login";
    }


    @GetMapping("/registration")
    public String register(){
        return "registration";
    }

//    @GetMapping
//    public String confirm(@RequestParam("token") String token){
//        registrationService.confirmToken(token);
//        return "";
//    }

}
