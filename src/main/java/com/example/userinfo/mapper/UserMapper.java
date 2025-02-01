package com.example.userinfo.mapper;

import com.example.userinfo.dto.ContactsDTO;
import com.example.userinfo.dto.DetailsDTO;
import com.example.userinfo.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface UserMapper {
    void toUserFromContacts(ContactsDTO contactsDTO,
                @MappingTarget UserEntity userEntity);


    void toUserFromDetails(DetailsDTO detailsDTO,
                           @MappingTarget UserEntity userEntity);
}
