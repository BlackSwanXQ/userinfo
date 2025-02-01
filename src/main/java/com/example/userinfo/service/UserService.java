package com.example.userinfo.service;

import com.example.userinfo.dto.ContactsDTO;
import com.example.userinfo.dto.DetailsDTO;
import com.example.userinfo.entity.UserEntity;

public interface UserService {
    UserEntity createUser(UserEntity user);

    UserEntity getUser(long id);
    UserEntity updateUser(long id,UserEntity user);
    UserEntity removeUser(long id);
    UserEntity updateContacts(long id, ContactsDTO contacts);
    UserEntity updateDetails(long id, DetailsDTO detailsDTO);
}
