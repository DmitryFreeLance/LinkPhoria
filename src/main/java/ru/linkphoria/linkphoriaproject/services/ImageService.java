package ru.linkphoria.linkphoriaproject.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.linkphoria.linkphoriaproject.models.Image;
import ru.linkphoria.linkphoriaproject.repositories.ImageRepository;

import java.io.IOException;

@Service
@RequiredArgsConstructor
public class ImageService {

    private final ImageRepository imageRepository;

    public Image saveImage(MultipartFile file) throws IOException {
        if (file.getSize() > 5 * 1024 * 1024) {
            throw new IOException("Файл слишком большой");
        }
        Image image = new Image();
        image.setFileName(file.getOriginalFilename());
        image.setContent(file.getBytes());
        return imageRepository.save(image);
    }
}