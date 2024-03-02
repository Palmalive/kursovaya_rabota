package com.example.kursovaya_rabota.menu.segment;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class SegmentController {

    private final SegmentService segmentService;

    @PostMapping("/menu/segment/create")
    public String createSegment(Segment segment){
        segmentService.saveSegment(segment);
        return "redirect:/menu";
    }

    @PostMapping("/menu/segment/delete/{id}")
    public String deleteSegment(@PathVariable Long id){
        segmentService.deleteSegment(id);
        return "redirect:/menu";
    }

}
