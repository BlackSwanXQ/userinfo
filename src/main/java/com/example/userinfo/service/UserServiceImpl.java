package com.example.userinfo.service;

import com.example.userinfo.dto.ContactsDTO;
import com.example.userinfo.dto.DetailsDTO;
import com.example.userinfo.entity.UserEntity;
import com.example.userinfo.exceptions.UserNotFoundException;
import com.example.userinfo.mapper.UserMapper;
import com.example.userinfo.repository.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository,
                           UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    /**
     * Создает пользователя.
     */
    @Override
    public UserEntity createUser(UserEntity user) {
        return userRepository.save(user);
    }
    /**
     * Получает пользователя.
     */
    @Override
    public UserEntity getUser(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));
    }
    /**
     * Обновляет всю информацию о пользователе.
     */
    @Override
    public UserEntity updateUser(long id, UserEntity user) {
        UserEntity oldUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        oldUser.setFirstName(user.getFirstName());
        oldUser.setLastName(user.getLastName());
        oldUser.setPatronymicName(user.getPatronymicName());
        oldUser.setEmail(user.getEmail());
        oldUser.setBirthday(user.getBirthday());
        oldUser.setPhone(user.getPhone());

        return userRepository.save(oldUser);
    }

    /**
     * Удаляет пользователя.
     */
    @Override
    public UserEntity removeUser(long id) {
        UserEntity user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        userRepository.delete(user);
        return user;
    }

    /**
     * Обновляет информацию о контактах пользователя.
     */
    @Override
    public UserEntity updateContacts(long id, ContactsDTO contactsDTO) {
        UserEntity oldUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        userMapper.toUserFromContacts(contactsDTO, oldUser);
        return userRepository.save(oldUser);
    }

    /**
     * Обновляет информацию о Ф.И.О. пользователя.
     */
    @Override
    public UserEntity updateDetails(long id, DetailsDTO detailsDTO) {
        UserEntity oldUser = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(id));

        userMapper.toUserFromDetails(detailsDTO, oldUser);
        return userRepository.save(oldUser);
    }
}
