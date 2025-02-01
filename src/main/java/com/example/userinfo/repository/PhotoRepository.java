package com.example.userinfo.repository;

import com.example.userinfo.entity.PhotoEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PhotoRepository extends JpaRepository<PhotoEntity,Long> {
    Optional<PhotoEntity> findByUser_Id(long id);
}
