package ru.linkphoria.linkphoriaproject.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.linkphoria.linkphoriaproject.models.Image;
import ru.linkphoria.linkphoriaproject.models.User;
import ru.linkphoria.linkphoriaproject.repositories.ImageRepository;
import ru.linkphoria.linkphoriaproject.repositories.UserRepository;
import ru.linkphoria.linkphoriaproject.services.ImageService;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequiredArgsConstructor
public class ImageController {
    private final ImageRepository imageRepository;
    private final ImageService imageService;
    private final UserRepository userRepository;

    @GetMapping("/images/{imageId}")
    public ResponseEntity<byte[]> getImage(@PathVariable Long imageId) {
        Image image = imageRepository.findById(imageId)
                .orElseThrow(() -> new IllegalArgumentException("Изображение не найдено"));
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_TYPE, "image/jpeg")
                .body(image.getContent());
    }

    @PostMapping("/upload")
    public ResponseEntity<Map<String, Object>> uploadImage(@RequestParam("avatar") MultipartFile file, Model model) {
        Map<String, Object> response = new HashMap<>();

        try {
            Image image = imageService.saveImage(file);

            User currentUser = getCurrentUser();

            currentUser.setAvatar(image);
            userRepository.save(currentUser);

            response.put("success", true);
            response.put("avatarUrl", "/images/" + image.getId());
            model.addAttribute("img", image.getId());
            return ResponseEntity.ok(response);

        } catch (IOException e) {
            response.put("success", false);
            response.put("message", "Ошибка загрузки изображения");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }


    private User getCurrentUser() {
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
