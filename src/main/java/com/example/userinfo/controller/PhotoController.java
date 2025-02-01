package com.example.userinfo.controller;

import com.example.userinfo.service.PhotoService;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/photo")
public class PhotoController {
    private final PhotoService photoService;
    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }
    /**
     * Создает/обновляет фото пользователя.
     */
    @PostMapping(value = "/{id}/file", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public void uploadPhoto(@PathVariable Long id,
                             @RequestParam MultipartFile multipartFile) {
        photoService.uploadPhoto(multipartFile, id);
    }

    /**
     * Получает фото пользователя из файловой системы.
     */
    @GetMapping("/{id}/photo-from-fs")
    public ResponseEntity<byte[]> getPhotoFromFs(@PathVariable long id) {
        return buildResponseEntity(photoService.getPhotoFromFs(id));
    }


    private ResponseEntity<byte[]> buildResponseEntity(Pair<byte[], String> pair) {
        byte[] data = pair.getFirst();
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentLength(data.length)
                .contentType(MediaType.parseMediaType(pair.getSecond()))
                .body(data);
    }

    /**
     * Удаляет фото пользователя
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deletePhoto(@PathVariable("id") Long id) {
        photoService.deletePhoto(id);
        return ResponseEntity.ok().build();
    }
}
