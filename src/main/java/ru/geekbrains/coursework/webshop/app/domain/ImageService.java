package ru.geekbrains.coursework.webshop.app.domain;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.geekbrains.coursework.webshop.app.dao.ImageRepository;
import ru.geekbrains.coursework.webshop.app.domain.entities.Image;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ImageService extends AService<Image, ImageRepository> {

    public void save(Image entity, List<MultipartFile> multipartFiles) {
        this.requireNotNull(entity, "Entity Cant be NULL");
        this.requireNotNull(multipartFiles, "MultipartFiles Cant be NULL");

        if (entity.getId() == 0 && !multipartFiles.isEmpty()) {
            this.getRepository().saveAll(multipartFiles.stream().map(Image::new).collect(Collectors.toList()));
        } else {
            multipartFiles.stream().findFirst().ifPresent(multipartFile -> {
                entity.setAll(multipartFile);
                this.getRepository().save(entity);
            });
        }
    }
}
