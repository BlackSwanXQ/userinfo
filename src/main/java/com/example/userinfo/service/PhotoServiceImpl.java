package com.example.userinfo.service;

import com.example.userinfo.entity.PhotoEntity;
import com.example.userinfo.entity.UserEntity;
import com.example.userinfo.exceptions.PhotoNotFountException;
import com.example.userinfo.exceptions.UserNotFoundException;
import com.example.userinfo.repository.PhotoRepository;
import com.example.userinfo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.Pair;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

import static java.nio.file.Paths.get;

@Service
public class PhotoServiceImpl implements PhotoService {

    private final UserRepository userRepository;
    private final PhotoRepository photoRepository;
    private final Path path;

    public PhotoServiceImpl(UserRepository userRepository,
                            PhotoRepository photoRepository,
                            @Value("${path.to.photos.folder}") String photoDirName) {
        this.userRepository = userRepository;
        this.photoRepository = photoRepository;
        path = get(photoDirName);
    }

    /**
     * Создает/обновляет фото пользователя.
     */
    @Override
    public void uploadPhoto(MultipartFile multipartFile, Long id) {
        try {
            byte[] data = multipartFile.getBytes();
            String extention = StringUtils.getFilenameExtension(multipartFile.getOriginalFilename());
            Path userPath = path.resolve(UUID.randomUUID().toString() + "." + extention);
            Files.write(userPath, data);
            UserEntity user = userRepository.findById(id)
                    .orElseThrow(() -> new UserNotFoundException(id));

            PhotoEntity photo = photoRepository.findByUser_Id(id)
                    .orElseGet(PhotoEntity::new);
            photo.setUser(user);
            photo.setData(data);
            photo.setFileSize(data.length);
            photo.setMediaType(multipartFile.getContentType());
            photo.setFilePath(userPath.toString());
            photoRepository.save(photo);
        } catch (IOException e) {
            throw new PhotoNotFountException(id);
        }
    }

    /**
     * Получает фото пользователя из файловой системы.
     */
    public Pair<byte[], String> getPhotoFromFs(long id) {
        try {
            PhotoEntity avatar = photoRepository.findByUser_Id(id)
                    .orElseThrow(() -> {
                        return new UserNotFoundException(id);
                    });
            return Pair.of(Files.readAllBytes(Paths.get(avatar.getFilePath())), avatar.getMediaType());
        } catch (IOException e) {
            throw new PhotoNotFountException(id);
        }
    }

    /**
     * Удаляет фото пользователя
     */
    @Override
    public void deletePhoto(Long id) {
        PhotoEntity photo = photoRepository.findByUser_Id(id)
                .orElseThrow(() -> new PhotoNotFountException(id));
        photoRepository.delete(photo);
    }
}
