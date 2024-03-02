package com.example.kursovaya_rabota.analytics;

import com.example.kursovaya_rabota.appUser.AppUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class AnalyticsController {
    private final AnalyticsService analyticsService;
    private final AppUserService appUserService;
    @GetMapping("/analytics")
    public String analytics(Model model, Principal principal){
        model.addAttribute("report1", analyticsService.getMonthReportCountPerHour());
        model.addAttribute("report2", analyticsService.getMonthReportSumPerDay());
        model.addAttribute("report3", analyticsService.getMonthReportCountDish());
        model.addAllAttributes(appUserService.getAllRoles(principal));
        return "analytics";
    }
}
