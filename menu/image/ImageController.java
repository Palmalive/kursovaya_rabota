package com.example.kursovaya_rabota.menu.image;

import com.example.kursovaya_rabota.menu.dish.DishService;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;

@Controller
@RequiredArgsConstructor
public class ImageController {

    private final ImageRepository imageRepository;
    private final DishService dishService;

    @GetMapping("/menu/image/{id}")
    public ResponseEntity<?> getImageById(@PathVariable Long id){
        Image image = imageRepository.findById(id).orElse(null);
        return ResponseEntity.ok()
                .header("fileName", image.getOriginalFileName())
                .contentType(MediaType.valueOf(image.getContentType()))
                .contentLength(image.getSize())
                .body(new InputStreamResource(new ByteArrayInputStream(image.getBytes())));
    }

    @PostMapping("/menu/image/delete")
    public String deleteImage(@RequestParam("imageId") Long id, @RequestParam("dishId") Long dishId){
        imageRepository.deleteById(id);
        return "redirect:/menu/dish/edit/"+dishId;
    }

    @PostMapping("/menu/image/add")
    public String addImageToDish(@RequestParam("dishId") Long dishId, @RequestParam("file") MultipartFile multipartFile){
        dishService.addImageToDish(dishId, multipartFile);
        return "redirect:/menu/dish/edit/" + dishId;
    }
}
