package com.example.userinfo.service;

import org.springframework.data.util.Pair;
import org.springframework.web.multipart.MultipartFile;

public interface PhotoService {
void uploadPhoto(MultipartFile file,Long id);
    Pair<byte[], String> getPhotoFromFs(long id);
    void deletePhoto(Long id);
}
